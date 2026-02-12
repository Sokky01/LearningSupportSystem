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

import dao.UserLearningViewDao;
import model.AdminLoginBeans;
import model.userLearningRecordBeans;

@WebServlet("/UserViewLearningRecordServlet")
public class UserViewLearningRecordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // セッションから学生番号を取得
        HttpSession session = request.getSession();
        AdminLoginBeans login = (AdminLoginBeans) session.getAttribute("Loginbeans");
        
        if (login == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }
        
        int studentNo = login.getAccountId();
        
        System.out.println("=== UserViewLearningRecordServlet ===");
        System.out.println("StudentNo: " + studentNo);
        
        // DAOから学習記録を取得
        UserLearningViewDao dao = new UserLearningViewDao();
        List<userLearningRecordBeans> userRecordList = dao.getUserLearningRecord(studentNo);
        
        // ★重要: ビューの累計を使わず、表示科目から累計を計算
        int totalStudyTime = 0;  // 秒
        int totalGoalTime = 0;   // 秒
        
        System.out.println("--- 科目別データ ---");
        for (userLearningRecordBeans record : userRecordList) {
            int subjectTotal = record.getSubjectTotal();  // 秒
            int subjectGoal = record.getSubjectGoal();    // 分
            
            System.out.println("科目: " + record.getSubjectName());
            System.out.println("  学習時間: " + subjectTotal + "秒 = " + formatSeconds(subjectTotal));
            System.out.println("  目標時間: " + subjectGoal + "分 = " + formatMinutes(subjectGoal));
            
            totalStudyTime += subjectTotal;           // 秒を加算
            totalGoalTime += (subjectGoal * 60);      // 分→秒に変換して加算
        }
        
        System.out.println("--- 累計（計算結果） ---");
        System.out.println("累計学習時間: " + totalStudyTime + "秒 = " + formatSeconds(totalStudyTime));
        System.out.println("累計目標時間: " + totalGoalTime + "秒 = " + formatSeconds(totalGoalTime));
        System.out.println("==========================================");
        
        // リクエスト属性に設定
        request.setAttribute("userRecordList", userRecordList);
        request.setAttribute("totalStudyTime", totalStudyTime);   // 秒
        request.setAttribute("totalGoalTime", totalGoalTime);     // 秒
        
        // JSPにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/userLearningRecordView.jsp");
        dispatcher.forward(request, response);
    }
    
    // デバッグ用: 秒を時間表示に変換
    private String formatSeconds(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        
        if (hours > 0) {
            return hours + "時間" + minutes + "分" + secs + "秒";
        } else if (minutes > 0) {
            return minutes + "分" + secs + "秒";
        } else {
            return secs + "秒";
        }
    }
    
    // デバッグ用: 分を時間表示に変換
    private String formatMinutes(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        
        if (hours > 0 && mins > 0) {
            return hours + "時間" + mins + "分";
        } else if (hours > 0) {
            return hours + "時間";
        } else {
            return mins + "分";
        }
    }
}