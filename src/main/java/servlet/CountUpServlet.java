package servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.LearningstarttransitionDAO;
import logic.CountUpLogic;
import logic.GradeGetLogic;
import logic.GradeUpdateLogic;
import model.AdminLoginBeans;
import model.LearningstarttransitionBeans;

@WebServlet("/CountUpServlet")
public class CountUpServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String subjectIdStr = request.getParameter("subjectId");
        HttpSession session = request.getSession();

        // subjectId が null でなければ session にセット
        Integer subjectId = null;
        if (subjectIdStr != null && !subjectIdStr.isEmpty()) {
            subjectId = Integer.parseInt(subjectIdStr);
            session.setAttribute("subjectId", subjectId);
        }

        // 開始時刻が未登録なら記録（初回遷移時のみ）
        if (session.getAttribute("startTime") == null) {
            session.setAttribute("startTime", LocalDateTime.now());
        }

        // DAO を使って科目名取得
        String subjectName = "";
        if (subjectId != null) {
            LearningstarttransitionDAO dao = new LearningstarttransitionDAO();
            LearningstarttransitionBeans subject = dao.getSubjectById(subjectId);
            if (subject != null) {
                subjectName = subject.getSubjectname();
            }
        }
        
        // ユーザーIDを取得
        AdminLoginBeans ALB = (AdminLoginBeans) session.getAttribute("Loginbeans");
        int userId = ALB.getAccountId();
        
        // gradeId取得（数字で取得）
        GradeGetLogic GGL = new GradeGetLogic();
        int gradeId = GGL.execute(userId);
        request.setAttribute("subjectName", subjectName);
        request.setAttribute("gradeId", gradeId);

        // 学習ツール JSP へフォワード
        request.getRequestDispatcher("/WEB-INF/learningTools.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // JSP から送られた学習時間（秒）を取得
        String timeStr = request.getParameter("learningTime");

        int learningTime = 0;
        if (timeStr != null && !timeStr.isEmpty()) {
            learningTime = Integer.parseInt(timeStr);
        }

        HttpSession session = request.getSession();

        // ユーザーIDを取得
        AdminLoginBeans ALB = (AdminLoginBeans) session.getAttribute("Loginbeans");
        int userId = ALB.getAccountId();

        // session から subjectId を安全に取得
        Object subjectObj = session.getAttribute("subjectId");
        Integer subjectId = null;

        if (subjectObj instanceof String) {
            subjectId = Integer.parseInt((String) subjectObj);
        } else if (subjectObj instanceof Integer) {
            subjectId = (Integer) subjectObj;
        }

        // subjectId が null の場合は安全のためエラー遷移
        if (subjectId == null) {
            response.sendRedirect("errorPage.jsp");
            return;
        }

        // logic を呼び出して DB に保存
        CountUpLogic CUL = new CountUpLogic();
        CUL.execute(learningTime, userId, subjectId);

        // ★ ミッションポイント累計によるグレード付与処理
        GradeUpdateLogic gradeUpdateLogic = new GradeUpdateLogic();
        gradeUpdateLogic.updateGradeByPoints(userId);

        // 完了後の遷移
        response.sendRedirect("Learningstarttransition");
    }
}