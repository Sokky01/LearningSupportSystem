package lrs.lrs0701.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lrs.lrs0701.model.LearningstarttransitionBeans;

public class LearningstarttransitionDAO {
    private Connection conn;

    private final String JDBC_URL = "jdbc:mariadb://127.0.0.1:3306/lssdb";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";

    public LearningstarttransitionDAO() {
        // JDBCドライバを接続するコンストラクタ
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            this.conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした");
        } catch (SQLException e) {
            System.err.println("DB接続に失敗しました。");
            e.printStackTrace();
        }
    }

    // すべての科目取得
    public List<LearningstarttransitionBeans> subjectsNameAcquisition() {
        List<LearningstarttransitionBeans> list = new ArrayList<>();
        String sql = "SELECT SubjectId, SubjectName FROM subjectmaster";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
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
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subjectId);
            ResultSet rs = stmt.executeQuery();
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
