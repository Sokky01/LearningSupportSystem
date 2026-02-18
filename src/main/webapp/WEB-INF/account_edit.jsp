<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>プロフィール編集</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<style>
/* ===== プロフィール編集専用スタイル ===== */
.edit-container {
	max-width: 900px;
	margin: 0 auto;
	padding: 20px;
}

.edit-header {
	text-align: center;
	margin-bottom: 30px;
}

.edit-header h2 {
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

/* 編集コンテンツ */
.edit-content {
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	padding: 40px;
}

/* エラー表示 */
.error-box {
	background: #fee;
	border: 1px solid #e74c3c;
	color: #e74c3c;
	padding: 15px;
	border-radius: 8px;
	margin-bottom: 20px;
	font-weight: bold;
}

/* フォーム行 */
.form-row {
	display: flex;
	align-items: center;
	margin-bottom: 25px;
	gap: 20px;
}

.form-row label {
	min-width: 180px;
	font-weight: 600;
	color: #333;
	font-size: 16px;
}

.form-row input[type="text"], .form-row input[type="password"],
	.form-row select {
	flex: 1;
	padding: 10px 15px;
	border: 2px solid #ddd;
	border-radius: 8px;
	font-size: 16px;
	transition: border-color 0.3s;
}

.form-row input:focus, .form-row select:focus {
	outline: none;
	border-color: #667eea;
}

/* ラジオボタングループ */
.radio-group {
	display: flex;
	gap: 30px;
}

.radio-group label {
	min-width: auto;
	display: flex;
	align-items: center;
	gap: 8px;
	font-weight: normal;
	cursor: pointer;
}

.radio-group input[type="radio"] {
	width: 18px;
	height: 18px;
	cursor: pointer;
}

/* セクション区切り */
.section-divider {
	height: 2px;
	background: linear-gradient(90deg, transparent, #ddd, transparent);
	margin: 40px 0;
}

/* ボタンエリア */
.button-area {
	display: flex;
	justify-content: center;
	gap: 20px;
	margin-top: 40px;
}

.save-btn {
	padding: 12px 40px;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: #fff;
	border: none;
	border-radius: 8px;
	font-weight: bold;
	font-size: 16px;
	cursor: pointer;
	transition: all 0.3s;
	box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.save-btn:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 20px rgba(102, 126, 234, 0.5);
}

.cancel-btn {
	padding: 12px 40px;
	background: #6c757d;
	color: #fff;
	border: none;
	border-radius: 8px;
	font-weight: bold;
	font-size: 16px;
	cursor: pointer;
	transition: all 0.3s;
}

.cancel-btn:hover {
	background: #5a6268;
	transform: translateY(-2px);
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
	.edit-container {
		padding: 10px;
	}
	.edit-header h2 {
		font-size: 22px;
	}
	.edit-content {
		padding: 20px;
	}
	.form-row {
		flex-direction: column;
		align-items: flex-start;
		gap: 10px;
	}
	.form-row label {
		min-width: auto;
	}
	.form-row input, .form-row select {
		width: 100%;
	}
	.button-area {
		flex-direction: column;
	}
	.save-btn, .cancel-btn {
		width: 100%;
	}
}
</style>
</head>

<body>
	<jsp:include page="common/header.jsp" />
	<div class="container">

		<div class="edit-container">
			<!-- ===== ヘッダー ===== -->
			<div class="edit-header">
				<h2>プロフィール編集</h2>
				<p class="breadcrumb">メインメニュー　&gt;　利用者情報　&gt;　プロフィール編集</p>
			</div>

			<!-- ===== 編集コンテンツ ===== -->
			<div class="edit-content">

				<!-- エラーメッセージ -->
				<%
				String errorMsg = (String) request.getAttribute("errorMsg");
				if (errorMsg != null) {
				%>
				<div class="error-box"><%=errorMsg%></div>
				<%
				}
				%>

				<form action="AccountEditServlet" method="post">

					<div class="form-row">
						<label>学籍番号</label>
						<div>
							${profile.studentId} <input type="hidden" name="studentId"
								value="${profile.studentId}"> <input type="hidden"
								name="maxGrade" value="${profile.maxGrade}">
						</div>
					</div>

					<div class="form-row">
					<label>名前</label>
					<div>
						${profile.studentName} <input type="hidden" name="studentName"
							value="${profile.studentName}">
					</div>
				</div>

					<div class="form-row">
						<label>ニックネーム</label> <input type="text" name="nickName"
							value="${profile.nickName}" required>
					</div>

					<div class="form-row">
						<label>公開設定</label>
						<div class="radio-group">
							<label> <input type="radio" name="publicPreference"
								value="1" ${profile.publicPreference == '1' ? 'checked' : ''}>
								公開
							</label> <label> <input type="radio" name="publicPreference"
								value="0" ${profile.publicPreference == '0' ? 'checked' : ''}>
								非公開
							</label>
						</div>
					</div>

					<div class="form-row">
						<label>グレード</label> <select name="settingGradeId">
							<%
							model.UserProfile p = (model.UserProfile) request.getAttribute("profile");
							if (p != null && p.getMaxGrade() != null && p.getSettingGradeId() != null) {
								int max = Integer.parseInt(p.getMaxGrade());
								int current = Integer.parseInt(p.getSettingGradeId());
								String[] names = { "", "ブロンズ", "シルバー", "ゴールド", "プラチナ", "ダイヤモンド" };
								for (int i = 1; i <= max; i++) {
							%>
							<option value="<%=i%>" <%=(i == current ? "selected" : "")%>>
								<%=(i < names.length ? names[i] : "グレード" + i)%>
							</option>
							<%
							}
							} else {
							%>
							<option value="">グレード情報なし</option>
							<%
							}
							%>
						</select>
					</div>

					<div class="section-divider"></div>

					<div class="form-row">
						<label>現在のパスワード</label> <input type="password"
							name="currentPassword">
					</div>

					<div class="form-row">
						<label>新しいパスワード</label> <input type="password"
							name="newPassword">
					</div>

					<div class="form-row">
						<label>確認用パスワード</label> <input type="password"
							name="confirmNewPassword">
					</div>

					<div class="button-area">
						<button type="submit" class="save-btn">保存する</button>
						<button type="button" class="cancel-btn" onclick="history.back()">キャンセル</button>
					</div>

				</form>
			</div>
		</div>

	</div>

	<!-- ===== 戻るボタン ===== -->
	<div class="back-btn-container">
    <a href="<%=request.getContextPath()%>/UserMainmenuServlet" class="back-btn">← メインメニューに戻る</a>
</div>

</body>
</html>
