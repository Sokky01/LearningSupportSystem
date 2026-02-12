package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AdminAccountListBeans;

public class AdminViewInfoDao {

    /**
     * 管理者一覧情報を取得する
     * @return 管理者情報のリスト
     */
    public List<AdminAccountListBeans> findAll() {
        List<AdminAccountListBeans> beansList = new ArrayList<>();
        
        // SQL文（読みやすさのため、テーブル名のプレフィックスを整理）
        String sql = "SELECT ManagerId, ManagerName, ClassId, SchoolGrade, "
                   + "SchoolGradeName, SchoolGr, MajorId, MajorName FROM ManagerListVirtual";

        // DbConnectから接続を取得し、try-with-resourcesで確実にクローズする
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql);
             ResultSet rs = pStmt.executeQuery()) {

            // SELECT文の結果をArrayListに格納
            while (rs.next()) {
                int managerId = rs.getInt("ManagerId");
                String managerName = rs.getString("ManagerName");
                String classId = rs.getString("ClassId");
                String schoolGrade = rs.getString("SchoolGrade");
                String schoolGradeName = rs.getString("SchoolGradeName");
                String schoolGr = rs.getString("SchoolGr");
                String majorId = rs.getString("MajorId");
                String majorName = rs.getString("MajorName");

                AdminAccountListBeans adminAccount = new AdminAccountListBeans(
                    managerId, managerName, classId, schoolGrade, 
                    schoolGradeName, schoolGr, majorId, majorName
                );
                beansList.add(adminAccount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // エラー時はnullを返す（呼び出し側でハンドリングが必要）
        }
        return beansList;
    }
}