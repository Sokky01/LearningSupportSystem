package logic;

import dao.account_infoDao;
import model.UserProfile;

public class AccountInfoLogic {

    /**
     * 学生番号を元にプロフィール情報を取得して返す
     */
    public UserProfile getProfile(String studentNo) {
        account_infoDao dao = new account_infoDao();
        return dao.findAccount(studentNo);
    }

    /**
     * プロフィール更新時のバリデーションチェック（桁数・グレード範囲・パスワード照合）
     * * @param user        入力されたデータ（Beans）
     * @param currentPass 入力された「現在のパスワード」
     * @param newPass     入力された「新しいパスワード」
     * @param confirmPass 入力された「確認用パスワード」
     * @param dbPass      DBに保存されている「本当のパスワード」
     * @return すべてOKなら true、NGなら false
     */
    public String validate(UserProfile user, String currentPass, String newPass, String confirmPass, String dbPass) {
        try {
            // 1. 名前・ニックネームのチェック
            if (user.getStudentName() == null || user.getStudentName().isEmpty()) return "名前を入力してください。";
            if (user.getStudentName().length() > 60) return "名前は60文字以内で入力してください。";
            if (user.getNickName() == null || user.getNickName().length() > 60) return "ニックネームは60文字以内で入力してください。";

            // 2. グレードの範囲チェック
            int settingGrade = Integer.parseInt(user.getSettingGradeId());
            int maxGrade = Integer.parseInt(user.getMaxGrade());
            if (settingGrade < 1 || settingGrade > maxGrade) return "設定可能なグレードの範囲を超えています。";

            // 3. パスワード更新のチェック
            if (newPass != null && !newPass.isEmpty()) {
                if (!dbPass.equals(currentPass)) return "現在のパスワードが正しくありません。";
                if (!newPass.equals(confirmPass)) return "新しいパスワードと確認用が一致しません。";
                if (newPass.length() > 50) return "新しいパスワードは50文字以内で入力してください。";
                
                user.setPassword(newPass);
            } else {
                user.setPassword(dbPass);
            }
        } catch (NumberFormatException | NullPointerException e) {
            return "入力形式が正しくありません。";
        }

        return null; // ★エラーがない場合は null を返す
    }
}