<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%-- セッション情報からログイン情報を取得 --%>
<%@ page import="model.AdminLoginBeans"%>
<%
AdminLoginBeans login = (AdminLoginBeans) session.getAttribute("Loginbeans");

int accountId = 0;
if (login != null) {
	accountId = login.getAccountId();
} else {
	response.sendRedirect("adminLogin.jsp");
	return;
}

String nickname = login.getNickName();
boolean hasNickname = (nickname != null && !nickname.trim().isEmpty());
%>

<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>利用者メインメニュー</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<style>
/* ===== メインメニュー専用スタイル ===== */
.mainmenu-container {
	max-width: 1200px;
	margin: 0 auto;
	padding: 20px;
	position: relative;
}

.mainmenu-header {
	text-align: center;
	margin-bottom: 40px;
	position: relative;
}

.mainmenu-header h1 {
	font-size: 32px;
	color: #333;
	margin-bottom: 20px;
	text-align: center;
}

/* ウェルカムメッセージ */
.welcome-message {
	text-align: center;
	margin-top: 15px;
	margin-bottom: 30px;
}

.user-info {
	display: inline-block;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: white;
	padding: 12px 30px;
	border-radius: 25px;
	font-size: 18px;
	font-weight: 600;
	box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.user-info .user-id {
	font-weight: 700;
	margin-right: 10px;
}

.user-info .user-name {
	font-weight: 600;
}

/* パンくずリスト */
.breadcrumb {
	text-align: left;
	color: #666;
	font-size: 14px;
	margin-bottom: 30px;
	padding-left: 0;
}

/* メニューグリッド */
.menu-grid {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 25px;
	margin-bottom: 40px;
	max-width: 900px;
	margin-left: auto;
	margin-right: auto;
}

/* メニューボタン */
.menu-btn {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: #fff;
	text-decoration: none;
	border-radius: 12px;
	font-weight: bold;
	font-size: 18px;
	border: none;
	cursor: pointer;
	transition: all 0.3s;
	box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
	padding: 30px 20px;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	gap: 12px;
	height: 160px;
	width: 100%;
}

.menu-btn:hover {
	transform: translateY(-4px);
	box-shadow: 0 8px 20px rgba(102, 126, 234, 0.5);
}

.menu-btn:active {
	transform: translateY(-2px);
}

.menu-icon {
	font-size: 48px;
	line-height: 1;
}

.menu-text {
	font-size: 18px;
	font-weight: bold;
	line-height: 1.2;
}

/* レスポンシブ対応 */
@media ( max-width : 768px) {
	.mainmenu-container {
		padding: 10px;
	}
	.mainmenu-header h1 {
		font-size: 24px;
	}
	.welcome-message {
		margin-top: 10px;
		margin-bottom: 20px;
	}
	.user-info {
		padding: 10px 20px;
		font-size: 16px;
	}
	.menu-grid {
		grid-template-columns: repeat(2, 1fr);
		gap: 15px;
	}
	.menu-btn {
		padding: 20px 10px;
		height: 140px;
		font-size: 16px;
	}
	.menu-icon {
		font-size: 40px;
	}
	.menu-text {
		font-size: 16px;
	}
}

@media ( max-width : 480px) {
	.menu-grid {
		grid-template-columns: 1fr;
	}
	.menu-btn {
		padding: 25px 15px;
		height: 120px;
	}
	.user-info {
		padding: 8px 16px;
		font-size: 14px;
	}
}
</style>
</head>

<body>
	<jsp:include page="common/header.jsp" />

	<div class="container">

		<div class="mainmenu-container">
			<!-- ===== ヘッダー ===== -->
			<div class="mainmenu-header">
				<img src="${pageContext.request.contextPath}/icon/icon.png" alt="ロゴ" width="50">
				
				<h1>Step Up</h1>
				
				<!-- ID表示 -->
				<div class="welcome-message">
					<% if (hasNickname) { %>
					<div class="user-info">
						<span class="user-id">ようこそ、<%=nickname%>さん</span>
					</div>
					<% } else { %>
					<div class="user-info" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); box-shadow: 0 4px 12px rgba(245, 87, 108, 0.3);">
						<span class="user-id">利用者情報画面から、ニックネームを変更してください</span>
					</div>
					<% } %>
				</div>
			</div>

			<!-- ===== メニューグリッド ===== -->
			<div class="menu-grid">

				<!-- 学習開始 -->
				<form action="Learningstarttransition" method="get" style="margin: 0;">
					<button type="submit" class="menu-btn">
						<span class="menu-icon">✏️</span>
						<span class="menu-text">学習開始</span>
					</button>
				</form>

				<!-- 学習記録 -->
				<form
					action="${pageContext.request.contextPath}/UserViewLearningRecordServlet"
					method="get" style="margin: 0;">
					<button type="submit" class="menu-btn">
						<span class="menu-icon">📝</span>
						<span class="menu-text">学習記録</span>
					</button>
				</form>

				<!-- ミッション -->
				<form action="DisplayMissionsServlet" method="get" style="margin: 0;">
					<button type="submit" class="menu-btn">
						<span class="menu-icon">🎯</span>
						<span class="menu-text">ミッション</span>
					</button>
				</form>

				<!-- ランキング -->
				<form action="DisplayRankingServlet" method="get" style="margin: 0;">
					<button type="submit" class="menu-btn">
						<span class="menu-icon">🏆</span>
						<span class="menu-text">ランキング</span>
					</button>
				</form>

				<!-- 学習時間グラフ -->
				<form action="DisplayStudyTimeGraph" method="get" style="margin: 0;">
					<button type="submit" class="menu-btn">
						<span class="menu-icon">📈</span>
						<span class="menu-text">学習時間グラフ</span>
					</button>
				</form>

				<!-- 利用者情報 -->
				<form action="AccountInfoDisplayServlet" method="get" style="margin: 0;">
					<button type="submit" class="menu-btn">
						<span class="menu-icon">⚙️</span>
						<span class="menu-text">利用者情報</span>
					</button>
				</form>

			</div>

		</div>

	</div>

</body>
</html>