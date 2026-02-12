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

import logic.UserDeleteLogic;
import logic.UserUpdateLogic;
import model.userAccountListBeans;

@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 一覧表示（GET）
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	   System.out.println("UserDeleteServlet#doGet に入りました");
    	   
    	   
    	   request.setCharacterEncoding("UTF-8");
    	   
    	// ① 一覧から studentNo を受け取る
           String sNo = request.getParameter("studentNo");
          

           if (sNo != null) {
        	   int studentNo = Integer.parseInt(sNo);
			    // Loginbeansを使用して処理
			    System.out.println("ログインID: " + studentNo);
			    
			 // ② DBから「1件」取得
		           UserUpdateLogic logic = new UserUpdateLogic();
		           userAccountListBeans user = logic.execute2(studentNo);

		    	// ③ JSPに渡す（名前は "user"）
		           request.setAttribute("user", user);
		           
			    
			} else {
			    System.out.println("ログイン情報がありません。");
			}
           
    	   
    	
           
           // ④ 画面遷移
           RequestDispatcher dispatcher =
               request.getRequestDispatcher("WEB-INF//userAccountDelete.jsp");
           dispatcher.forward(request, response);
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 System.out.println("UserDeleteServlet#do に入りました");
		// ① 一覧から studentNo を受け取る
		 request.setCharacterEncoding("UTF-8");
        // 元の学籍番号（更新前のキー）
		 String sNo = request.getParameter("studentNo");
        int originalStudentNo = Integer.parseInt(sNo);
        System.out.print(originalStudentNo);
         HttpSession session =request.getSession();
        List<userAccountListBeans>  userLogin = (List<userAccountListBeans> ) session.getAttribute("userBeansList");
    	userAccountListBeans user = (userAccountListBeans) request.getAttribute("user");
             
        // ② DBから「1件」取得
        UserDeleteLogic logic = new UserDeleteLogic();
        System.out.print(sNo);
        boolean delete1 = logic.execute3(originalStudentNo);
        boolean delete2 = logic.execute4(originalStudentNo);
        boolean delete3 = logic.execute5(originalStudentNo);
        boolean delete4 = logic.execute6(originalStudentNo);
        boolean result = logic.execute(originalStudentNo);
        
       
        
        
 	   

 	//フォワード
	   RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/accountManagement.jsp");
	   dispatcher.forward(request, response);
	
	}
	
}
