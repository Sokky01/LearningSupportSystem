package lrs.lrs0701.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CountUpDAO {
private Connection conn;
	
	private final String JDBC_URL = "jdbc:mariadb://127.0.0.1:3306/lssdb";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";
    
	public CountUpDAO() {
		// JDBCドライバを接続するコンストラクタ
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			this.conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}catch (SQLException e) {
            System.err.println("DB接続に失敗しました。");
            e.printStackTrace();
        }
	    
	}
	
	public boolean learningRecord(int learningTime,int userId,int subjectId) {
		LocalDate today = LocalDate.now();
		String sql = "INSERT INTO learninghistory (StudentNo,SubjectId,YearMonthDate,LearningTime) VALUES(?,?,?,?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setInt(1, userId);
		    stmt.setInt(2, subjectId);
		    stmt.setDate(3, Date.valueOf(today));
		    stmt.setInt(4, learningTime);

            stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
