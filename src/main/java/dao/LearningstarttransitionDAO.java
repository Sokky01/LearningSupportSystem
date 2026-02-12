package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.LearningstarttransitionBeans;

public class LearningstarttransitionDAO {

    // すべての科目取得
    public List<LearningstarttransitionBeans> subjectsNameAcquisition() {
        List<LearningstarttransitionBeans> list = new ArrayList<>();
        String sql = "SELECT SubjectId, SubjectName FROM subjectmaster";
        try (Connection conn = DbConnect.getConnection();
	             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("SubjectId");
                String name = rs.getString("SubjectName");
                list.add(new LearningstarttransitionBeans(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 追加：IDから科目取得
    public LearningstarttransitionBeans getSubjectById(int subjectId) {
        String sql = "SELECT SubjectId, SubjectName FROM subjectmaster WHERE SubjectId = ?";
        try (Connection conn = DbConnect.getConnection();
	             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, subjectId);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("SubjectId");
                String name = rs.getString("SubjectName");
                return new LearningstarttransitionBeans(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 見つからなかった場合
    }
}
