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

import logic.DisplayRankingLogic;
import model.AdminLoginBeans;
import model.RankingBeans;

@WebServlet("/DisplayRankingServlet")
public class DisplayRankingServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    HttpSession session = request.getSession();

	    // ★ Bean を取得 
	    AdminLoginBeans loginBeans = (AdminLoginBeans) session.getAttribute("Loginbeans"); 
	    // ★ Bean が null の場合（未ログイン or セッション切れ） 
	    if (loginBeans == null) { response.sendRedirect("AdminLoginServlet"); return; } 
	    // ★ Bean から studentNo を取得 
	    int studentNo = loginBeans.getAccountId();

	    // ランキング取得
	    List<RankingBeans> rankingList = DisplayRankingLogic.rankingMain();

	    RankingBeans myRanking = null;
	    Integer myRank = null;

	    int rank = 1;
	    for (RankingBeans r : rankingList) {
	        if (r.getStudentNo() == studentNo) {
	            myRanking = r;
	            myRank = rank;
	            break;
	        }
	        rank++;
	    }

	    request.setAttribute("rankingList", rankingList);
	    request.setAttribute("myRanking", myRanking);
	    request.setAttribute("myRank", myRank);

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/userManagementRanking.jsp");
	    dispatcher.forward(request, response);
	}

}