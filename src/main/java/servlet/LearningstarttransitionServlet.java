package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.LearningstarttransitionLogic;
import model.LearningstarttransitionBeans;

@WebServlet("/Learningstarttransition")
public class LearningstarttransitionServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LearningstarttransitionLogic LSTL = new LearningstarttransitionLogic();
		List<LearningstarttransitionBeans> subjectList = LSTL.execute();
		
		request.setAttribute("subjectList", subjectList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/learningStart.jsp");
		dispatcher.forward(request, response);
		
	}
}