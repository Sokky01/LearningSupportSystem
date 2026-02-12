package servlet;

import java.io.IOException;

import dao.AdminLoginDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.AdminLoginDecisionLogic;
import logic.AdminLoginLogic;
import logic.AdminMissonLogic;
import model.AdminLoginBeans;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエスト：ログイン画面を表示するだけ
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ログイン画面へフォワード
        // ※ファイルを移動した場合はパスを "/WEB-INF/admin/login.jsp" などに変更してください
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * POSTリクエスト：ログイン処理（ID/PWの判定）を行う
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // リクエストパラメータの取得
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("AccountId");
        String password = request.getParameter("Password");

        // 1. IDの入力チェック
        if (idStr == null || idStr.length() == 0) {
            request.setAttribute("errorMsg", "IDを入力してください");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
            dispatcher.forward(request, response);
            return; // ★重要：ここで処理を終了させる
        }

        // 2. IDをintに変換（数字以外が入っていた場合のエラー処理）
        int accountId = 0;
        try {
            accountId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg", "IDは数値で入力してください");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
            dispatcher.forward(request, response);
            return; // ★重要
        }

        // Logicの呼び出し
        AdminLoginLogic adminLogic = new AdminLoginLogic();
        
        // 3. IDからユーザー情報を取得
        AdminLoginBeans loginBeans = adminLogic.findAdminById(accountId);

        // IDが存在しない場合
        if (loginBeans == null) {
            request.setAttribute("errorMsg", "IDが違います");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
            dispatcher.forward(request, response);
            return; // ★重要
        }

        // 学生(Role=0)の場合、ミッションロジックを実行
        if (loginBeans.getRole() == 0) {
            AdminMissonLogic.Mission(accountId);
        }

        // 4. パスワード判定
        AdminLoginDecisionLogic decisionLogic = new AdminLoginDecisionLogic();
        boolean pwResult = decisionLogic.pw(password, loginBeans);

        if (pwResult == false) {
            request.setAttribute("errorMsg", "パスワードが違います");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
            dispatcher.forward(request, response);
            return; // ★重要
        }

        // 5. 権限（Role）判定と画面遷移
        boolean isTeacher = decisionLogic.role(loginBeans);

        // セッションスコープに保存（共通）
        HttpSession session = request.getSession();
        session.setAttribute("Loginbeans", loginBeans);

        if (isTeacher == false) {
            // 生徒側へ遷移（Servletへ転送）
            RequestDispatcher dispatcher = request.getRequestDispatcher("/UserMainmenuServlet");
            dispatcher.forward(request, response);
        } else {
            // 教師側へ遷移
            // ※ファイルを移動した場合はパスを "/WEB-INF/admin/menu.jsp" などに変更してください
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminMainmenu.jsp");
            dispatcher.forward(request, response);
        }
    }
}