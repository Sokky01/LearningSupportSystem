<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>アカウント情報</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">
<style>
/* ===== アカウント情報専用スタイル ===== */
.account-container {
	max-width: 900px;
	margin: 0 auto;
	padding: 20px;
}

.account-header {
	text-align: center;
	margin-bottom: 30px;
}

.account-header h2 {
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

/* プロフィールボックス */
.profile-box {
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	padding: 40px;
}

/* 定義リストスタイル */
.profile-box dl {
	display: grid;
	grid-template-columns: 180px 1fr;
	row-gap: 20px;
	column-gap: 30px;
	margin: 0;
}

.profile-box dt {
	font-weight: 600;
	color: #667eea;
	padding: 10px 0;
	font-size: 16px;
}

.profile-box dd {
	margin: 0;
	color: #333;
	padding: 10px 0;
	border-bottom: 1px solid #eee;
	font-size: 16px;
}

.profile-box dd:last-of-type {
	border-bottom: none;
}

/* ボタンエリア */
.button-area {
	display: flex;
	justify-content: center;
	gap: 20px;
	margin-top: 40px;
	flex-wrap: wrap;
}

.edit-btn {
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

.edit-btn:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 20px rgba(102, 126, 234, 0.5);
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
	.account-container {
		padding: 10px;
	}
	.account-header h2 {
		font-size: 22px;
	}
	.profile-box {
		padding: 20px;
	}
	.profile-box dl {
		grid-template-columns: 1fr;
		row-gap: 10px;
	}
	.profile-box dt {
		border-bottom: none;
	}
	.button-area {
		flex-direction: column;
	}
	.edit-btn {
		width: 100%;
	}
}
</style>
</head>

<body>
	<jsp:include page="common/header.jsp" />
	<div class="container">

		<div class="account-container">
			<!-- ===== ヘッダー ===== -->
			<div class="account-header">
				<h2>プロフィール</h2>
				<p class="breadcrumb">メインメニュー　&gt;　利用者情報　&gt;　プロフィール</p>
			</div>

			<!-- ===== プロフィールボックス ===== -->
			<div class="profile-box">
				<dl>
					<dt>ID</dt>
					<dd>${profile.studentId}</dd>

					<dt>名前</dt>
					<dd>${profile.studentName}</dd>

					<dt>ニックネーム</dt>
					<dd>${profile.nickName}</dd>

					<dt>公開設定</dt>
					<dd>${profile.publicPreference == '1' ? '公開' : '非公開'}</dd>

					<dt>パスワード</dt>
					<dd>************</dd>

					<!-- 【修正】gradeName ではなく settingGradeId から グレード名を計算 -->
					<dt>グレード</dt>
					<dd>
						<%
							// profile から SettingGradeId を取得
							model.UserProfile profile = (model.UserProfile) request.getAttribute("profile");
							String gradeName = "不明";
							
							if (profile != null && profile.getSettingGradeId() != null) {
								try {
									int gradeId = Integer.parseInt(profile.getSettingGradeId());
									String[] names = { "", "ブロンズ", "シルバー", "ゴールド", "プラチナ", "ダイヤモンド" };
									
									if (gradeId > 0 && gradeId < names.length) {
										gradeName = names[gradeId];
									} else {
										gradeName = "グレード" + gradeId;
									}
								} catch (NumberFormatException e) {
									gradeName = "不明";
								}
							}
						%>
						<%= gradeName %>
					</dd>
				</dl>

				<div class="button-area">
					<form action="AccountEditServlet" method="get" style="margin: 0;">
						<button type="submit" class="edit-btn">設定変更</button>
					</form>
				</div>
			</div>
		</div>

	</div>

	<!-- ===== 戻るボタン ===== -->
	<div class="back-btn-container">
    <a href="<%=request.getContextPath()%>/UserMainmenuServlet" class="back-btn">← メインメニューに戻る</a>
</div>

</body>
</html>
