package logic;

import java.util.List;
import java.util.Map;

import dao.RankingProfileDAO;
import model.RankingProfileBeans;

public class RankingProfileLogic {
    
    private static final RankingProfileDAO dao = new RankingProfileDAO();
    
    /**
     * 指定した学生が公開設定かチェック
     */
    public static boolean checkPublicPreference(int studentNo) {
        return dao.checkPublicPreference(studentNo);
    }
    
    /**
     * 指定した学生の基本プロフィールを取得
     */
    public static RankingProfileBeans getBasicProfile(int studentNo) {
        return dao.getBasicProfile(studentNo);
    }
    
    /**
     * 指定した学生の最近の学習記録を取得
     */
    public static List<Map<String, Object>> getRecentHistory(int studentNo) {
        return dao.getRecentLearningHistory(studentNo);
    }
    
    /**
     * 指定した学生の過去7日間の学習データを取得
     */
    public static List<Map<String, Object>> getWeeklyData(int studentNo) {
        return dao.getWeeklyLearningData(studentNo);
    }
}