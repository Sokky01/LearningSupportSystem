package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.DeleteLearningRecordLogic;
import model.DeleteLearningRecordBeans;

@WebServlet("/DeleteLearningRecordServlet")
public class DeleteLearningRecordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int studentNo = Integer.parseInt(request.getParameter("studentNo"));
        String[] subjectIds = request.getParameterValues("subjectIds");

        if (subjectIds != null && subjectIds.length > 0) {

            DeleteLearningRecordLogic logic = new DeleteLearningRecordLogic();

            for (String subjectId : subjectIds) {
                DeleteLearningRecordBeans bean =
                        new DeleteLearningRecordBeans(studentNo, Integer.parseInt(subjectId));

                logic.execute(bean);
            }
        }

        // ★ 画面遷移なし：学習記録閲覧画面を再表示
        response.sendRedirect("UserViewLearningRecordServlet");
    }
}
