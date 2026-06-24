package logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import model.UserProfile;

/**
 * AccountInfoLogic#validate のユニットテスト。
 *
 * <p>デシジョンテーブル（AccInf-01〜AccInf-10）に対応。
 * validate は DB に依存しない純粋なロジックのため、モックは不要。
 * UserProfile を生成し、各 if 文の分岐と try-catch（例外処理）を検証する。</p>
 */
class AccountInfoLogicTest {

    private final AccountInfoLogic logic = new AccountInfoLogic();

    /** 全項目を正常値で埋めた UserProfile を返すヘルパー。各テストで必要な箇所だけ上書きする。 */
    private UserProfile validUser() {
        UserProfile user = new UserProfile();
        user.setStudentName("山田太郎");   // null/空でなく60文字以内
        user.setNickName("たろう");        // null でなく60文字以内
        user.setSettingGradeId("3");       // 数値変換でき、範囲内
        user.setMaxGrade("10");            // 数値変換できる
        return user;
    }

    /** 指定文字数の文字列を生成するヘルパー（境界値テスト用）。 */
    private String str(int length) {
        return "a".repeat(length);
    }

    // ======================================================================
    // try-catch（例外処理）の分岐
    // ======================================================================
    @Nested
    @DisplayName("AccInf-01: try-catch（例外発生）")
    class TryCatchBranch {

        @Test
        @DisplayName("settingGradeId が数値変換できず NumberFormatException → 入力形式エラー")
        void numberFormatException() {
            UserProfile user = validUser();
            user.setSettingGradeId("abc"); // Integer.parseInt で NumberFormatException

            String result = logic.validate(user, null, null, null, "dbpass");

            assertEquals("入力形式が正しくありません。", result);
        }

        @Test
        @DisplayName("maxGrade が null で NumberFormatException → 入力形式エラー")
        void numberFormatExceptionByNull() {
            UserProfile user = validUser();
            user.setMaxGrade(null); // Integer.parseInt(null) → NumberFormatException

            String result = logic.validate(user, null, null, null, "dbpass");

            assertEquals("入力形式が正しくありません。", result);
        }

        @Test
        @DisplayName("dbPass が null のままパスワード照合で NullPointerException → 入力形式エラー")
        void nullPointerException() {
            UserProfile user = validUser();
            // newPass 入力あり → dbPass.equals(currentPass) を実行 → dbPass=null で NPE
            String result = logic.validate(user, "current", "newpass", "newpass", null);

            assertEquals("入力形式が正しくありません。", result);
        }
    }

    // ======================================================================
    // 名前（studentName）の if 文
    // ======================================================================
    @Nested
    @DisplayName("AccInf-02/03: 名前(studentName)の検証")
    class StudentNameBranch {

        @Test
        @DisplayName("AccInf-02: 名前が null → 名前を入力してください")
        void nameIsNull() {
            UserProfile user = validUser();
            user.setStudentName(null);

            assertEquals("名前を入力してください。",
                    logic.validate(user, null, null, null, "dbpass"));
        }

        @Test
        @DisplayName("AccInf-02: 名前が空文字 → 名前を入力してください")
        void nameIsEmpty() {
            UserProfile user = validUser();
            user.setStudentName("");

            assertEquals("名前を入力してください。",
                    logic.validate(user, null, null, null, "dbpass"));
        }

        @Test
        @DisplayName("AccInf-03: 名前が61文字 → 60文字以内エラー")
        void nameTooLong() {
            UserProfile user = validUser();
            user.setStudentName(str(61));

            assertEquals("名前は60文字以内で入力してください。",
                    logic.validate(user, null, null, null, "dbpass"));
        }

        @Test
        @DisplayName("境界値: 名前が60文字ちょうど → 名前エラーにならない")
        void nameBoundary60() {
            UserProfile user = validUser();
            user.setStudentName(str(60));

            // 名前は通過し、最終的に正常終了（null）になる
            assertNull(logic.validate(user, null, null, null, "dbpass"));
        }
    }

    // ======================================================================
    // ニックネーム（nickName）の if 文
    // ======================================================================
    @Nested
    @DisplayName("AccInf-04: ニックネーム(nickName)の検証")
    class NickNameBranch {

        @Test
        @DisplayName("ニックネームが null → 60文字以内エラー")
        void nickNameIsNull() {
            UserProfile user = validUser();
            user.setNickName(null);

            assertEquals("ニックネームは60文字以内で入力してください。",
                    logic.validate(user, null, null, null, "dbpass"));
        }

        @Test
        @DisplayName("ニックネームが61文字 → 60文字以内エラー")
        void nickNameTooLong() {
            UserProfile user = validUser();
            user.setNickName(str(61));

            assertEquals("ニックネームは60文字以内で入力してください。",
                    logic.validate(user, null, null, null, "dbpass"));
        }

        @Test
        @DisplayName("境界値: ニックネームが60文字ちょうど → エラーにならない")
        void nickNameBoundary60() {
            UserProfile user = validUser();
            user.setNickName(str(60));

            assertNull(logic.validate(user, null, null, null, "dbpass"));
        }
    }

    // ======================================================================
    // グレード範囲（settingGrade）の if 文
    // ======================================================================
    @Nested
    @DisplayName("AccInf-05: グレード範囲の検証")
    class GradeRangeBranch {

        @Test
        @DisplayName("グレードが下限未満(0 < 1) → 範囲超過エラー")
        void gradeBelowMin() {
            UserProfile user = validUser();
            user.setSettingGradeId("0");

            assertEquals("設定可能なグレードの範囲を超えています。",
                    logic.validate(user, null, null, null, "dbpass"));
        }

        @Test
        @DisplayName("グレードが上限超過(11 > maxGrade 10) → 範囲超過エラー")
        void gradeAboveMax() {
            UserProfile user = validUser();
            user.setSettingGradeId("11");
            user.setMaxGrade("10");

            assertEquals("設定可能なグレードの範囲を超えています。",
                    logic.validate(user, null, null, null, "dbpass"));
        }

        @Test
        @DisplayName("境界値: グレードが下限ちょうど(1) → エラーにならない")
        void gradeBoundaryMin() {
            UserProfile user = validUser();
            user.setSettingGradeId("1");

            assertNull(logic.validate(user, null, null, null, "dbpass"));
        }

        @Test
        @DisplayName("境界値: グレードが上限ちょうど(10 == maxGrade 10) → エラーにならない")
        void gradeBoundaryMax() {
            UserProfile user = validUser();
            user.setSettingGradeId("10");
            user.setMaxGrade("10");

            assertNull(logic.validate(user, null, null, null, "dbpass"));
        }
    }

    // ======================================================================
    // パスワード更新（newPass != null && !isEmpty）の if 文
    // ======================================================================
    @Nested
    @DisplayName("AccInf-06〜09: パスワード更新あり(newPass入力あり)の検証")
    class PasswordUpdateBranch {

        @Test
        @DisplayName("AccInf-06: 現在のパスワードが DB と不一致 → 現在のパスワードエラー")
        void currentPassMismatch() {
            UserProfile user = validUser();
            // currentPass("wrong") != dbPass("realdb")
            String result = logic.validate(user, "wrong", "newpass", "newpass", "realdb");

            assertEquals("現在のパスワードが正しくありません。", result);
        }

        @Test
        @DisplayName("AccInf-07: 新パスワードと確認用が不一致 → 確認用不一致エラー")
        void confirmMismatch() {
            UserProfile user = validUser();
            // currentPass == dbPass、newPass != confirmPass
            String result = logic.validate(user, "db", "newpass", "different", "db");

            assertEquals("新しいパスワードと確認用が一致しません。", result);
        }

        @Test
        @DisplayName("AccInf-08: 新パスワードが51文字 → 50文字以内エラー")
        void newPassTooLong() {
            UserProfile user = validUser();
            String longPass = str(51);
            String result = logic.validate(user, "db", longPass, longPass, "db");

            assertEquals("新しいパスワードは50文字以内で入力してください。", result);
        }

        @Test
        @DisplayName("AccInf-09: すべて正常 → null を返し、パスワードが newPass に更新される")
        void allValid_passwordUpdatedToNewPass() {
            UserProfile user = validUser();
            String result = logic.validate(user, "db", "newpw", "newpw", "db");

            assertNull(result);
            assertEquals("newpw", user.getPassword()); // newPass で更新
        }

        @Test
        @DisplayName("境界値: 新パスワードが50文字ちょうど → エラーにならず更新成功")
        void newPassBoundary50() {
            UserProfile user = validUser();
            String pass50 = str(50);
            String result = logic.validate(user, "db", pass50, pass50, "db");

            assertNull(result);
            assertEquals(pass50, user.getPassword());
        }
    }

    // ======================================================================
    // パスワード更新なし（else 分岐）
    // ======================================================================
    @Nested
    @DisplayName("AccInf-10: パスワード更新なし(newPass未入力)の検証")
    class PasswordKeepBranch {

        @Test
        @DisplayName("newPass が null → null を返し、パスワードは dbPass を維持")
        void newPassIsNull_keepDbPass() {
            UserProfile user = validUser();
            String result = logic.validate(user, null, null, null, "dbpass");

            assertNull(result);
            assertEquals("dbpass", user.getPassword()); // dbPass を維持
        }

        @Test
        @DisplayName("newPass が空文字 → null を返し、パスワードは dbPass を維持")
        void newPassIsEmpty_keepDbPass() {
            UserProfile user = validUser();
            String result = logic.validate(user, null, "", "", "dbpass");

            assertNull(result);
            assertEquals("dbpass", user.getPassword());
        }
    }
}
