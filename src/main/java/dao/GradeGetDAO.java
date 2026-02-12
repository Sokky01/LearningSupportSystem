package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeGetDAO {
	/**
	 * ユーザーのSettingGradeIdを取得する（数字で返す）
	 * @param userId ユーザーID
	 * @return SettingGradeId (1=ブロンズ, 2=シルバー, 3=ゴールド, 4=プラチナ, 5=ダイヤモンド)
	 */
	public int gradeGet(int userId) {
		int gradeId = 1; // デフォルトはブロンズ
        String sql = "SELECT SettingGradeId FROM userMaster WHERE StudentNo = ?";
        
        // DbConnectを使って接続を取得し、try-with-resourcesで自動クローズ
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, userId);
            
         // SQL実行
         			try (ResultSet rs = stmt.executeQuery()) {
         				if (rs.next()) {
         					gradeId = rs.getInt("SettingGradeId");
         				}
         			}
            
            return gradeId;
            
        } catch(SQLException e) {
            e.printStackTrace();
            return gradeId;
        }
    }

}