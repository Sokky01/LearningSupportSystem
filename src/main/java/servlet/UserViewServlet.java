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

import logic.UserViewLogic;
import model.userAccountListBeans;

/**
 * Servlet implementation class UserViewServlet
 */
@WebServlet("/UserViewServlet")
public class UserViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//ドロップダウンリストの値を取得
		String schoolGradeName = request.getParameter("schoolGradeName");
		
		
		//1.AdminViewLogic呼び出し
		UserViewLogic adminviewlogic = new UserViewLogic();
				
				//ここから先管理者のコード引っ張ってきただけだから利用者用に書き直す
				//2.AdminViewLogicからBeans受け取り
				List<userAccountListBeans> userBeansList = adminviewlogic.execute();
				
				//セッション取得
				HttpSession session = request.getSession();

				//リクエストスコープに保存
				session.setAttribute("userBeansList", userBeansList);
				
				List<userAccountListBeans>  userLogin = (List<userAccountListBeans> ) session.getAttribute("userBeansList");
				
				
				
				
				if (userLogin != null) {
					int i=0;
					while(i==4) {
						 
						userAccountListBeans userLogin1 = userLogin.get(i);
					    // Loginbeansを使用して処理
						int StudentNo1= userLogin1.getStudentNo();
						
						//検索できればやる
						//ドロップダウンリストのクラスが選択されてるときに実行する
						if(schoolGradeName != null) {//ここに入らない
							System.out.println("ログインID: " + userLogin1.getStudentNo());
							//ロジックの呼び出し
							UserViewLogic logic = new UserViewLogic();
							List<userAccountListBeans> recordList = logic.execute2(schoolGradeName,StudentNo1); //取得メソッド
							
							//リクエストスコープにセット
							request.setAttribute("recordList",recordList);
							
						}
						 
						 i++;
						
					}
					
					
					
				   
				} else {
				    System.out.println("ログイン情報がありません。");
				    //こっちが出てくるつまりLoginには何も入ってない
				    //Daoが原因？ManagerListVirtualがHQLで入らない
				}
				
				
				
				//adminAccoountLstを表示させる
				
				   RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/userAccountList.jsp");
				   dispatcher.forward(request, response);
			}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
