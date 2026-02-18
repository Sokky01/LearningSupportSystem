package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.account_infoDao;
import logic.AccountInfoLogic;
import model.AdminLoginBeans; // 【追加】これが必要です！
import model.UserProfile;

@WebServlet("/AccountEditServlet")
public class AccountEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AccountEditServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. セッションから Loginbeans を取得
		HttpSession session = request.getSession();
		AdminLoginBeans loginBeans = (AdminLoginBeans) session.getAttribute("Loginbeans");

		String studentNo = null;

		// ログイン済みかチェック
		if (loginBeans != null) {
			// BeansからIDを取り出し、String型に変換（getProfileがStringを求めているため）
			// ※ getAccountId() の部分は、実際のBeansのメソッド名に合わせてください！
			studentNo = String.valueOf(loginBeans.getAccountId());
		} else {
			// 未ログインの場合の処理（リダイレクトなど）
			response.sendRedirect("AdminLoginServlet");
			return;
		}

		// 2. 現在の情報を取得
		AccountInfoLogic logic = new AccountInfoLogic();
		UserProfile profile = logic.getProfile(studentNo);

		// 3. お盆（リクエストスコープ）に乗せる
		request.setAttribute("profile", profile);

		// 4. 作成した編集画面へフォワード！
		request.getRequestDispatcher("/WEB-INF/account_edit.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// ■■ doPostでも同様にセッションからIDを取ります ■■
		HttpSession session = request.getSession();
		AdminLoginBeans loginBeans = (AdminLoginBeans) session.getAttribute("Loginbeans");
		
		if (loginBeans == null) {
			response.sendRedirect("AdminLoginServlet");
			return;
		}
		
		// セッションのBeansからIDを取得
		// ※ getAccountId() の部分は実際のメソッド名に合わせてください
		String studentId = String.valueOf(loginBeans.getAccountId());

		// （以下、フォーム入力値の取得）
		String studentName = request.getParameter("studentName");
		String nickName = request.getParameter("nickName");
		String publicPreference = request.getParameter("publicPreference");
		String settingGradeId = request.getParameter("settingGradeId");
		String maxGrade = request.getParameter("maxGrade");
		String currentPass = request.getParameter("currentPassword");
		String newPass = request.getParameter("newPassword");
		String confirmPass = request.getParameter("confirmNewPassword");

		// Beansにまとめる
		UserProfile user = new UserProfile();
		user.setStudentId(studentId); // セッションから取った安全なIDをセット
		user.setStudentName(studentName);
		user.setNickName(nickName);
		user.setPublicPreference(publicPreference);
		user.setSettingGradeId(settingGradeId);
		user.setMaxGrade(maxGrade);

		AccountInfoLogic logic = new AccountInfoLogic();
		
		// DBから現在のパスワード取得
		UserProfile currentProfile = logic.getProfile(studentId);
		String dbPass = (currentProfile != null) ? currentProfile.getPassword() : "";

		// バリデーション実行
		String errorMsg = logic.validate(user, currentPass, newPass, confirmPass, dbPass);

		if (errorMsg == null) {
			account_infoDao dao = new account_infoDao();
			if (!dao.updateAccount(user)) {
				errorMsg = "データベースの更新に失敗しました。";
			} else {
				// DB更新成功後、セッションのニックネームも更新する
				loginBeans.setNickName(nickName);
				session.setAttribute("Loginbeans", loginBeans);
			}
		}

		if (errorMsg == null) {
			response.sendRedirect("AccountInfoDisplayServlet");
		} else {
			request.setAttribute("errorMsg", errorMsg);
			request.setAttribute("profile", user);
			request.getRequestDispatcher("/WEB-INF/account_edit.jsp").forward(request, response);
		}
	}
}