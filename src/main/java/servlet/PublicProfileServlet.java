package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.RankingBeans;

@WebServlet("/PublicProfileServlet")
public class PublicProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. URLパラメータから studentNo を取得
        String studentNoStr = request.getParameter("studentNo");

        if (studentNoStr == null || studentNoStr.isEmpty()) {
            response.sendRedirect("DisplayRankingServlet");
            return;
        }
        int targetId = Integer.parseInt(studentNoStr);

        // 2. セッションスコープからランキングリストを取得
        HttpSession session = request.getSession();
        List<RankingBeans> rankingList = (List<RankingBeans>) session.getAttribute("rankingList");

        RankingBeans profile = null;

        // 3. リストの中から学籍番号が一致する人を探す
        if (rankingList != null) {
            for (RankingBeans r : rankingList) {
                if (r.getStudentNo() == targetId) {
                    profile = r; // すでに PointTotal が入っている Beans をそのまま使う
                    break;
                }
            }
        }

        // 4. リクエストスコープに保存してJSPへ
        request.setAttribute("profile", profile);
        request.getRequestDispatcher("/WEB-INF/public_profile.jsp").forward(request, response);
    }
}