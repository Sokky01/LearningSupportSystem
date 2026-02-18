<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録確認</title>
<link rel="stylesheet" href="css/admin-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<style>
body {
	background: var(--admin-bg);
	display: flex;
	align-items: center;
	justify-content: center;
	min-height: 100vh;
	margin: 0;
}

.confirm-container {
	max-width: 400px;
	width: 90%;
	background: var(--admin-bg-white);
	border: 1px solid var(--admin-border);
	border-radius: 8px;
	padding: 40px;
	text-align: center;
	box-shadow: var(--admin-shadow-hover);
}

.icon {
	font-size: 48px;
	margin-bottom: 20px;
}

h3 {
	font-size: 22px;
	color: var(--admin-text);
	margin: 0 0 15px 0;
}

p {
	color: var(--admin-text-muted);
	font-size: 14px;
	margin-bottom: 30px;
}

.button-group {
	display: flex;
	gap: 15px;
	justify-content: center;
}

button {
	padding: 10px 30px;
	font-size: 14px;
	font-weight: 500;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s;
}

.confirm-btn {
	background: var(--admin-success);
	color: white;
}

.confirm-btn:hover {
	background: #218838;
	box-shadow: var(--admin-shadow-hover);
}

.cancel-btn {
	background: var(--admin-secondary);
	color: white;
}

.cancel-btn:hover {
	background: var(--admin-primary);
	box-shadow: var(--admin-shadow-hover);
}
</style>
</head>
<body>

<div class="confirm-container">
	<div class="icon">❓</div>
	<h3>登録確認</h3>
	<p>この情報を登録しますか？</p>

	<div class="button-group">
		<button class="confirm-btn" onclick="submitParent()">✓ 確定</button>
		<button class="cancel-btn" onclick="window.close()">← 戻る</button>
	</div>
</div>

<script>
function submitParent() {
    window.opener.document.getElementById("csvForm").submit();
    window.close();
}
</script>

</body>
</html>
