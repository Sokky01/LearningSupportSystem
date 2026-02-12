package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.HttpSession; // 今回は不要

import logic.AdminViewLogic;
import model.AdminAccountListBeans;

@WebServlet("/AdminViewServlet")
public class AdminViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. Logic呼び出し
		AdminViewLogic adminviewlogic = new AdminViewLogic();
		
		// 2. Logicからリストを受け取る
		List<AdminAccountListBeans> beansList = adminviewlogic.execute();
		
		// 3. デバッグ用：データが取れているかコンソールで確認
		if (beansList != null && !beansList.isEmpty()) {
			System.out.println("データ取得成功: " + beansList.size() + "件");
			System.out.println("先頭のID: " + beansList.get(0).getManagerId());
		} else {
			System.out.println("データが空、または取得失敗（null）です。");
			// ここが出る場合、DAOでエラーが起きている可能性があります（コンソールの赤文字を確認してください）
		}

		// ★重要★ リクエストスコープに保存
		// JSP側で "Login" という名前で取り出せるようにセットする
		request.setAttribute("accountList", beansList);
		
		// 4. JSPへフォワード
		// ※フォルダ階層を変えた場合はパスを修正してください（例: /WEB-INF/admin/accountList.jspなど）
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accountManagement.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 今回は使わない想定
		doGet(request, response);
	}
}
