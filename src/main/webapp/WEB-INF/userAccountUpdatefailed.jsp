<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="ja">
<head>
<meta charset="UTF-8">
<title>更新失敗</title>
<link rel="stylesheet" href="css/admin-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<style>
/* 失敗画面専用スタイル */
body {
	background: var(--admin-bg);
	display: flex;
	align-items: center;
	justify-content: center;
	min-height: 100vh;
	margin: 0;
}

.result-container {
	max-width: 500px;
	width: 90%;
	background: var(--admin-bg-white);
	border: 1px solid var(--admin-border);
	border-radius: 8px;
	padding: 60px 40px;
	text-align: center;
	box-shadow: var(--admin-shadow-hover);
}

.icon {
	font-size: 64px;
	margin-bottom: 20px;
}

.message {
	font-size: 24px;
	font-weight: 600;
	color: var(--admin-danger);
	margin-bottom: 30px;
}

.description {
	color: var(--admin-text-muted);
	font-size: 14px;
	margin-bottom: 40px;
}

.back-btn {
	background: var(--admin-secondary);
	color: white;
	border: none;
	padding: 12px 40px;
	font-size: 15px;
	font-weight: 500;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s;
}

.back-btn:hover {
	background: var(--admin-primary);
	box-shadow: var(--admin-shadow-hover);
	transform: translateY(-2px);
}

@media (max-width: 768px) {
	.result-container {
		padding: 40px 20px;
	}
	
	.icon {
		font-size: 48px;
	}
	
	.message {
		font-size: 20px;
	}
}
</style>
</head>

<body>

<div class="result-container">
	<div class="icon">❌</div>
	<p class="message">更新が失敗しました</p>
	<p class="description">
		データの更新中にエラーが発生しました。<br>
		入力内容を確認して、もう一度お試しください。
	</p>

	<form action="UserViewServlet" method="post" style="margin: 0;">
		<button type="submit" class="back-btn">← 戻る</button>
	</form>
</div>

</body>
</html>
