<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ page import="model.AdminLoginBeans" %>
<% AdminLoginBeans Login = (AdminLoginBeans) session.getAttribute("Loginbeans"); %>
<title>管理者用メインメニュー</title>
<link rel="stylesheet" href="css/admin-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">
<style>
/* メインメニュー専用スタイル */
.admin-header {
	background: var(--admin-primary);
	color: white;
	padding: 15px 20px;
	margin: 0;
	display: flex;
	justify-content: space-between;
	align-items: center;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.admin-header h1 {
	margin: 0;
	padding: 0;
	border: none;
	font-size: 20px;
	color: white;
	text-align: left;
}

.admin-id-display {
	background: rgba(255, 255, 255, 0.2);
	padding: 6px 14px;
	border-radius: 4px;
	font-size: 13px;
	font-weight: 500;
}

.menu-container {
	max-width: 800px;
	margin: 40px auto;
	padding: 30px;
}

.menu-title {
	text-align: center;
	font-size: 22px;
	font-weight: 600;
	color: var(--admin-text);
	margin-bottom: 30px;
	padding-bottom: 15px;
	border-bottom: 2px solid var(--admin-border);
}

.menu-grid {
	display: grid;
	grid-template-columns: repeat(2, 1fr);
	gap: 20px;
	margin-bottom: 30px;
}

.menu-grid form {
	margin: 0;
}

.menu-button {
	width: 100%;
	background: var(--admin-bg-white);
	border: 2px solid var(--admin-border);
	color: var(--admin-text);
	padding: 50px 20px;
	font-size: 18px;
	font-weight: 600;
	text-align: center;
	border-radius: 6px;
	cursor: pointer;
	transition: all 0.2s;
}

.menu-button:hover {
	background: var(--admin-bg-light);
	border-color: var(--admin-accent);
	color: var(--admin-accent);
	box-shadow: var(--admin-shadow-hover);
	transform: translateY(-2px);
}

/* 無効化されたボタン */
.menu-button:disabled {
	background: var(--admin-bg-light);
	border-color: var(--admin-border);
	color: var(--admin-text-muted);
	cursor: not-allowed;
	opacity: 0.6;
}

.menu-button:disabled:hover {
	transform: none;
	background: var(--admin-bg-light);
	border-color: var(--admin-border);
	color: var(--admin-text-muted);
	box-shadow: none;
}

.logout-area {
	text-align: center;
	margin-top: 40px;
	padding-top: 20px;
	border-top: 1px solid var(--admin-border);
}

.logout-button {
	background: var(--admin-danger);
	color: white;
	border: none;
	padding: 12px 30px;
	font-size: 15px;
	font-weight: 500;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s;
}

.logout-button:hover {
	background: #c82333;
	box-shadow: var(--admin-shadow-hover);
}

@media (max-width: 768px) {
	.admin-header {
		flex-direction: column;
		gap: 10px;
		padding: 12px 15px;
	}
	
	.admin-header > div[style*="width: 120px"] {
		display: none;
	}
	
	.menu-container {
		margin: 20px 10px;
		padding: 20px;
	}
	
	.menu-grid {
		grid-template-columns: 1fr;
		gap: 15px;
	}
	
	.menu-button {
		padding: 40px 20px;
		font-size: 16px;
	}
}
</style>
</head>
<body>
	

	<!-- メインコンテンツ -->
	<jsp:include page="common/admin_header.jsp" />
	<div class="menu-container">
		<div class="menu-title">学習支援サービス 管理メニュー</div>

		<div class="menu-grid">
			<!-- 学習記録情報 -->
			<input type="button" class="menu-button" value="📈 学習記録情報" disabled>

			<!-- アカウント管理 -->
			<form action="AdminViewServlet" method="post">
				<input type="submit" class="menu-button" value="👥 アカウント管理">
			</form>

			<!-- クラス情報管理 -->
			<input type="button" class="menu-button" value="🏫 クラス情報管理" disabled>

			<!-- 科目情報管理 -->
			<input type="button" class="menu-button" value="📚 科目情報管理" disabled>
		</div>

	</div>

</body>
</html>
