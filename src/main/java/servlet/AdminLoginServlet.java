package servlet;

import java.io.IOException;

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

        // 1. IDの入力チェック（null または 空文字）
        if (idStr == null || idStr.trim().length() == 0) {
            request.setAttribute("errorMsg", "IDを入力してください");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 2. IDをintに変換（数字以外が入っていた場合のエラー処理）
        int accountId = 0;
        try {
            accountId = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg", "IDは数値で入力してください");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 3. IDからユーザー情報を取得
        //    ★修正: DB接続エラーなど例外が発生した場合も
        //            500エラーにならないよう try-catch で捕捉する
        AdminLoginLogic adminLogic = new AdminLoginLogic();
        AdminLoginBeans loginBeans = null;

        try {
            loginBeans = adminLogic.findAdminById(accountId);
        } catch (Exception e) {
            // DB接続失敗など予期しないエラー
            request.setAttribute("errorMsg", "IDが違います");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // IDが存在しない場合（DB検索結果が null）
        if (loginBeans == null) {
            request.setAttribute("errorMsg", "IDが違います");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 4. 学生(Role=0)の場合、ミッションロジックを実行
        //    ★修正: Mission() が例外を投げても 500エラーにならないよう try-catch で捕捉する
        if (loginBeans.getRole() == 0) {
            try {
                AdminMissonLogic.Mission(accountId);
            } catch (Exception e) {
                // ミッション処理が失敗してもログイン自体は続行する
                System.err.println("[AdminLoginServlet] AdminMissonLogic.Mission() でエラー発生: " + e.getMessage());
            }
        }

        // 5. パスワード判定
        AdminLoginDecisionLogic decisionLogic = new AdminLoginDecisionLogic();
        boolean pwResult = false;

        try {
            pwResult = decisionLogic.pw(password, loginBeans);
        } catch (Exception e) {
            request.setAttribute("errorMsg", "システムエラーが発生しました。管理者に連絡してください。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (!pwResult) {
            request.setAttribute("errorMsg", "パスワードが違います");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminLogin.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 6. 権限（Role）判定と画面遷移
        boolean isTeacher = decisionLogic.role(loginBeans);

        // セッションスコープに保存（共通）
        HttpSession session = request.getSession();
        session.setAttribute("Loginbeans", loginBeans);

        if (!isTeacher) {
            // 生徒側へ遷移（Servletへ転送）
            RequestDispatcher dispatcher = request.getRequestDispatcher("/UserMainmenuServlet");
            dispatcher.forward(request, response);
        } else {
            // 教師側へ遷移
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/adminMainmenu.jsp");
            dispatcher.forward(request, response);
        }
    }
}