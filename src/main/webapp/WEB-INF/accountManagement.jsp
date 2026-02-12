<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<%@ page import="model.AdminLoginBeans" %>
<% AdminLoginBeans Login = (AdminLoginBeans) session.getAttribute("Loginbeans"); %>

<title>アカウント管理</title>
<link rel="stylesheet" href="css/admin-theme.css">
<style>
/* アカウント管理専用スタイル */
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

.management-container {
	max-width: 800px;
	margin: 20px auto;
	background: var(--admin-bg-white);
	border: 1px solid var(--admin-border);
	border-radius: 4px;
	padding: 30px;
	box-shadow: var(--admin-shadow);
}

.section {
	margin-bottom: 30px;
	padding: 20px;
	background: var(--admin-bg-light);
	border-radius: 4px;
}

.section:last-child {
	margin-bottom: 0;
}

.section-title {
	font-size: 18px;
	font-weight: 600;
	color: var(--admin-text);
	margin: 0 0 20px 0;
	padding-bottom: 10px;
	border-bottom: 2px solid var(--admin-border);
}

.btn-row {
	display: flex;
	gap: 15px;
	justify-content: center;
}

/* form要素のスタイル統一 */
.btn-row form,
.btn-row .btn-wrapper {
	flex: 1;
	max-width: 200px;
}

.btn {
	width: 100%;
	background: var(--admin-accent);
	color: white;
	border: none;
	padding: 15px 20px;
	font-size: 16px;
	font-weight: 500;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s;
}

.btn:hover {
	background: #0056b3;
	box-shadow: var(--admin-shadow-hover);
	transform: translateY(-2px);
}

/* 無効化されたボタン */
.btn:disabled {
	background: var(--admin-text-muted);
	cursor: not-allowed;
	opacity: 0.6;
}

.btn:disabled:hover {
	transform: none;
	box-shadow: none;
}

.back-area {
	text-align: center;
	margin-top: 30px;
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

@media (max-width: 768px) {
	.management-container {
		margin: 10px;
		padding: 20px;
	}
	
	.btn-row {
		flex-direction: column;
	}
	
	.btn-row form,
	.btn-row .btn-wrapper {
		max-width: 100%;
	}
}
</style>
</head>
<body>

<!-- 管理者用ヘッダー -->
<jsp:include page="common/admin_header.jsp" />

<!-- ページヘッダー -->
<div class="page-header">
	<h2>👥 アカウント管理</h2>
	<p class="path">メインメニュー ＞ アカウント管理</p>
</div>

<!-- 管理コンテナ -->
<div class="management-container">
	
	<!-- 管理者用セクション -->
	<div class="section">
		<h3 class="section-title">🔑 管理者アカウント</h3>
		<div class="btn-row">
			<!-- formなし、divでラップしてサイズ統一 -->
			<div class="btn-wrapper">
				<input type="button" value="登録" class="btn" disabled>
			</div>
			
			<div class="btn-wrapper">
				<input type="button" value="一覧" class="btn" disabled>
			</div>
		</div>
	</div>

	<!-- 利用者用セクション -->
	<div class="section">
		<h3 class="section-title">👤 利用者アカウント</h3>
		<div class="btn-row">
			<form action="<%=request.getContextPath()%>/UserRegisterServlet" method="get">
				<input type="submit" value="登録" class="btn">
			</form>

			<form action="UserViewServlet" method="get">
				<input type="submit" value="一覧" class="btn">
			</form>
		</div>
	</div>

</div>

<!-- 戻るボタン -->
<div class="back-area">
	<form action="AdminMainmenuServlet" method="post">
		<input type="submit" value="← メインメニューに戻る" class="back-btn">
	</form>
</div>

</body>
</html>
