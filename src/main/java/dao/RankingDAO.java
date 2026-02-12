package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.RankingBeans;

public class RankingDAO extends DbConnect {

    public List<RankingBeans> rankingbeans() {
     
        List<RankingBeans> rankingList = new ArrayList<>();
        String sql = "SELECT upt.StudentNo, upt.PointTotal, um.NickName FROM userpointtotal upt JOIN usermaster um ON um.StudentNo = upt.StudentNo ORDER BY upt.PointTotal DESC;";

        // DB接続
        try (Connection conn = DbConnect.getConnection();
        		PreparedStatement pStmt = conn.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
            	RankingBeans rb = new RankingBeans();
            	rb.setStudentNo(rs.getInt("StudentNo"));
                rb.setNickName(rs.getString("NickName"));
                rb.setPointTotal(rs.getInt("PointTotal"));
                rankingList.add(rb);
            }

        } catch (SQLException e) {
            throw new RuntimeException("DBアクセス中にエラーが発生しました", e);
        }
        
        return rankingList;
    }
}