package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.RankingProfileBeans;

public class LearningTransitionDAO extends DbConnect{

	private static RankingProfileBeans studentInfo = null;

    public RankingProfileBeans LearningTransition(int studentNo) {
    	String sql = "SELECT * FROM LearningRecordTransitionVirtual WHERE StudentNo = ?";
    	
    	// DB接続
        try (Connection conn = DbConnect.getConnection();
        		PreparedStatement pStmt = conn.prepareStatement(sql)) {

        	pStmt.setInt(1, studentNo);

        	try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    studentInfo = new RankingProfileBeans();
                    studentInfo.setStudentNo(rs.getInt("StudentNo"));
                    studentInfo.setNickName(rs.getString("NickName"));
                    studentInfo.setMaxGrade(rs.getInt("MaxGrade"));
                    studentInfo.setPointTotal(rs.getInt("PointTotal"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("DBアクセス中にエラーが発生しました", e);
        }
        
        return studentInfo;

    }
    
}
