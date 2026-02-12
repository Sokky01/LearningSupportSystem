package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.AccountInfoLogic;
import model.AdminLoginBeans;
import model.UserProfile;

// URLパターンは適宜変更してください
@WebServlet("/AccountInfoDisplayServlet")
public class AccountInfoDisplayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. セッションからログイン中のユーザーID (studentNo) を取得
        // ※ ログイン時に "loginUser" や "studentNo" という名前でセッションに保存している想定です
        HttpSession session = request.getSession();
        
        // ここでは仮に、セッションに "studentNo" という文字列が入っているとして取得します
        // 実際の実装に合わせて変更してください（例: UserBeansオブジェクトからgetIdするなど）
        //String studentNo = (String) session.getAttribute("studentNo");
        AdminLoginBeans login = (AdminLoginBeans) session.getAttribute("Loginbeans");        


        // 2. Logicを呼び出してプロフィール情報を取得
        AccountInfoLogic logic = new AccountInfoLogic();
        UserProfile profile = logic.getProfile(String.valueOf(login.getAccountId()));
        // 3. 取得したデータをリクエストスコープに保存 (名前は "profile" とします)
        request.setAttribute("profile", profile);

        // 4. JSPへフォワード (表示)
        request.getRequestDispatcher("/WEB-INF/account_info.jsp").forward(request, response);
    }
}