package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.DisplayStudyTimeGraphBeans;

public class GraphDisplayDAO {
    
    public List<DisplayStudyTimeGraphBeans> learningHistory(int stNo) {
        List<DisplayStudyTimeGraphBeans> list = new ArrayList<>();
        String sql = "SELECT StudentNo, SubjectId, SubjectName, YearMonthDate, LearningTime  FROM displaygraphvirtual WHERE StudentNo = ?";
        try (Connection conn = DbConnect.getConnection();
	             PreparedStatement pStmt = conn.prepareStatement(sql)) {
        		pStmt.setInt(1, stNo);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                int studentNo = rs.getInt("StudentNo");
                int subjectId = rs.getInt("SubjectId");
                String subjectName = rs.getString("SubjectName");
                Date yearMonthDate = rs.getDate("YearMonthDate");
                int learningTime = rs.getInt("LearningTime");
                
                list.add(new DisplayStudyTimeGraphBeans(studentNo, subjectId, subjectName, yearMonthDate, learningTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
