package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.DisplayMissionsLogic;
import model.AdminLoginBeans;
import model.DisplayMissionsBeansList;

@WebServlet("/DisplayMissionsServlet")
public class DisplayMissionsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		// セッションスコープからインスタンスを取得
		HttpSession session = request.getSession();
		
		// ★ Bean を取得 
	    AdminLoginBeans loginBeans = (AdminLoginBeans) session.getAttribute("Loginbeans"); 
	    // ★ Bean が null の場合（未ログイン or セッション切れ） 
	    if (loginBeans == null) { response.sendRedirect("AdminLoginServlet"); return; } 
	    // ★ Bean から studentNo を取得 
	    int studentNo = loginBeans.getAccountId();
		
		// ミッションのメインのlogicを呼び出す
		DisplayMissionsBeansList dmbl = DisplayMissionsLogic.missionMain(studentNo);

		// jspにrequestで渡す
		request.setAttribute("dmbl", dmbl);
		
		// displayMissions.jspへ遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/displayMissions.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
