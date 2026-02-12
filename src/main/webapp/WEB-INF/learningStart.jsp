<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%-- セッション情報から科目リストを取得 --%>
<%@ page import="java.util.List"%>
<%@ page import="model.LearningstarttransitionBeans"%>
<%
List<LearningstarttransitionBeans> subjectList = (List<LearningstarttransitionBeans>) request
		.getAttribute("subjectList");
%>

<html>
<head>
<meta charset="UTF-8">
<title>学習開始画面</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">
<style>
/* ===== 学習開始専用スタイル ===== */
.learning-container {
	max-width: 900px;
	margin: 0 auto;
	padding: 20px;
}

.learning-header {
	text-align: center;
	margin-bottom: 30px;
}

.learning-header h2 {
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

/* 学習開始コンテンツ */
.learning-content {
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	padding: 40px;
	text-align: center;
}

.title-box {
	margin-bottom: 30px;
}

.title-box h1 {
	font-size: 24px;
	color: #333;
	margin: 0;
}

/* 科目選択フォーム */
.content-box {
	margin-bottom: 30px;
}

.content-box label {
	display: block;
	font-size: 16px;
	font-weight: bold;
	color: #333;
	margin-bottom: 15px;
}

.content-box select {
	width: 100%;
	max-width: 400px;
	padding: 12px 15px;
	font-size: 16px;
	border: 2px solid #ddd;
	border-radius: 8px;
	background: #fff;
	transition: border-color 0.3s;
	margin-bottom: 20px;
}

.content-box select:focus {
	outline: none;
	border-color: #667eea;
}

/* 学習開始ボタン */
.start-btn {
	display: inline-block;
	padding: 15px 40px;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: #fff;
	text-decoration: none;
	border-radius: 8px;
	font-weight: bold;
	font-size: 18px;
	border: none;
	cursor: pointer;
	transition: all 0.3s;
	box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
}

.start-btn:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
}

.start-btn:active {
	transform: translateY(0);
}

/* ヒント表示 */
.hint {
	color: #666;
	font-size: 14px;
	margin-top: 20px;
	line-height: 1.6;
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
	.learning-container {
		padding: 10px;
	}
	.learning-header h2 {
		font-size: 22px;
	}
	.learning-content {
		padding: 20px;
	}
	.title-box h1 {
		font-size: 20px;
	}
	.content-box label {
		font-size: 14px;
	}
	.content-box select {
		font-size: 14px;
		padding: 10px;
	}
	.start-btn {
		font-size: 16px;
		padding: 12px 30px;
	}
}
</style>
</head>

<body>
	<jsp:include page="common/header.jsp" />
	<div class="container">

		<div class="learning-container">
			<!-- ===== ヘッダー ===== -->
			<div class="learning-header">
				<h2>学習開始</h2>
				<p class="breadcrumb">メインメニュー　&gt;　学習開始</p>
			</div>

			<!-- ===== 学習開始コンテンツ ===== -->
			<div class="learning-content">
				<div class="title-box">
					<h1>学習を開始しましょう</h1>
				</div>

				<div class="content-box">
					<label for="subject">学習する教科を選択：</label> <select
						id="subject" name="subject">
						<option value="">選択してください</option>
						<%
						if (subjectList != null) {
							for (LearningstarttransitionBeans subject : subjectList) {
						%>
						<option value="<%=subject.getSubjectid()%>">
							<%=subject.getSubjectname()%>
						</option>
						<%
						}
						}
						%>
					</select>

					<form action="CountUpServlet" method="get"
						onsubmit="return checkBeforeStart();">
						<input type="hidden" name="subjectId" id="selectedSubjectId">
						<button type="submit" id="startBtn" class="start-btn">学習開始</button>
					</form>

					<p class="hint">
						クリックすると選択した教科の学習画面に移動します。<br> 学習が完了すると自動で記録されます。
					</p>
				</div>
			</div>
		</div>

	</div>

	<!-- ===== 戻るボタン ===== -->
	<div class="back-btn-container">
    <a href="<%=request.getContextPath()%>/UserMainmenuServlet" class="back-btn">← メインメニューに戻る</a>
</div>

	<script>
		// 選択された教科IDをhiddenにセット
		document.getElementById('subject').addEventListener('change',
				function() {
					document.getElementById('selectedSubjectId').value = this.value;
				});

		function checkBeforeStart() {
			// ▼ 例：subjectId が設定されていない場合のチェック
			const subjectId = document.getElementById("selectedSubjectId").value;

			if (!subjectId) {
				alert("科目が選択されていません。選択してください。");
				return false; // 送信しない
			}

			// ▼ 確認ダイアログを出したい場合（任意）
			return confirm("学習を開始しますか？");

			// true が返る → そのまま送信
			// false → 送信キャンセル
		}
	</script>

</body>
</html>
