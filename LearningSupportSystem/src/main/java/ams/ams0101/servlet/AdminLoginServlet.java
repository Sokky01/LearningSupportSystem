package ams.ams0101.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ams.ams0101.logic.AdminLoginDecisionLogic;
import ams.ams0101.logic.AdminLoginLogic;
import ams.ams0101.logic.AdminMissonLogic;
import ams.ams0101.model.beans.AdminLoginBeans;
/**
 * Servlet implementation class AdminLoginLogic
 */
@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    /*public AdminLoginLogic() {
        super();
        // TODO Auto-generated constructor stub
    }*/

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//リクエストパラメータの取得
		

		request.setCharacterEncoding("UTF-8");
		String Id =request.getParameter("AccountId");
		
		//IDの入力値がなかった場合
				if(Id.length()==0) {
					//フォワード
					   RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.jsp");
					   dispatcher.forward(request, response);
				}
		
		String Password = request.getParameter("Password");
		
		
		
		
		//intに変換
		int AccountId =Integer.parseInt(Id);
		
		
		//IDをadminLogicへ送る 
		AdminLoginLogic adminlogin = new AdminLoginLogic();
		
		
		//3.adminLogincからadminLogicBeansを受け取る  
		AdminLoginBeans Loginbeans =adminlogin.findAdminById(AccountId);
		//AdminLoginBeans Loginbeansid =adminlogin.findAdminById2(AccountId);
		
		
		
		//AdminMissonLogicを呼び出す　
		AdminMissonLogic.Mission(AccountId);
		
		
		//pwもしくはAccountIdが間違っている場合
		if(Loginbeans==null){//フォワード
			   RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.jsp");
			   dispatcher.forward(request, response);
			}

		
		//4.pwとadminLogicBeansをadminLoginDecisionLogic送る
		AdminLoginDecisionLogic adminlogindecion = new AdminLoginDecisionLogic();
		
		boolean pwResult = adminlogindecion.pw(Password,Loginbeans);//pw判定
		boolean loginResult = adminlogindecion.role(Loginbeans);//password→ユーザーが入力したpw    
                                                                                   //Loginbeans→DBなどから取得したid,pwなどを持っているbeans
		
		
		//5.adminLoginDecisionLogicからTrueまたはFalseを受け取る
		if(pwResult == false) {
	
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.jsp");
			dispatcher.forward(request, response);
			
		}else if(loginResult == false){//生徒側へ遷移
			//セッションスコープに保存
			   HttpSession session =request.getSession();
			   session.setAttribute("Loginbeans", Loginbeans);
			   
			 
		
			//フォワード
			   RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/userMainmenu.jsp");
			   dispatcher.forward(request, response);
			
		}else if(loginResult == true) {//教師側へ遷移
			
            //セッションスコープに保存
		   HttpSession session =request.getSession();
		   session.setAttribute("Loginbeans", Loginbeans);
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/adminMainmenu.jsp");
			dispatcher.forward(request, response);
		}
		
		

		
				
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}