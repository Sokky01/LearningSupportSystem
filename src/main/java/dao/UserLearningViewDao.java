package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.userLearningRecordBeans; 

public class UserLearningViewDao {

	
	private final String JDBC_URL = "jdbc:mariadb://127.0.0.1:3306/lssdb";
	private final String DB_USER = "root";
	private final String DB_PASS = "mysql";
	
	public List<userLearningRecordBeans>getUserLearningRecord(int studentNo){
		
		String sql = "SELECT StudentNo, SubjectId, SubjectName, SubjectGoal, SubjectTotal, StudyGoalTotal, StudyTimeTotal FROM userviewlearningrecordvirtual WHERE StudentNo = ?";
												
		List<userLearningRecordBeans> userRecordViewList = new ArrayList<>();
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);
					PreparedStatement ps = conn.prepareStatement(sql)){										
			
				//sqlの？に学籍番号を入れる処理
				ps.setInt(1, studentNo);
				
				//sqlの実行結果を取得する処理
				try(ResultSet rs = ps.executeQuery()){
					while (rs.next()) {
						userRecordViewList.add(new userLearningRecordBeans(
								rs.getInt("StudentNo"),
								rs.getInt("SubjectId"),
								rs.getString("SubjectName"),
								rs.getInt("SubjectGoal"),
								rs.getInt("SubjectTotal"),
								rs.getInt("StudyGoalTotal"),
								rs.getInt("StudyTimeTotal")
								));
					}//
				}
			}
				
		
		}catch(Exception e) {
			e.printStackTrace();
		}
			return userRecordViewList;
	}
}

