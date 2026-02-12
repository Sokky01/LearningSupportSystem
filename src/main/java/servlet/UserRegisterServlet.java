package servlet;

import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import logic.UserRegisterLogic;
@WebServlet("/UserRegisterServlet")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024, // 1MB以上のファイルはディスクに保存
	    maxFileSize = 5 * 1024 * 1024,   // 最大ファイルサイズ 5MB
	    maxRequestSize = 10 * 1024 * 1024 // 最大リクエストサイズ 10MB
	)
public class UserRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 一覧表示（GET）
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	   System.out.println("UserRegisterServlet#doGet に入りました");
    	
    	// ユーザー一覧取得
       // List<UserRegisterBeans> list = new UserList().getUserLoginList();

        // sessionに保存
        HttpSession session = request.getSession();
        //session.setAttribute("userList", list);

        // JSPにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/userAccountRegister.jsp");
        dispatcher.forward(request, response);
    }

    // CSV登録などの処理（POST）
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	 //filePart はユーザーがアップロードしたCSVファイルの情報（データ＋メタ情報）を持つ
    	 Part filePart = request.getPart("csvFile");
    	 //中身を読み取るためのバイトストリーム(ファイルの中身が一気に全部ではなく、順番に読み取られていきます)
         InputStream is = filePart.getInputStream();
         
         

         UserRegisterLogic logic = new UserRegisterLogic();
         logic.registerCsv(is);

         response.sendRedirect(request.getContextPath() + "/AdminViewServlet");
    }
    
}
