<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">

<%@ page import="java.util.List"%>
<%@ page import="model.userAccountListBeans" %>

<% 
// セッションスコープに保存されたuserAccountListBeansの取得
List<userAccountListBeans> userLogin = (List<userAccountListBeans>) session.getAttribute("userBeansList");
%>

<title>利用者アカウント一覧</title>
<link rel="stylesheet" href="css/admin-theme.css">
<style>
/* 利用者一覧専用スタイル */
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

.table-container {
	background: var(--admin-bg-white);
	border: 1px solid var(--admin-border);
	border-radius: 4px;
	padding: 20px;
	margin-bottom: 20px;
}

.table-wrapper {
	overflow-x: auto;
	max-height: 600px;
	overflow-y: auto;
	border: 1px solid var(--admin-border);
	border-radius: 4px;
}

/* カスタムスクロールバー */
.table-wrapper::-webkit-scrollbar {
	width: 10px;
	height: 10px;
}

.table-wrapper::-webkit-scrollbar-track {
	background: var(--admin-bg-light);
	border-radius: 4px;
}

.table-wrapper::-webkit-scrollbar-thumb {
	background: var(--admin-secondary);
	border-radius: 4px;
}

.table-wrapper::-webkit-scrollbar-thumb:hover {
	background: var(--admin-primary);
}

table {
	width: 100%;
	border-collapse: collapse;
	background: var(--admin-bg-white);
}

thead {
	position: sticky;
	top: 0;
	z-index: 10;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

th {
	background: var(--admin-secondary);
	color: white;
	padding: 14px 12px;
	text-align: left;
	font-weight: 600;
	font-size: 14px;
	border-bottom: 2px solid var(--admin-border-dark);
	white-space: nowrap;
}

td {
	padding: 12px;
	border-bottom: 1px solid var(--admin-bg-dark);
	font-size: 14px;
}

tbody tr:hover {
	background: var(--admin-bg-light);
}

tbody tr:last-child td {
	border-bottom: none;
}

.update-btn {
	background: var(--admin-accent);
	color: white;
	border: none;
	padding: 6px 16px;
	font-size: 13px;
	font-weight: 500;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s;
}

.update-btn:hover {
	background: #0056b3;
	box-shadow: var(--admin-shadow-hover);
}

.button-area {
	display: flex;
	justify-content: center;
	gap: 15px;
	margin-top: 20px;
}

.back-btn {
	background: var(--admin-secondary);
	color: white;
	border: none;
	padding: 10px 30px;
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

/* データ件数表示 */
.data-count {
	text-align: right;
	color: var(--admin-text-muted);
	font-size: 13px;
	margin-bottom: 10px;
}

@media (max-width: 768px) {
	.table-wrapper {
		max-height: 400px;
	}
	
	th, td {
		font-size: 12px;
		padding: 8px 6px;
	}
}
</style>
</head>
<body>

<!-- 管理者用ヘッダー -->
<jsp:include page="common/admin_header.jsp" />

<!-- ページヘッダー -->
<div class="page-header">
	<h2>👥 利用者アカウント一覧</h2>
	<p class="path">メインメニュー ＞ アカウント管理 ＞ 利用者アカウント一覧</p>
</div>

<!-- テーブルコンテナ -->
<div class="table-container">
	<div class="data-count">
		<% if (userLogin != null) { %>
			全 <%= userLogin.size() %> 件
		<% } else { %>
			データなし
		<% } %>
	</div>
	
	<div class="table-wrapper">
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>出席番号</th>
					<th>クラス</th>
					<th>名前</th>
					<th style="text-align: center; width: 100px;">操作</th>
				</tr>
			</thead>
			<tbody>
				<% if (userLogin != null && !userLogin.isEmpty()) { %>
					<% for (userAccountListBeans beans : userLogin) { %>
					<tr>
						<td><%= beans.getStudentNo() %></td>
						<td><%= beans.getAttendanceNo() %></td>
						<td><%= beans.getClassId() %></td>
						<td><%= beans.getStudentName() %></td>
						<td style="text-align: center;">
							<form action="UserUpdateServlet" method="get" style="margin: 0;">
								<input type="hidden" name="studentNo" value="<%= beans.getStudentNo() %>">
								<input type="submit" value="更新" class="update-btn">
							</form>
						</td>
					</tr>
					<% } %>
				<% } else { %>
					<tr>
						<td colspan="5" style="text-align: center; padding: 40px; color: var(--admin-text-muted);">
							データがありません
						</td>
					</tr>
				<% } %>
			</tbody>
		</table>
	</div>
</div>

<!-- ボタンエリア -->
<div class="button-area">
	<form action="AdminViewServlet" method="post">
		<input type="submit" value="← 戻る" class="back-btn">
	</form>
</div>

</body>
</html>
