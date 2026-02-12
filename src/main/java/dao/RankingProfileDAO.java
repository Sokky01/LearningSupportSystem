package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.RankingProfileBeans;

public class RankingProfileDAO {
    
    /**
     * 指定した学生の公開設定をチェック
     * @return true: 公開(1), false: 非公開(0)
     */
    public boolean checkPublicPreference(int studentNo) {
        String sql = "SELECT PublicPreference FROM userMaster WHERE StudentNo = ?";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentNo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int publicPreference = rs.getInt("PublicPreference");
                    return publicPreference == 1; // 1なら公開
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // データが見つからない、またはエラーの場合は非公開扱い
        return false;
    }
    
    /**
     * 指定した学生のプロフィール情報を取得
     */
    public RankingProfileBeans getBasicProfile(int studentNo) {
        RankingProfileBeans profile = new RankingProfileBeans();
        
        String sql = "SELECT um.StudentNo, um.NickName, um.MaxGrade, gm.GradeName, " +
                     "COALESCE(upt.PointTotal, 0) AS PointTotal " +
                     "FROM userMaster um " +
                     "LEFT JOIN userPointTotal upt ON um.StudentNo = upt.StudentNo " +
                     "LEFT JOIN gradeMaster gm ON um.MaxGrade = gm.GradeId " +
                     "WHERE um.StudentNo = ?";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentNo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    profile.setStudentNo(rs.getInt("StudentNo"));
                    profile.setNickName(rs.getString("NickName"));
                    profile.setMaxGrade(rs.getInt("MaxGrade"));
                    profile.setGradeName(rs.getString("GradeName"));
                    profile.setPointTotal(rs.getInt("PointTotal"));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return profile;
    }
    
    /**
     * 指定した学生の過去1週間の学習記録を取得（今日から過去7日間）
     */
    public List<Map<String, Object>> getRecentLearningHistory(int studentNo) {
        List<Map<String, Object>> historyList = new ArrayList<>();
        
        // 今日から過去6日間 = 合計7日間のデータを取得
        String sql = "SELECT lh.YearMonthDate, sm.SubjectName, lh.LearningTime " +
                     "FROM learningHistory lh " +
                     "JOIN subjectMaster sm ON lh.SubjectId = sm.SubjectId " +
                     "WHERE lh.StudentNo = ? " +
                     "AND lh.YearMonthDate >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) " +
                     "AND lh.YearMonthDate <= CURDATE() " +
                     "ORDER BY lh.YearMonthDate DESC, lh.LearningHistoryId DESC";
        
        System.out.println("=== getRecentLearningHistory Debug ===");
        System.out.println("StudentNo: " + studentNo);
        System.out.println("Today: " + java.time.LocalDate.now());
        System.out.println("7 days ago: " + java.time.LocalDate.now().minusDays(6));
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentNo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    Map<String, Object> record = new HashMap<>();
                    java.sql.Date date = rs.getDate("YearMonthDate");
                    record.put("date", date);
                    record.put("subject", rs.getString("SubjectName"));
                    record.put("learningTime", rs.getInt("LearningTime"));
                    record.put("point", rs.getInt("LearningTime"));
                    historyList.add(record);
                    
                    System.out.println("Record " + count + ": Date=" + date + 
                                     ", Subject=" + rs.getString("SubjectName") + 
                                     ", Time=" + rs.getInt("LearningTime"));
                }
                System.out.println("Total records found: " + count);
            }
            
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return historyList;
    }
    
    /**
     * 指定した学生の過去7日間の学習時間データを取得（グラフ用）
     */
    public List<Map<String, Object>> getWeeklyLearningData(int studentNo) {
        List<Map<String, Object>> weeklyData = new ArrayList<>();
        
        // 今日から過去6日間 = 合計7日間のデータを取得
        String sql = "SELECT YearMonthDate, SUM(LearningTime) AS TotalTime " +
                     "FROM learningHistory " +
                     "WHERE StudentNo = ? " +
                     "AND YearMonthDate >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) " +
                     "AND YearMonthDate <= CURDATE() " +
                     "GROUP BY YearMonthDate " +
                     "ORDER BY YearMonthDate ASC";
        
        try (Connection conn = DbConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentNo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("date", rs.getDate("YearMonthDate"));
                    data.put("totalTime", rs.getInt("TotalTime"));
                    weeklyData.add(data);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return weeklyData;
    }
}