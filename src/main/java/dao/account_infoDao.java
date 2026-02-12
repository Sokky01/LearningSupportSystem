package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.UserProfile;

public class account_infoDao {

    // 取得用SQL
    private static final String FIND_SQL = 
            "SELECT StudentNo, StudentName, NickName, MaxGrade, PublicPreference, SettingGradeId, GradeName, Password " +
            "FROM userprofilesettingvirtual " +
            "WHERE StudentNo = ?";
    
    /**
     * アカウント情報を取得する
     */
    public UserProfile findAccount(String studentNo) {
        UserProfile user = null;

        // DbConnectを使用して接続
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(FIND_SQL)) {

            pStmt.setString(1, studentNo);
            
            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    user = new UserProfile();
                    user.setStudentId(rs.getString("StudentNo"));
                    user.setStudentName(rs.getString("StudentName"));
                    user.setNickName(rs.getString("NickName"));
                    user.setMaxGrade(rs.getString("MaxGrade")); 
                    user.setPublicPreference(rs.getString("PublicPreference"));
                    user.setSettingGradeId(rs.getString("SettingGradeId"));
                    user.setGradeName(rs.getString("GradeName"));
                    user.setPassword(rs.getString("Password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    /**
     * アカウント情報を更新する（2つのテーブルをトランザクション管理で更新）
     */
    public boolean updateAccount(UserProfile user) {
        String sqlUser = "UPDATE userMaster SET StudentName = ?, NickName = ?, PublicPreference = ?, SettingGradeId = ? WHERE StudentNo = ?";
        String sqlLogin = "UPDATE loginMaster SET Password = ? WHERE AccountId = ?";

        // DbConnectから接続を取得
        try (Connection conn = DbConnect.getConnection()) {
            
            // トランザクション開始
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(sqlUser);
                 PreparedStatement ps2 = conn.prepareStatement(sqlLogin)) {

                // 1. userMasterテーブルの更新
                ps1.setString(1, user.getStudentName());
                ps1.setString(2, user.getNickName());
                ps1.setString(3, user.getPublicPreference());
                ps1.setString(4, user.getSettingGradeId());
                ps1.setString(5, user.getStudentId());
                ps1.executeUpdate();

                // 2. loginMasterテーブルの更新
                ps2.setString(1, user.getPassword());
                ps2.setString(2, user.getStudentId());
                ps2.executeUpdate();

                // 成功したらコミット
                conn.commit();
                return true;

            } catch (SQLException e) {
                // 失敗したらロールバック
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}