package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.DisplayStudyTimeGraphLogic;
import model.AdminLoginBeans;
import model.DisplayStudyTimeGraphBeans;

@WebServlet("/DisplayStudyTimeGraph")
public class DisplayStudyTimeGraphServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		DisplayStudyTimeGraphLogic DSTG = new DisplayStudyTimeGraphLogic();
		HttpSession session = request.getSession();
		AdminLoginBeans LoginBeans = (AdminLoginBeans) session.getAttribute("Loginbeans");
		int studentNo = LoginBeans.getAccountId();
		
		System.out.println("=== DisplayStudyTimeGraph ===");
		System.out.println("StudentNo: " + studentNo);
		
		List<DisplayStudyTimeGraphBeans> learningHistoryList = DSTG.execute(studentNo);
		
		// 学習履歴を JSON 文字列に変換
		String json = "[]";
		if (learningHistoryList != null && !learningHistoryList.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			
			System.out.println("--- 学習履歴データ ---");
			for (int i = 0; i < learningHistoryList.size(); i++) {
				DisplayStudyTimeGraphBeans b = learningHistoryList.get(i);
				String dateStr = b.getYearMonthDate() != null ? sdf.format(b.getYearMonthDate()) : "";
				
				// ★重要: LearningTime は「秒」なので「分」に変換
				int seconds = b.getLearningTime();
				int mins = seconds / 60;  // 秒 → 分
				
				System.out.println("日付: " + dateStr + ", 秒: " + seconds + ", 分: " + mins);
				
				String subj = null;
				try {
					// subjectName を取得
					java.lang.reflect.Method m = b.getClass().getMethod("getSubjectName");
					Object val = m.invoke(b);
					if (val != null) subj = val.toString();
				} catch (Exception e) {
					// getSubjectName が無ければ科目IDを使う
				}
				if (subj == null || subj.trim().isEmpty()) {
					subj = "科目ID:" + b.getSubjectId();
				}
				
				// JSON 要素を追加
				sb.append('{');
				sb.append("\"studyDate\":\"").append(dateStr).append("\"");
				sb.append(",\"minutes\":").append(mins);  // ★ここで分を出力
				sb.append(",\"subject\":\"").append(escapeJson(subj)).append("\"");
				sb.append('}');
				
				if (i < learningHistoryList.size() - 1) sb.append(',');
			}
			sb.append(']');
			json = sb.toString();
			
			System.out.println("JSON: " + json);
		}
		
		System.out.println("==========================");
		
		request.setAttribute("learningHistoryJson", json);
		request.setAttribute("learningHistoryList", learningHistoryList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/displayStudyTimeGraph.jsp");
		dispatcher.forward(request, response);
	}

	// 簡易的な JSON エスケープ
	private static String escapeJson(String s) {
		if (s == null) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\\': sb.append("\\\\"); break;
			case '"': sb.append("\\\""); break;
			case '\b': sb.append("\\b"); break;
			case '\f': sb.append("\\f"); break;
			case '\n': sb.append("\\n"); break;
			case '\r': sb.append("\\r"); break;
			case '\t': sb.append("\\t"); break;
			default:
				if (c < 0x20) {
					String hex = Integer.toHexString(c);
					sb.append("\\u");
					for (int k = hex.length(); k < 4; k++) sb.append('0');
					sb.append(hex);
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}
}
