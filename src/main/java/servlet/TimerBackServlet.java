package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/TimerBackServlet")
public class TimerBackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // タイマー情報だけリセットしたい場合
        session.removeAttribute("timerValue"); // timer.jsp で保持していた場合

        // subjectId は既に session に入っているのでそのまま保持
        Integer subjectId = (Integer) session.getAttribute("subjectId");
        if(subjectId == null) {
            // nullの場合は安全策としてエラー画面へ
            response.sendRedirect("errorPage.jsp");
            return;
        }

        // CountUpServlet の doGet へリダイレクト
        response.sendRedirect("CountUpServlet");
    }
}
