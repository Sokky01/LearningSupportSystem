package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.RankingProfileLogic;
import model.RankingProfileBeans;

@WebServlet("/NavigateToLearningRecordPageServlet")
public class NavigateToLearningRecordPageServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // パラメータから学生番号を取得
        String studentNoStr = request.getParameter("studentNo");
        
        if (studentNoStr == null || studentNoStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/DisplayRankingServlet");
            return;
        }
        
        try {
            int studentNo = Integer.parseInt(studentNoStr);
            
            // 公開設定をチェック
            boolean isPublic = RankingProfileLogic.checkPublicPreference(studentNo);
            
            if (!isPublic) {
                // 非公開の場合はセッションにエラーメッセージを設定してランキングに戻す
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "このユーザーは学習記録を非公開に設定しています。");
                response.sendRedirect(request.getContextPath() + "/DisplayRankingServlet");
                return;
            }
            
            // 基本プロフィールを取得
            RankingProfileBeans profile = RankingProfileLogic.getBasicProfile(studentNo);
            
            // 最近の学習記録を取得
            List<Map<String, Object>> recentHistory = RankingProfileLogic.getRecentHistory(studentNo);
            
            // 過去7日間のデータを取得
            List<Map<String, Object>> weeklyData = RankingProfileLogic.getWeeklyData(studentNo);
            
            // リクエストスコープに設定
            request.setAttribute("profile", profile);
            request.setAttribute("recentHistory", recentHistory);
            request.setAttribute("weeklyData", weeklyData);
            
            // JSPへフォワード
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/rankingProfile.jsp");
            dispatcher.forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/DisplayRankingServlet");
        }
    }
}