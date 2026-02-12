package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.SetGoalLogic;
import model.targetSettingBeans;

@WebServlet("/SetGoalServlet")
public class SetGoalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 目標設定画面表示（学習記録画面 → ここ）
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // studentNo は必ず GET パラメータから取得
        String studentNoStr = request.getParameter("studentNo");
        if (studentNoStr == null) {
            // studentNo が無ければ学習記録画面に戻す
            response.sendRedirect("UserViewLearningRecordServlet");
            return;
        }

        int studentNo = Integer.parseInt(studentNoStr);

        SetGoalLogic logic = new SetGoalLogic();

        // 科目一覧取得
        List<targetSettingBeans> subjectList =
            logic.getTargetSettingList(studentNo);

        request.setAttribute("studentNo", studentNo);
        request.setAttribute("subjectList", subjectList);

        request.getRequestDispatcher("/WEB-INF/targetSetting.jsp")
               .forward(request, response);
    }

    /**
     * 目標時間確定（設定画面 → DB更新）
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // パラメータを安全に取得
        String studentNoStr = request.getParameter("studentNo");
        String subjectIdStr = request.getParameter("subjectId");
        String goalHourStr = request.getParameter("goalHour");
        String goalMinuteStr = request.getParameter("goalMinute");

        // null チェック（戻るボタンや不正アクセス対策）
        if (studentNoStr == null || subjectIdStr == null 
                || goalHourStr == null || goalMinuteStr == null) {
            response.sendRedirect("UserViewLearningRecordServlet");
            return;
        }

        // パラメータを int に変換
        int studentNo = Integer.parseInt(studentNoStr);
        int subjectId = Integer.parseInt(subjectIdStr);
        int goalHour   = Integer.parseInt(goalHourStr);
        int goalMinute = Integer.parseInt(goalMinuteStr);

        int subjectGoal = goalHour * 60 + goalMinute;

        SetGoalLogic logic = new SetGoalLogic();
        logic.setGoal(studentNo, subjectId, subjectGoal);

        // 設定後は学習記録画面に戻す
        response.sendRedirect("UserViewLearningRecordServlet");
    }
}
