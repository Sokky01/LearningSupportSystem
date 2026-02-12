package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.userAccountListBeans;

public class UserDeleteInfoDAO {

    /**
     * userMasterからの削除
     */
    public boolean delete(int originalStudentNo) {
        String sql = "DELETE FROM userMaster WHERE StudentNo = ?";
        return executeDelete(sql, originalStudentNo);
    }

    /**
     * learninghistoryからの削除
     */
    public boolean delete2(int originalStudentNo) {
        String sql = "DELETE FROM learninghistory WHERE StudentNo = ?";
        return executeDelete(sql, originalStudentNo);
    }

    /**
     * missionachievementからの削除
     */
    public boolean delete3(int originalStudentNo) {
        String sql = "DELETE FROM missionachievement WHERE StudentNo = ?";
        return executeDelete(sql, originalStudentNo);
    }

    /**
     * subjecttargetからの削除
     */
    public boolean delete4(int originalStudentNo) {
        String sql = "DELETE FROM subjecttarget WHERE StudentNo = ?";
        return executeDelete(sql, originalStudentNo);
    }

    /**
     * userdailymissionsからの削除
     */
    public boolean delete5(int originalStudentNo) {
        String sql = "DELETE FROM userdailymissions WHERE StudentNo = ?";
        return executeDelete(sql, originalStudentNo);
    }

    /**
     * 削除処理の共通化メソッド
     */
    private boolean executeDelete(String sql, int studentNo) {
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            
            pStmt.setInt(1, studentNo);
            pStmt.executeUpdate();
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 学籍番号によるユーザー検索
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
}