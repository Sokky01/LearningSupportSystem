package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.targetSettingBeans;

public class TargetSettingDao {

    private final String JDBC_URL = "jdbc:mariadb://127.0.0.1:3306/lssdb";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";

    /**
     * 目標時間を登録 or 更新
     */
    public void updateGoal(int studentNo, int subjectId, int targetTime) {

        String sql =
            "INSERT INTO subjecttarget (StudentNo, SubjectId, TargetTime) " +
            "VALUES (?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE TargetTime = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentNo);
            ps.setInt(2, subjectId);
            ps.setInt(3, targetTime);
            ps.setInt(4, targetTime);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("目標時間更新失敗", e);
        }
    }

    /**
     * 科目一覧取得（目標設定画面用）
     */
    public List<targetSettingBeans> getTargetSettingList(int studentNo) {

        List<targetSettingBeans> list = new ArrayList<>();

        String sql =
            "SELECT " +
            " v.StudentNo, " +
            " v.SubjectId, " +
            " v.SubjectName, " +
            " COALESCE(t.TargetTime, 0) AS TargetTime, " +
            " v.SubjectTotal, " +
            " v.StudyGoalTotal, " +
            " v.StudyTimeTotal " +
            "FROM userviewlearningrecordvirtual v " +
            "LEFT JOIN subjecttarget t " +
            " ON v.StudentNo = t.StudentNo " +
            " AND v.SubjectId = t.SubjectId " +
            "WHERE v.StudentNo = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentNo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new targetSettingBeans(
                        rs.getInt("StudentNo"),
                        rs.getInt("SubjectId"),
                        rs.getString("SubjectName"),
                        rs.getInt("TargetTime"),
                        rs.getInt("SubjectTotal"),
                        rs.getInt("StudyGoalTotal"),
                        rs.getInt("StudyTimeTotal")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
