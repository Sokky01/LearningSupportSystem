package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.userAccountListBeans;

public class UserViewInfoDAO {

    /**
     * ユーザー一覧を表示する
     */
    public List<userAccountListBeans> findAll() {
        List<userAccountListBeans> userBeansList = new ArrayList<>();
        String sql = "SELECT StudentNo, AttendanceNo, ClassId, StudentName FROM userMaster";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql);
             ResultSet rs = pStmt.executeQuery()) {

            while (rs.next()) {
                int studentNo = rs.getInt("StudentNo");
                int attendanceNo = rs.getInt("AttendanceNo");
                String classId = rs.getString("ClassId");
                String studentName = rs.getString("StudentName");

                userAccountListBeans userAccount = new userAccountListBeans(studentNo, attendanceNo, classId, studentName);
                userBeansList.add(userAccount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return userBeansList;
    }

    /**
     * 学年によるフィルタリング検索
     */
    public List<userAccountListBeans> execute2(String schoolGradeName, int studentNo1) {
        List<userAccountListBeans> recordViewList = new ArrayList<>();
        
        // 基本となるSQL
        StringBuilder sql = new StringBuilder(
            "SELECT DISTINCT u.StudentNo, u.AttendanceNo, u.ClassId, u.StudentName " +
            "FROM userMaster u " +
            "INNER JOIN AdminViewLearningRecordVirtual v ON v.StudentNo = u.StudentNo WHERE 1=1 "
        );

        // 学年フィルタがある場合の条件追加
        if (schoolGradeName != null && !schoolGradeName.isEmpty()) {
            sql.append(" AND v.SchoolGradeName = ?");
        }

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql.toString())) {

            // パラメータのセット
            if (schoolGradeName != null && !schoolGradeName.isEmpty()) {
                pStmt.setString(1, schoolGradeName);
            }

            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    int studentNo = rs.getInt("StudentNo");
                    int attendanceNo = rs.getInt("AttendanceNo");
                    String classId = rs.getString("ClassId");
                    String studentName = rs.getString("StudentName");

                    userAccountListBeans bean = new userAccountListBeans(studentNo, attendanceNo, classId, studentName);
                    recordViewList.add(bean);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordViewList;
    }

    /**
     * ドロップダウンリスト用の学年一覧を取得
     */
    public List<String> getAllSchoolGrades() {
        String sql = "SELECT DISTINCT SchoolGradeName FROM adminviewlearningrecordvirtual ORDER BY SchoolGradeName";
        List<String> list = new ArrayList<>();

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql);
             ResultSet rs = pStmt.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("SchoolGradeName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}