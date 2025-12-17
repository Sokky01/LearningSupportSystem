package myp.myp1101.logic;

import myp.myp1101.dao.account_infoDao;
import myp.myp1101.model.UserProfile;

public class AccountInfoLogic {

    /**
     * 学生番号を元にプロフィール情報を取得して返す
     */
    public UserProfile getProfile(String studentNo) {
        // DAOのインスタンス化 (クラス名が account_info_display である点に注意)
        account_infoDao dao = new account_infoDao();
        
        // DAOのメソッドを呼び出してデータを取得
        UserProfile profile = dao.findAccount(studentNo);
        
        return profile;
    }
}