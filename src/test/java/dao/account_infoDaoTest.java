package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import model.UserProfile;

/**
 * account_infoDao のユニットテスト。
 *
 * <p>findAccount / updateAccount は DB に依存するため、
 * {@code DbConnect.getConnection()}（static メソッド）を {@link MockedStatic} で差し替え、
 * Connection / PreparedStatement / ResultSet をモック化して
 * 各 try-catch（例外処理）と if 文（rs.next()）の分岐を検証する。</p>
 *
 * <p>※ MockedConstruction / MockedStatic には Mockito 5.x 以上が必要。</p>
 */
class account_infoDaoTest {

    private final account_infoDao dao = new account_infoDao();

    /** テスト用の UserProfile を返すヘルパー（updateAccount 用）。 */
    private UserProfile sampleUser() {
        UserProfile user = new UserProfile();
        user.setStudentId("1001");
        user.setStudentName("山田太郎");
        user.setNickName("たろう");
        user.setPublicPreference("1");
        user.setSettingGradeId("3");
        user.setPassword("pass123");
        return user;
    }

    // ======================================================================
    // findAccount
    // ======================================================================
    @Nested
    @DisplayName("findAccount: アカウント取得")
    class FindAccount {

        @Test
        @DisplayName("PubProf-01: DB成功 & レコード有り(rs.next()=true) → 値をセットした UserProfile を返す")
        void recordFound_returnsUserProfile() throws SQLException {
            Connection conn = mock(Connection.class);
            PreparedStatement pStmt = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);

            when(conn.prepareStatement(anyString())).thenReturn(pStmt);
            when(pStmt.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true); // レコード有り
            when(rs.getString("StudentNo")).thenReturn("1001");
            when(rs.getString("StudentName")).thenReturn("山田太郎");
            when(rs.getString("NickName")).thenReturn("たろう");
            when(rs.getString("MaxGrade")).thenReturn("10");
            when(rs.getString("PublicPreference")).thenReturn("1");
            when(rs.getString("SettingGradeId")).thenReturn("3");
            when(rs.getString("GradeName")).thenReturn("中級");
            when(rs.getString("Password")).thenReturn("pass123");

            try (MockedStatic<DbConnect> mocked = mockStatic(DbConnect.class)) {
                mocked.when(DbConnect::getConnection).thenReturn(conn);

                UserProfile result = dao.findAccount("1001");

                assertNotNull(result);
                assertEquals("1001", result.getStudentId());
                assertEquals("山田太郎", result.getStudentName());
                assertEquals("たろう", result.getNickName());
                assertEquals("10", result.getMaxGrade());
                assertEquals("1", result.getPublicPreference());
                assertEquals("3", result.getSettingGradeId());
                assertEquals("中級", result.getGradeName());
                assertEquals("pass123", result.getPassword());
            }

            // studentNo がプレースホルダにセットされていること
            verify(pStmt).setString(1, "1001");
        }

        @Test
        @DisplayName("PubProf-02: DB成功 & レコード無し(rs.next()=false) → null を返す")
        void recordNotFound_returnsNull() throws SQLException {
            Connection conn = mock(Connection.class);
            PreparedStatement pStmt = mock(PreparedStatement.class);
            ResultSet rs = mock(ResultSet.class);

            when(conn.prepareStatement(anyString())).thenReturn(pStmt);
            when(pStmt.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(false); // レコード無し

            try (MockedStatic<DbConnect> mocked = mockStatic(DbConnect.class)) {
                mocked.when(DbConnect::getConnection).thenReturn(conn);

                UserProfile result = dao.findAccount("9999");

                assertNull(result);
            }
        }

        @Test
        @DisplayName("PubProf-03: SQL実行で SQLException 発生 → catch で null を返す")
        void sqlException_returnsNull() throws SQLException {
            Connection conn = mock(Connection.class);

            // prepareStatement で SQLException を発生させ catch 節へ
            when(conn.prepareStatement(anyString())).thenThrow(new SQLException("SQL実行失敗"));

            try (MockedStatic<DbConnect> mocked = mockStatic(DbConnect.class)) {
                mocked.when(DbConnect::getConnection).thenReturn(conn);

                UserProfile result = dao.findAccount("1001");

                assertNull(result);
            }
        }

        @Test
        @DisplayName("PubProf-03(別ケース): DB接続(getConnection)で SQLException 発生 → null を返す")
        void connectionException_returnsNull() {
            try (MockedStatic<DbConnect> mocked = mockStatic(DbConnect.class)) {
                mocked.when(DbConnect::getConnection).thenThrow(new SQLException("接続失敗"));

                UserProfile result = dao.findAccount("1001");

                assertNull(result);
            }
        }
    }

    // ======================================================================
    // updateAccount
    // ======================================================================
    @Nested
    @DisplayName("updateAccount: アカウント更新（トランザクション）")
    class UpdateAccount {

        @Test
        @DisplayName("PubProf-01: 全更新成功 → setAutoCommit(false)→commit→true を返す")
        void updateSuccess_commitAndReturnTrue() throws SQLException {
            Connection conn = mock(Connection.class);
            PreparedStatement ps1 = mock(PreparedStatement.class);
            PreparedStatement ps2 = mock(PreparedStatement.class);

            // 1回目=ps1(userMaster用), 2回目=ps2(loginMaster用)
            when(conn.prepareStatement(anyString())).thenReturn(ps1, ps2);

            try (MockedStatic<DbConnect> mocked = mockStatic(DbConnect.class)) {
                mocked.when(DbConnect::getConnection).thenReturn(conn);

                boolean result = dao.updateAccount(sampleUser());

                assertTrue(result);
            }

            // トランザクション開始 → 2件更新 → コミット、ロールバックなし
            verify(conn).setAutoCommit(false);
            verify(ps1).executeUpdate();
            verify(ps2).executeUpdate();
            verify(conn).commit();
            verify(conn, never()).rollback();
        }

        @Test
        @DisplayName("PubProf-02: 更新中に SQLException → rollback→false を返す（commitは呼ばれない）")
        void updateFails_rollbackAndReturnFalse() throws SQLException {
            Connection conn = mock(Connection.class);
            PreparedStatement ps1 = mock(PreparedStatement.class);
            PreparedStatement ps2 = mock(PreparedStatement.class);

            when(conn.prepareStatement(anyString())).thenReturn(ps1, ps2);
            // userMaster の更新実行で例外発生
            when(ps1.executeUpdate()).thenThrow(new SQLException("更新失敗"));

            try (MockedStatic<DbConnect> mocked = mockStatic(DbConnect.class)) {
                mocked.when(DbConnect::getConnection).thenReturn(conn);

                boolean result = dao.updateAccount(sampleUser());

                assertFalse(result);
            }

            verify(conn).setAutoCommit(false);
            verify(conn).rollback();          // ロールバック実行
            verify(conn, never()).commit();   // コミットは呼ばれない
        }

        @Test
        @DisplayName("PubProf-03: DB接続失敗(getConnection で SQLException) → false を返す")
        void connectionFails_returnFalse() throws SQLException {
            try (MockedStatic<DbConnect> mocked = mockStatic(DbConnect.class)) {
                mocked.when(DbConnect::getConnection).thenThrow(new SQLException("接続失敗"));

                boolean result = dao.updateAccount(sampleUser());

                assertFalse(result);
            }
        }

        @Test
        @DisplayName("PubProf-03(別ケース): setAutoCommit で SQLException → 外側catchで false を返す")
        void setAutoCommitFails_returnFalse() throws SQLException {
            Connection conn = mock(Connection.class);
            // トランザクション開始で例外 → 外側の catch(SQLException) へ
            doThrow(new SQLException("autocommit失敗")).when(conn).setAutoCommit(false);

            try (MockedStatic<DbConnect> mocked = mockStatic(DbConnect.class)) {
                mocked.when(DbConnect::getConnection).thenReturn(conn);

                boolean result = dao.updateAccount(sampleUser());

                assertFalse(result);
            }

            verify(conn, never()).commit();
        }
    }
}
