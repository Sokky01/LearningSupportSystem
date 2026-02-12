package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.UserUpdateLogic;
import model.userAccountListBeans;

@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// ① 一覧から studentNo を受け取る
        String sNo = request.getParameter("studentNo");
        int studentNo = Integer.parseInt(sNo);

        // ② DBから「1件」取得
        UserUpdateLogic logic = new UserUpdateLogic();
        userAccountListBeans user = logic.execute2(studentNo);

        // ③ JSPに渡す（名前は "user"）
        request.setAttribute("user", user);

        // ④ 画面遷移
        RequestDispatcher dispatcher =
            request.getRequestDispatcher("WEB-INF/userAccountUpdate.jsp");
        dispatcher.forward(request, response);
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");

        // １．リクエスト取得
        String sNo = request.getParameter("studentNo");
        
        // 学籍番号に全角が含まれているかチェック
        if (!sNo.matches("[0-9]+")) {
            // 数字以外（全角含む）がある場合、失敗画面へ
            request.setAttribute("errorMessage", "学籍番号は半角数字で入力してください。");
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("WEB-INF//userAccountUpdatefailed.jsp");
                dispatcher.forward(request, response);
            return;
        }
        
        int studentNo =Integer.parseInt(sNo);
        
      
		String aNo =request.getParameter("attendanceNo");
		
		  // 出席番号に全角が含まれているかチェック
        if (!aNo.matches("[0-9]+")) {
            // 数字以外（全角含む）がある場合、失敗画面へ
            request.setAttribute("errorMessage", "出席番号は半角数字で入力してください。");
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("WEB-INF//userAccountUpdatefailed.jsp");
                dispatcher.forward(request, response);
            return;
        }
		
        int attendanceNo =Integer.parseInt(aNo);        
        
        String classId = request.getParameter("classId");
        String studentName = request.getParameter("studentName");
        

        // 元の学籍番号（更新前のキー）
        String oNo =request.getParameter("originalStudentNo");
        int originalStudentNo = Integer.parseInt(oNo);
        
        

        // ２．Beansに保存
        userAccountListBeans beans =
            new userAccountListBeans(studentNo, attendanceNo, classId, studentName);

        // ３．Logic呼び出し
        UserUpdateLogic logic = new UserUpdateLogic();
        boolean result = logic.execute(beans,originalStudentNo);

        // ４． 判定によって画面遷移を変える
        if (result) {
        	// ④ 画面遷移
            RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF//userAccountUpdateConfirm.jsp");
            dispatcher.forward(request, response);
        } else {
        	//セッションスコープに保存
			   HttpSession session =request.getSession();
        	userAccountListBeans Login = (userAccountListBeans) session.getAttribute("result");
			if (Login != null) {
			    // Loginbeansを使用して処理
			    System.out.println("ログインID: " + Login.getStudentNo());
			} else {
			    System.out.println("ログイン情報がありません。");
			}
        	// ④ 画面遷移
            RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/userAccountUpdatefailed.jsp");
            dispatcher.forward(request, response);
        }
        
	}

}



/**
 * Servlet implementation class UserUpdateServlet
 */
/*
@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;*/
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//リクエストパラメーターの取得
		//リクエストパラメーターの取得
				request.setCharacterEncoding("UTF-8");
				String ClassId = request.getParameter("ClassId");
				
				String SNo =request.getParameter("StudentNo");
				int StudentNo =Integer.parseInt(SNo);
				String ANo =request.getParameter("AttendanceNo");
				int AttendanceNo =Integer.parseInt(ANo);
				String StudentName =request.getParameter("StudentName");
				
				userAccountUpdateBeans beans = new userAccountUpdateBeans(
					    StudentNo,
					    AttendanceNo,
					    ClassId,
					    StudentName
					);
		
		
		UserUpdateLogic Update =new UserUpdateLogic();
		List<userAccountUpdateBeans> list = Update.execute();
		
		
		
		//１．	userAccountUpdateの値を取得
		//セッション取得
		HttpSession session = request.getSession();

		//リクエストスコープに保存
		session.setAttribute("list", list);
		List<userAccountUpdateBeans> userUpdate = (List<userAccountUpdateBeans>)session.getAttribute("list");
		 RequestDispatcher dispatcher = request.getRequestDispatcher("userAccountUpdate.jsp");
		   dispatcher.forward(request, response);
	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//EmplnfoRegiser.javaのpostを参考に書いてください
		//リクエストパラメーターの取得
		request.setCharacterEncoding("UTF-8");
		String ClassId = request.getParameter("ClassId");
		
		String SNo =request.getParameter("StudentNo");
		int StudentNo =Integer.parseInt(SNo);
		String ANo =request.getParameter("AttendanceNo");
		int AttendanceNo =Integer.parseInt(ANo);
		String StudentName =request.getParameter("StudentName");
		
		userAccountUpdateBeans beans = new userAccountUpdateBeans(
			    StudentNo,
			    AttendanceNo,
			    ClassId,
			    StudentName
			);

	    UserUpdateLogic logic = new UserUpdateLogic();
	    UserUpdateInfoDAO dao = new UserUpdateInfoDAO();
		
		//セッション取得
		HttpSession session = request.getSession();
		List<userAccountUpdateBeans> userUpdate = (List<userAccountUpdateBeans>)session.getAttribute("list");
		
	  dao.update(beans);
	  
	  RequestDispatcher dispatcher = request.getRequestDispatcher("userAccountUpdate.jsp");
	   dispatcher.forward(request, response);
		/*
		 session.setAttribute("ClassId",ClassId);
		  session.setAttribute("StudentNo",StudentNo);
		 session.setAttribute("AttendanceNo",AttendanceNo);
		
		 session.setAttribute("StudentName",StudentName");
		 */
		
		
	    
	/*}

}*/
