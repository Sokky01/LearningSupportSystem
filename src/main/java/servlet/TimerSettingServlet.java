package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.GradeGetLogic;
import logic.LearningstarttransitionLogic;
import model.AdminLoginBeans;
import model.LearningstarttransitionBeans;

@WebServlet("/TimerSettingServlet")
public class TimerSettingServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LearningstarttransitionLogic LSTL = new LearningstarttransitionLogic();
		List<LearningstarttransitionBeans> subjectList = LSTL.execute();
		
		// ユーザーIDを取得してgradeIdをsessionに設定
		HttpSession session = request.getSession();
		AdminLoginBeans ALB = (AdminLoginBeans) session.getAttribute("Loginbeans");
		if (ALB != null) {
			int userId = ALB.getAccountId();
			GradeGetLogic GGL = new GradeGetLogic();
			int gradeId = GGL.execute(userId);
			session.setAttribute("gradeId", gradeId);
		}
		
		request.setAttribute("subjectList", subjectList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/timer.jsp");
		dispatcher.forward(request, response);
		
	}

}