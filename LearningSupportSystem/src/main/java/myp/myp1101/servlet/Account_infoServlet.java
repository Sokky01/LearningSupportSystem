package myp.myp1101.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lrs.lrs0701.logic.LearningstarttransitionLogic;
import lrs.lrs0701.model.LearningstarttransitionBeans;

@WebServlet("/Account_info")
public class Account_infoServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LearningstarttransitionLogic LSTL = new LearningstarttransitionLogic();
		List<LearningstarttransitionBeans> subjectList = LSTL.execute();
		
		request.setAttribute("subjectList", subjectList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/account_info.jsp");
		dispatcher.forward(request, response);
		
	}
}