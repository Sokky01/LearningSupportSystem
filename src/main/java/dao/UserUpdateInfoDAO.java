package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.userAccountListBeans;

public class UserUpdateInfoDAO {

    /**
     * 全ユーザーの一覧を取得する
     */
    public List<userAccountListBeans> findAll() {
        List<userAccountListBeans> userList = new ArrayList<>();
        String sql = "SELECT StudentNo, AttendanceNo, ClassId, StudentName FROM userMaster";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql);
             ResultSet rs = pStmt.executeQuery()) {

            while (rs.next()) {
                int studentNo = rs.getInt("StudentNo");
                int attendanceNo = rs.getInt("AttendanceNo");
                String classId = rs.getString("ClassId");
                String studentName = rs.getString("StudentName");

                userAccountListBeans list = new userAccountListBeans(studentNo, attendanceNo, classId, studentName);
                userList.add(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return userList;
    }

    /**
     * 学籍番号でユーザーを検索する
     */
    public userAccountListBeans findByStudentNo(int studentNo) {
        userAccountListBeans user = null;
        String sql = "SELECT StudentNo, AttendanceNo, ClassId, StudentName FROM userMaster WHERE StudentNo = ?";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setInt(1, studentNo);
            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    user = new userAccountListBeans(
                        rs.getInt("StudentNo"),
                        rs.getInt("AttendanceNo"),
                        rs.getString("ClassId"),
                        rs.getString("StudentName")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * ユーザー情報を更新する
     */
    public boolean profileInfoAdd(userAccountListBeans beans, int originalStudentNo) {
        String sql = "UPDATE userMaster SET ClassId=?, AttendanceNo=?, StudentName=?, StudentNo=? WHERE StudentNo=?";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setString(1, beans.getClassId());
            pStmt.setInt(2, beans.getAttendanceNo());
            pStmt.setString(3, beans.getStudentName());
            pStmt.setInt(4, beans.getStudentNo());
            pStmt.setInt(5, originalStudentNo);

            int result = pStmt.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}