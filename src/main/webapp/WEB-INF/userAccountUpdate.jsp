<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List"%>
<%@ page import="model.userAccountListBeans" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">

<% 
// セッションスコープに保存されたuserAccountListBeansの取得
List<userAccountListBeans> userLogin = (List<userAccountListBeans>) session.getAttribute("userBeansList");
userAccountListBeans user = (userAccountListBeans) request.getAttribute("user");
%>

<title>利用者アカウント更新</title>
<link rel="stylesheet" href="css/admin-theme.css">
<style>
/* 更新画面専用スタイル */
.page-header {
	background: var(--admin-bg-white);
	padding: 20px;
	margin-bottom: 20px;
	border-bottom: 2px solid var(--admin-border);
}

.page-header h2 {
	margin: 0 0 10px 0;
	padding: 0;
	border: none;
	font-size: 24px;
}

.update-container {
	max-width: 1000px;
	margin: 20px auto;
	background: var(--admin-bg-white);
	border: 1px solid var(--admin-border);
	border-radius: 4px;
	padding: 30px;
	box-shadow: var(--admin-shadow);
}

.message-area {
	background: var(--admin-bg-light);
	padding: 15px;
	border-left: 4px solid var(--admin-accent);
	margin-bottom: 30px;
	font-size: 14px;
	color: var(--admin-text);
}

.form-area {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 30px;
	margin-bottom: 30px;
}

.before, .after {
	background: var(--admin-bg-light);
	padding: 20px;
	border-radius: 4px;
	border: 1px solid var(--admin-border);
}

.before h3, .after h3 {
	margin: 0 0 20px 0;
	padding-bottom: 10px;
	border-bottom: 2px solid var(--admin-border);
	font-size: 18px;
	color: var(--admin-text);
}

.before p {
	margin: 15px 0;
	padding: 10px;
	background: var(--admin-bg-white);
	border-radius: 4px;
	font-size: 14px;
}

.before p strong {
	display: block;
	color: var(--admin-text-muted);
	font-size: 12px;
	margin-bottom: 5px;
}

.after label {
	display: block;
	font-weight: 500;
	color: var(--admin-text);
	margin: 15px 0 5px 0;
	font-size: 14px;
}

.after input[type="text"],
.after select {
	width: 100%;
	padding: 8px 12px;
	border: 1px solid var(--admin-border);
	border-radius: 4px;
	font-size: 14px;
	background: var(--admin-bg-white);
}

.button-group {
	display: flex;
	justify-content: center;
	gap: 15px;
	margin-top: 30px;
	flex-wrap: wrap;
}

.update-btn {
	background: var(--admin-accent);
	color: white;
	border: none;
	padding: 12px 30px;
	font-size: 14px;
	font-weight: 500;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s;
}

.update-btn:hover {
	background: #0056b3;
	box-shadow: var(--admin-shadow-hover);
}

.delete-btn {
	background: var(--admin-danger);
	color: white;
	border: none;
	padding: 12px 30px;
	font-size: 14px;
	font-weight: 500;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s;
}

.delete-btn:hover {
	background: #c82333;
	box-shadow: var(--admin-shadow-hover);
}

.back-btn {
	background: var(--admin-secondary);
	color: white;
	border: none;
	padding: 12px 30px;
	font-size: 14px;
	font-weight: 500;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s;
}

.back-btn:hover {
	background: var(--admin-primary);
	box-shadow: var(--admin-shadow-hover);
}

@media (max-width: 768px) {
	.update-container {
		margin: 10px;
		padding: 20px;
	}
	
	.form-area {
		grid-template-columns: 1fr;
		gap: 20px;
	}
	
	.button-group {
		flex-direction: column;
	}
	
	.update-btn,
	.delete-btn,
	.back-btn {
		width: 100%;
	}
}
</style>
</head>
<body>

<!-- 管理者用ヘッダー -->
<jsp:include page="common/admin_header.jsp" />

<!-- ページヘッダー -->
<div class="page-header">
	<h2>✏️ 利用者アカウント更新</h2>
	<p class="path">メインメニュー ＞ アカウント管理 ＞ 利用者一覧 ＞ 更新</p>
</div>

<!-- 更新フォーム -->
<div class="update-container">
	<div class="message-area">
		📝 利用者情報を更新します。変更後の内容を入力してください。
	</div>

	<form action="UserUpdateServlet" method="post" id="updateForm">
		<input type="hidden" name="originalStudentNo" value="<%= user.getStudentNo() %>">

		<div class="form-area">
			<!-- 変更前 -->
			<div class="before">
				<h3>📋 変更前</h3>
				<p><strong>クラス</strong><%= user.getClassId() %></p>
				<p><strong>出席番号</strong><%= user.getAttendanceNo() %></p>
				<p><strong>学籍番号</strong><%= user.getStudentNo() %></p>
				<p><strong>名前</strong><%= user.getStudentName() %></p>
			</div>

			<!-- 変更後 -->
			<div class="after">
				<h3>✏️ 変更後</h3>

				<label>クラス</label>
				<select name="classId">
					<option value="2023RS1" <%= "2023RS1".equals(user.getClassId()) ? "selected" : "" %>>2023RS1</option>
					<option value="2023RA1" <%= "2023RA1".equals(user.getClassId()) ? "selected" : "" %>>2023RA1</option>
				</select>

				<label>出席番号</label>
				<input type="text" name="attendanceNo" value="<%= user.getAttendanceNo() %>" required>

				<label>学籍番号</label>
				<input type="text" name="studentNo" value="<%= user.getStudentNo() %>" required>

				<label>名前</label>
				<input type="text" name="studentName" value="<%= user.getStudentName() %>" required>
			</div>
		</div>

		<!-- ボタンエリア -->
		<div class="button-group">
			<button type="submit" class="update-btn">✓ 更新</button>
			<button type="button" class="delete-btn" onclick="confirmDelete()">🗑️ 削除</button>
		</div>
	</form>

	<div class="button-group" style="margin-top: 20px; padding-top: 20px; border-top: 1px solid var(--admin-border);">
		<form action="UserViewServlet" method="post">
			<input type="submit" value="← 戻る" class="back-btn">
		</form>
	</div>
</div>

<script>
function confirmDelete() {
	if (confirm('本当に削除しますか？\nこの操作は取り消せません。')) {
		// 削除処理のサーブレットに送信
		const form = document.createElement('form');
		form.method = 'post';
		form.action = 'UserDeleteServlet';
		
		const input = document.createElement('input');
		input.type = 'hidden';
		input.name = 'studentNo';
		input.value = '<%= user.getStudentNo() %>';
		
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
	}
}
</script>

</body>
</html>
