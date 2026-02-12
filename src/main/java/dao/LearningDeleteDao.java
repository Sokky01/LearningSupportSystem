
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import model.DeleteLearningRecordBeans;

public class LearningDeleteDao {

    private final String JDBC_URL = "jdbc:mariadb://127.0.0.1:3306/lssdb";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";

    // 学習時間を0に戻す
    public int resetLearningTime(DeleteLearningRecordBeans bean) {

        String sql =
            "UPDATE LearningHistory " +
            "SET LearningTime = 0 " +
            "WHERE StudentNo = ? AND SubjectId = ?";

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, bean.getStudentNo());
                ps.setInt(2, bean.getSubjectId());

                int updated = ps.executeUpdate();
                System.out.println("UPDATED=" + updated
                    + " studentNo=" + bean.getStudentNo()
                    + " subjectId=" + bean.getSubjectId());

                return updated;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
