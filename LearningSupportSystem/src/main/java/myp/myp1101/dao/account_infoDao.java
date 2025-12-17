package myp.myp1101.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import myp.myp1101.model.UserProfile;

public class account_infoDao {
    // データベース接続情報
    private static final String URL = "jdbc:mariadb://127.0.0.1:3306/lssdb";
    private static final String USER = "root";
    private static final String PASS = "mysql";

    // ユーザープロフィールを取得するSQL文
    private static final String FIND_SQL = 
            "SELECT StudentNo, StudentName, MaxGrade, PublicPreference, SettingGradeId, Password " +
            "FROM userprofilesettingvirtual " +
            "WHERE StudentNo = ?";
    
    /**
     * ユーザーID (StudentNo) を基にアカウント情報を取得するメソッド
     */
    public UserProfile findAccount(String studentNo) {
        
        UserProfile user = null;

        // ★★★ 【重要】追加した部分：ドライバーの手動読み込み ★★★
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // ドライバーが見つからない場合は処理を中断
            return null; 
        }

        // データベース接続とSQL実行
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pStmt = conn.prepareStatement(FIND_SQL)) {

            // SQLの ? 部分にIDをセット
            pStmt.setString(1, studentNo);

            // SQLを実行
            ResultSet rs = pStmt.executeQuery();

            // 結果があった場合、UserProfileオブジェクトにデータを詰める
            if (rs.next()) {
                user = new UserProfile();
                
                // DBのカラム名 → Javaのセッター
                user.setStudentId(rs.getString("StudentNo"));
                user.setStudentName(rs.getString("StudentName"));
                
                // user.setMaxGrade(rs.getString("MaxGrade")); // 必要な場合コメントアウトを外す
                
                user.setPublicPreference(rs.getString("PublicPreference"));
                user.setSettingGradeId(rs.getString("SettingGradeId"));
                user.setPassword(rs.getString("Password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }
}