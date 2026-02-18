<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%-- セッション情報からプロフィール情報を取得 --%>
<%@ page import="model.RankingBeans"%>
<%
RankingBeans profile = (RankingBeans) request.getAttribute("profile");
%>

<html lang="ja">
<head>
<meta charset="UTF-8">
<title><%=profile.getNickName()%> さんのプロフィール</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<style>
/* ===== プロフィール専用スタイル ===== */
.profile-container {
	max-width: 900px;
	margin: 0 auto;
	padding: 20px;
}

.profile-header {
	text-align: center;
	margin-bottom: 30px;
}

.profile-header h2 {
	font-size: 28px;
	color: #333;
	margin-bottom: 10px;
}

.breadcrumb {
	color: #666;
	font-size: 14px;
	margin-bottom: 20px;
	text-align: left;
}

/* プロフィールコンテンツ */
.profile-content {
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	padding: 40px;
}

.profile-content p {
	font-size: 18px;
	margin-bottom: 20px;
	color: #333;
	line-height: 1.8;
}

.profile-content strong {
	color: #667eea;
	font-weight: bold;
}

.highlight-point {
	font-size: 2em;
	color: #ff9800;
	font-weight: bold;
}

/* 戻るボタン */
.back-btn-container {
	text-align: center;
	margin: 30px 0;
	padding: 20px 0;
}

.back-btn {
	display: inline-block;
	padding: 12px 30px;
	background: #6c757d;
	color: #fff;
	text-decoration: none;
	border-radius: 5px;
	font-weight: bold;
	transition: background 0.3s;
	border: none;
	cursor: pointer;
	font-size: 16px;
}

.back-btn:hover {
	background: #5a6268;
}

/* レスポンシブ対応 */
@media ( max-width : 768px) {
	.profile-container {
		padding: 10px;
	}
	.profile-header h2 {
		font-size: 22px;
	}
	.profile-content {
		padding: 20px;
	}
	.profile-content p {
		font-size: 16px;
	}
}
</style>
</head>

<body>
	<jsp:include page="common/header.jsp" />
	<div class="container">

		<div class="profile-container">
			<!-- ===== ヘッダー ===== -->
			<div class="profile-header">
				<h2><%=profile.getNickName()%> さんの学習状況
				</h2>
				<p class="breadcrumb">メインメニュー　&gt;　ランキング　&gt;　プロフィール</p>
			</div>

			<!-- ===== プロフィールコンテンツ ===== -->
			<div class="profile-content">
				<p>
					<strong>ニックネーム:</strong>
					<%=profile.getNickName()%>
				</p>
				<p>
					<strong>累計獲得ポイント:</strong> <span class="highlight-point"><%=profile.getPointTotal()%>
						pt</span>
				</p>
			</div>
		</div>

	</div>

	<!-- ===== 戻るボタン ===== -->
	<div class="back-btn-container">
		<button type="button" onclick="history.back()" class="back-btn">←
			ランキングに戻る</button>
	</div>

</body>
</html>
