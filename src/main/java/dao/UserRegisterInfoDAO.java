package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserRegisterInfoDAO {

    private final String JDBC_URL = "jdbc:mariadb://127.0.0.1:3306/lssdb";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";


        public void insertUser(int studentNo, int attendanceNo, String classId, String studentName) {
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
                String sql = "INSERT INTO userMaster(StudentNo,AttendanceNo,ClassId,StudentName,MaxGrade,NickName,PublicPreference,SettingGradeId,LastLogin) VALUES (?, ?, ?, ?,1,'名無し',0,1,'2020-10-01')";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, studentNo);
                ps.setInt(2, attendanceNo);
                ps.setString(3, classId);
                ps.setString(4, studentName);
                
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void registerLogin(int studentNo, String password) {
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
                String sql = "INSERT INTO loginMaster(AccountId,Password,Role) VALUES (?, ?,0)";//利用者＝学生だから(role=0)に設定
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, studentNo);
                ps.setString(2, password);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        	
        	
        

    

}

