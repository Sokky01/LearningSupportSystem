<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%-- セッション情報から学生番号と科目リストを取得 --%>
<%@ page import="java.util.List"%>
<%@ page import="model.targetSettingBeans"%>
<%
int studentNo = (int) request.getAttribute("studentNo");
List<targetSettingBeans> subjectList = (List<targetSettingBeans>) request.getAttribute("subjectList");
%>

<html>
<head>
<meta charset="UTF-8">
<title>目標時間設定画面</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<style>
/* ===== 目標設定専用スタイル ===== */
.target-container {
	max-width: 900px;
	margin: 0 auto;
	padding: 20px;
}

.target-header {
	text-align: center;
	margin-bottom: 30px;
}

.target-header h2 {
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

/* 目標設定コンテンツ */
.target-content {
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	padding: 40px;
}

.form-section {
	margin-bottom: 30px;
	text-align: center;
}

.form-section label {
	display: block;
	font-size: 16px;
	font-weight: bold;
	color: #333;
	margin-bottom: 10px;
	text-align: center;
}

.form-section select {
	width: 100%;
	max-width: 400px;
	padding: 12px 15px;
	font-size: 16px;
	border: 2px solid #ddd;
	border-radius: 8px;
	background: #fff;
	transition: border-color 0.3s;
	margin: 0 auto;
	display: block;
}

.form-section select:focus {
	outline: none;
	border-color: #667eea;
}

/* 時間選択エリア */
.time-select-area {
	display: inline-flex;
	align-items: center;
	justify-content: center;
	gap: 8px;
	flex-wrap: nowrap;
}

.time-select-area select {
	width: 80px;
	padding: 10px 8px;
	font-size: 16px;
}

.time-select-area span {
	font-size: 16px;
	font-weight: bold;
	color: #333;
	margin: 0;
	padding: 0 8px 0 2px;
}

/* ボタンエリア */
.button-area {
	display: flex;
	justify-content: center;
	gap: 20px;
	margin-top: 40px;
}

.submit-btn {
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

.submit-btn:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
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
	.target-container {
		padding: 10px;
	}
	.target-header h2 {
		font-size: 22px;
	}
	.target-content {
		padding: 20px;
	}
	.time-select-area {
		flex-direction: column;
		align-items: flex-start;
	}
	.button-area {
		flex-direction: column;
	}
	.submit-btn {
		width: 100%;
	}
}
</style>
</head>

<body>
	<jsp:include page="common/header.jsp" />
	<div class="container">

		<div class="target-container">
			<!-- ===== ヘッダー ===== -->
			<div class="target-header">
				<h2>目標時間設定</h2>
				<p class="breadcrumb">メインメニュー　&gt;　目標時間設定</p>
			</div>

			<!-- ===== 目標設定コンテンツ ===== -->
			<div class="target-content">
				<form action="SetGoalServlet" method="post">
					<input type="hidden" name="studentNo" value="<%=studentNo%>">

					<div class="form-section">
						<label for="subjectId">科目：</label> <select name="subjectId"
							id="subjectId">
							<%
							for (targetSettingBeans s : subjectList) {
							%>
							<option value="<%=s.getSubjectId()%>">
								<%=s.getSubjectName()%>
							</option>
							<%
							}
							%>
						</select>
					</div>

					<div class="form-section">
						<label>目標時間：</label>
						<div class="time-select-area">
							<select name="goalHour">
								<%
								for (int i = 0; i <= 100; i++) {
								%>
								<option value="<%=i%>"><%=i%></option>
								<%
								}
								%>
							</select> <span>時間</span> <select name="goalMinute">
								<%
								for (int i = 0; i < 60; i += 5) {
								%>
								<option value="<%=i%>"><%=i%></option>
								<%
								}
								%>
							</select> <span>分</span>
						</div>
					</div>

					<div class="button-area">
						<button type="submit" class="submit-btn">設定確定</button>
					</div>
				</form>
			</div>
		</div>

	</div>

	<!-- ===== 戻るボタン ===== -->
	<div class="back-btn-container">
		<button type="button" onclick="history.back()" class="back-btn">←
			戻る</button>
	</div>

</body>
</html>
