package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CountUpDAO {

    // コンストラクタでの接続処理は不要になったので空にする、または削除してもOK
    public CountUpDAO() {
    }
    
    public boolean learningRecord(int learningTime, int userId, int subjectId) {
        LocalDate today = LocalDate.now();
        String sql = "INSERT INTO learninghistory (StudentNo, SubjectId, YearMonthDate, LearningTime) VALUES(?, ?, ?, ?)";
        
        // DbConnectを使って接続を取得し、try-with-resourcesで自動クローズ
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, userId);
            stmt.setInt(2, subjectId);
            stmt.setDate(3, Date.valueOf(today));
            stmt.setInt(4, learningTime);

            stmt.executeUpdate();
            
            // 正常に終わればtrue
            return true;
            
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 指定した科目の「今日の合計学習時間」を取得するメソッド
    public int getTodayTotalTimeBySubject(int studentNo, int subjectId) {
    	int todayTotalTimeBySubject = 0;
    	String sql = "SELECT SUM(LearningTime) AS TotalTime FROM learninghistory WHERE StudentNo = ? AND SubjectId = ? AND YearMonthDate = CURDATE()";
    	
    	try (Connection conn = DbConnect.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
    		
    		stmt.setInt(1, studentNo);
            stmt.setInt(2, subjectId);
    		
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    todayTotalTimeBySubject = rs.getInt("TotalTime");
                }
            }
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return todayTotalTimeBySubject;
    }

}