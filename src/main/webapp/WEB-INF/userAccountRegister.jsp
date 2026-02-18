<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>利用者アカウント登録</title>
<link rel="stylesheet" href="css/admin-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<style>
/* 登録画面専用スタイル */
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

.register-container {
	max-width: 1000px;
	margin: 20px auto;
	background: var(--admin-bg-white);
	border: 1px solid var(--admin-border);
	border-radius: 4px;
	padding: 30px;
	box-shadow: var(--admin-shadow);
}

.csv-area {
	background: var(--admin-bg-light);
	border: 2px dashed var(--admin-border);
	padding: 40px;
	text-align: center;
	border-radius: 4px;
	margin-bottom: 30px;
	transition: all 0.2s;
}

.csv-area:hover {
	border-color: var(--admin-accent);
	background: var(--admin-bg-white);
}

.csv-area input[type="file"] {
	margin: 20px 0;
}

.csv-preview-area {
	margin-top: 30px;
	max-height: 400px;
	overflow: auto;
	border: 1px solid var(--admin-border);
	border-radius: 4px;
}

.csv-preview-table {
	width: 100%;
	border-collapse: collapse;
	background: var(--admin-bg-white);
}

.csv-preview-table thead {
	position: sticky;
	top: 0;
	background: var(--admin-secondary);
	z-index: 10;
}

.csv-preview-table th {
	background: var(--admin-secondary);
	color: white;
	padding: 12px;
	text-align: left;
	font-size: 14px;
}

.csv-preview-table td {
	padding: 10px;
	border-bottom: 1px solid var(--admin-bg-dark);
	font-size: 14px;
}

.csv-preview-table tr:hover {
	background: var(--admin-bg-light);
}

.button-area {
	display: flex;
	justify-content: center;
	gap: 15px;
	margin-top: 30px;
}

.register-btn {
	background: var(--admin-success);
	color: white;
	border: none;
	padding: 12px 40px;
	font-size: 15px;
	font-weight: 500;
	border-radius: 4px;
	cursor: pointer;
	transition: all 0.2s;
}

.register-btn:hover {
	background: #218838;
	box-shadow: var(--admin-shadow-hover);
}

.register-btn:disabled {
	background: var(--admin-text-muted);
	cursor: not-allowed;
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

.info-message {
	background: var(--admin-bg-light);
	border-left: 4px solid var(--admin-info);
	padding: 15px;
	margin-bottom: 20px;
	font-size: 14px;
	color: var(--admin-text);
}

@media (max-width: 768px) {
	.register-container {
		margin: 10px;
		padding: 20px;
	}
	
	.csv-area {
		padding: 20px;
	}
	
	.button-area {
		flex-direction: column;
	}
	
	.register-btn,
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
	<h2>➕ 利用者アカウント登録</h2>
	<p class="path">メインメニュー ＞ アカウント管理 ＞ 利用者登録</p>
</div>

<!-- 登録フォーム -->
<div class="register-container">
	<div class="info-message">
		📋 CSVファイルをアップロードして、複数の利用者を一括登録できます。<br>
		<strong>形式:</strong> 学籍番号,出席番号,クラスID,名前
	</div>

	<form action="<%=request.getContextPath()%>/UserRegisterServlet" method="post" enctype="multipart/form-data" id="csvForm">
		
		<!-- CSV読み込みエリア -->
		<div class="csv-area">
			<h3>📁 CSVファイルを選択</h3>
			<input type="file" name="csvFile" accept=".csv" required id="csvInput">
			<p style="color: var(--admin-text-muted); font-size: 13px; margin-top: 10px;">
				※ CSVファイルのみ対応しています
			</p>
		</div>

		<!-- プレビューエリア -->
		<div id="previewArea" style="display: none;">
			<h3 style="margin-bottom: 15px;">👁️ プレビュー</h3>
			<div class="csv-preview-area">
				<table class="csv-preview-table" id="previewTable">
					<thead>
						<tr>
							<th>学籍番号</th>
							<th>出席番号</th>
							<th>クラスID</th>
							<th>名前</th>
						</tr>
					</thead>
					<tbody id="previewBody">
					</tbody>
				</table>
			</div>
		</div>

		<!-- ボタンエリア -->
		<div class="button-area">
			<button type="submit" class="register-btn" id="registerBtn" disabled>✓ 登録</button>
		</div>
	</form>

	<div class="button-area" style="margin-top: 20px; padding-top: 20px; border-top: 1px solid var(--admin-border);">
		<form action="<%=request.getContextPath()%>/AdminViewServlet" method="post">
			<input type="submit" value="← 戻る" class="back-btn">
		</form>
	</div>
</div>

<script>
// CSVファイル選択時のプレビュー機能
document.getElementById('csvInput').addEventListener('change', function(e) {
	const file = e.target.files[0];
	if (file) {
		const reader = new FileReader();
		reader.onload = function(event) {
			const text = event.target.result;
			const lines = text.split('\n').filter(line => line.trim() !== '');
			
			const tbody = document.getElementById('previewBody');
			tbody.innerHTML = '';
			
			// 最初の行をスキップ（ヘッダー行の場合）
			const startIndex = lines[0].includes('学籍番号') ? 1 : 0;
			
			for (let i = startIndex; i < lines.length; i++) {
				const columns = lines[i].split(',');
				if (columns.length >= 4) {
					const row = tbody.insertRow();
					columns.slice(0, 4).forEach(col => {
						const cell = row.insertCell();
						cell.textContent = col.trim();
					});
				}
			}
			
			// プレビュー表示と登録ボタン有効化
			document.getElementById('previewArea').style.display = 'block';
			document.getElementById('registerBtn').disabled = false;
		};
		reader.readAsText(file, 'UTF-8');
	}
});
</script>

</body>
</html>
