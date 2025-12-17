
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainmenuServlet
 */
@WebServlet("/UserMainmenuServlet")
public class UserMainmenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserMainmenuServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/userMainmenu.jsp");

        dispatcher.forward(request, response);
    }
}
