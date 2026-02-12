<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ page import="java.util.List"%>
<%@ page import="model.userAccountListBeans" %>
<% 
// セッションスコープに保存されたuserAccountListBeansの取得
List<userAccountListBeans> userLogin = (List<userAccountListBeans>) session.getAttribute("userBeansList");
userAccountListBeans user = (userAccountListBeans) request.getAttribute("user");
%>

<title>削除確認</title>
<link rel="stylesheet" href="css/admin-theme.css">
<style>
/* 削除確認モーダル専用スタイル */
body {
	background: rgba(0, 0, 0, 0.5);
	display: flex;
	align-items: center;
	justify-content: center;
	min-height: 100vh;
	margin: 0;
}

.modal {
	display: flex;
	align-items: center;
	justify-content: center;
	width: 100%;
	padding: 20px;
}

.modal-window {
	max-width: 500px;
	width: 100%;
	background: var(--admin-bg-white);
	border: 2px solid var(--admin-danger);
	border-radius: 8px;
	padding: 40px;
	box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
}

.modal-icon {
	text-align: center;
	font-size: 64px;
	margin-bottom: 20px;
}

.modal-title {
	text-align: center;
	font-size: 24px;
	font-weight: 600;
	color: var(--admin-danger);
	margin-bottom: 20px;
}

.modal-info {
	background: var(--admin-bg-light);
	padding: 20px;
	border-radius: 4px;
	margin-bottom: 20px;
	font-size: 14px;
}

.modal-info p {
	margin: 8px 0;
	display: flex;
	justify-content: space-between;
}

.modal-info strong {
	color: var(--admin-text-muted);
	min-width: 100px;
}

.modal-message {
	text-align: center;
	color: var(--admin-danger);
	font-weight: 600;
	font-size: 16px;
	margin-bottom: 30px;
	padding: 15px;
	background: #f8d7da;
	border-radius: 4px;
}

.modal-buttons {
	display: flex;
	gap: 15px;
	justify-content: center;
}

.modal-buttons form {
	flex: 1;
}

.modal-buttons button {
	width: 100%;
	padding: 12px 20px;
	font-size: 15px;
	font-weight: 500;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s;
}

.modal-buttons form:first-child button {
	background: var(--admin-danger);
	color: white;
}

.modal-buttons form:first-child button:hover {
	background: #c82333;
	box-shadow: var(--admin-shadow-hover);
}

.modal-buttons form:last-child button {
	background: var(--admin-secondary);
	color: white;
}

.modal-buttons form:last-child button:hover {
	background: var(--admin-primary);
	box-shadow: var(--admin-shadow-hover);
}

@media (max-width: 768px) {
	.modal-window {
		padding: 30px 20px;
	}
	
	.modal-icon {
		font-size: 48px;
	}
	
	.modal-title {
		font-size: 20px;
	}
	
	.modal-buttons {
		flex-direction: column;
	}
}
</style>
</head>
<body>

<!-- 削除確認モーダル -->
<div class="modal">
	<div class="modal-window">
		<div class="modal-icon">⚠️</div>
		<div class="modal-title">削除確認</div>

		<div class="modal-info">
			<p><strong>学籍番号:</strong> <span><%= request.getParameter("studentNo") %></span></p>
			<p><strong>出席番号:</strong> <span><%= request.getParameter("attendanceNo") %></span></p>
			<p><strong>クラス:</strong> <span><%= request.getParameter("classId") %></span></p>
			<p><strong>名前:</strong> <span><%= request.getParameter("studentName") %></span></p>
		</div>

		<div class="modal-message">
			⚠️ 上記の利用者情報を削除します。<br>
			この操作は取り消せません。
		</div>

		<div class="modal-buttons">
			<!-- 確定ボタン -->
			<form action="UserDeleteServlet" method="post">
				<input type="hidden" name="studentNo" value="<%= request.getParameter("studentNo") %>">
				<input type="hidden" name="attendanceNo" value="<%= request.getParameter("attendanceNo") %>">
				<input type="hidden" name="classId" value="<%= request.getParameter("classId") %>">
				<input type="hidden" name="studentName" value="<%= request.getParameter("studentName") %>">
				<input type="hidden" name="originalStudentNo" value="<%= request.getParameter("studentNo") %>">
				<button type="submit">🗑️ 削除する</button>
			</form>

			<!-- キャンセルボタン -->
			<form action="UserViewServlet" method="post">
				<input type="hidden" name="studentNo" value="<%= request.getParameter("studentNo") %>">
				<input type="hidden" name="attendanceNo" value="<%= request.getParameter("attendanceNo") %>">
				<input type="hidden" name="classId" value="<%= request.getParameter("classId") %>">
				<input type="hidden" name="studentName" value="<%= request.getParameter("studentName") %>">
				<input type="hidden" name="originalStudentNo" value="<%= request.getParameter("studentNo") %>">
				<button type="submit">← キャンセル</button>
			</form>
		</div>
	</div>
</div>

</body>
</html>
