package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeUpdateDAO {

    /**
     * ユーザーの現在のポイント累計を取得する
     * @param studentNo 学籍番号
     * @return ポイント累計（データが無い場合は0）
     */
    public int getPointTotal(int studentNo) {
        String sql = "SELECT COALESCE(PointTotal, 0) AS PointTotal FROM userPointTotal WHERE StudentNo = ?";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentNo);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("PointTotal");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    /**
     * ユーザーの現在のSettingGradeIdを取得する
     * @param studentNo 学籍番号
     * @return 現在のSettingGradeId（データが無い場合は1）
     */
    public int getCurrentSettingGradeId(int studentNo) {
        String sql = "SELECT SettingGradeId FROM userMaster WHERE StudentNo = ?";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentNo);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("SettingGradeId");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 1; // デフォルトはブロンズ
    }

    /**
     * ユーザーのMaxGradeを取得する（達成済み最高グレード）
     * @param studentNo 学籍番号
     * @return 現在のMaxGrade（データが無い場合は1）
     */
    public int getCurrentMaxGrade(int studentNo) {
        String sql = "SELECT MaxGrade FROM userMaster WHERE StudentNo = ?";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentNo);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MaxGrade");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 1; // デフォルトはブロンズ
    }

    /**
     * ユーザーのMaxGradeとSettingGradeIdを両方更新する
     * MaxGradeは現在より上位の場合のみ更新
     * SettingGradeIdは常に新しいグレードに更新（表示用）
     * 
     * @param studentNo 学籍番号
     * @param newGradeId 新しいグレードID
     * @return 更新された行数
     */
    public int updateGrade(int studentNo, int newGradeId) {
        String sql = "UPDATE userMaster SET MaxGrade = GREATEST(MaxGrade, ?), SettingGradeId = ? WHERE StudentNo = ?";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, newGradeId);
            pstmt.setInt(2, newGradeId);
            pstmt.setInt(3, studentNo);
            
            return pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}