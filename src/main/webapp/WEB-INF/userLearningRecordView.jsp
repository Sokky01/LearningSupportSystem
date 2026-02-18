<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%-- セッション情報から学習記録を取得 --%>
<%@ page import="java.util.List"%>
<%@ page import="model.userLearningRecordBeans"%>
<%
List<userLearningRecordBeans> userRecordList = (List<userLearningRecordBeans>) request
		.getAttribute("userRecordList");
%>

<%!// 秒 → 「時間 / 分 / 秒」に変換するメソッド
	public String formatTime(int seconds) {
		if (seconds >= 3600) {
			int hours = seconds / 3600;
			int minutes = (seconds % 3600) / 60;
			return hours + "時間" + minutes + "分";
		} else if (seconds >= 60) {
			int minutes = seconds / 60;
			return minutes + "分";
		} else {
			return seconds + "秒";
		}
	}
	
	// 分 → 「時間 / 分」に変換するメソッド（目標時間用）
	public String formatMinutes(int minutes) {
		if (minutes >= 60) {
			int hours = minutes / 60;
			int mins = minutes % 60;
			if (mins > 0) {
				return hours + "時間" + mins + "分";
			} else {
				return hours + "時間";
			}
		} else if (minutes > 0) {
			return minutes + "分";
		} else {
			return "0分";
		}
	}%>



<html>
<head>
<meta charset="UTF-8">
<title>学習記録情報画面</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">
<style>
/* ===== 学習記録専用スタイル ===== */
.record-container {
	max-width: 1000px;
	margin: 0 auto;
	padding: 20px;
}

.record-header {
	text-align: center;
	margin-bottom: 30px;
}

.record-header h2 {
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

/* 学習記録コンテンツ */
.record-content {
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	padding: 30px;
}

/* サマリー表示 */
.summary {
	display: flex;
	justify-content: space-around;
	margin-bottom: 30px;
	gap: 20px;
	flex-wrap: wrap;
}

.summary-item {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: #fff;
	padding: 20px 30px;
	border-radius: 10px;
	text-align: center;
	flex: 1;
	min-width: 250px;
	box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.summary-label {
	font-size: 14px;
	margin-bottom: 10px;
	opacity: 0.9;
}

.summary-value {
	font-size: 32px;
	font-weight: bold;
	margin-bottom: 5px;
}

.summary-sub {
	font-size: 14px;
	opacity: 0.8;
	margin-top: 5px;
}

/* テーブルスタイル */
.record-table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 30px;
}

.record-table th {
	background: #667eea;
	color: #fff;
	padding: 15px;
	text-align: center;
	font-weight: bold;
	border: 1px solid #5a6fd8;
}

.record-table td {
	padding: 12px;
	text-align: center;
	border: 1px solid #ddd;
}

.record-table tr:hover {
	background: #f5f7ff;
}

.highlight {
	color: #e74c3c;
	font-weight: bold;
}

.no-data {
	text-align: center;
	padding: 40px;
	color: #999;
	font-size: 16px;
}

/* ボタンエリア */
.button-area {
	display: flex;
	justify-content: center;
	gap: 20px;
	margin-top: 20px;
	flex-wrap: wrap;
}

.delete-btn {
	padding: 12px 30px;
	background: #e74c3c;
	color: #fff;
	border: none;
	border-radius: 8px;
	font-weight: bold;
	font-size: 16px;
	cursor: pointer;
	transition: all 0.3s;
}

.delete-btn:hover {
	background: #c0392b;
	transform: translateY(-2px);
	box-shadow: 0 4px 12px rgba(231, 76, 60, 0.4);
}

.goal-btn {
	padding: 12px 30px;
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

.goal-btn:hover {
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
	.record-container {
		padding: 10px;
	}
	.record-header h2 {
		font-size: 22px;
	}
	.record-content {
		padding: 15px;
	}
	.summary {
		flex-direction: column;
	}
	.summary-item {
		min-width: 100%;
	}
	.record-table {
		font-size: 14px;
	}
	.record-table th, .record-table td {
		padding: 8px;
	}
	.button-area {
		flex-direction: column;
	}
	.delete-btn, .goal-btn {
		width: 100%;
	}
}
</style>
</head>

<body>
	<jsp:include page="common/header.jsp" />
	<div class="container">

		<div class="record-container">
			<!-- ===== ヘッダー ===== -->
			<div class="record-header">
				<h2>学習記録</h2>
				<p class="breadcrumb">メインメニュー　&gt;　学習記録情報</p>
			</div>

			<!-- ===== 学習記録コンテンツ ===== -->
			<div class="record-content">

				<!-- サマリー表示 -->
				<div class="summary">
					<div class="summary-item">
						<div class="summary-label">累計学習時間</div>
						<div class="summary-value"><%= formatTime((Integer)request.getAttribute("totalStudyTime")) %></div>
						<div class="summary-sub">(<%= request.getAttribute("totalStudyTime") %>秒)</div>
					</div>
					<div class="summary-item">
						<div class="summary-label">累計学習目標時間</div>
						<div class="summary-value"><%= formatTime((Integer)request.getAttribute("totalGoalTime")) %></div>
						<div class="summary-sub">(<%= request.getAttribute("totalGoalTime") %>秒)</div>
					</div>
				</div>

				<!-- 削除フォーム -->
				<form action="DeleteLearningRecordServlet" method="post"
					onsubmit="return confirm('選択した科目の学習時間を0に戻します。よろしいですか？');">

					<table class="record-table">
						<thead>
							<tr>
								<th>選択</th>
								<th>科目名</th>
								<th>累計学習時間</th>
								<th>目標時間</th>
							</tr>
						</thead>
						<tbody>
							<%
							if (userRecordList != null && !userRecordList.isEmpty()) {
								for (userLearningRecordBeans rec : userRecordList) {
									boolean isOver = rec.getSubjectTotal() >= rec.getSubjectGoal();
							%>
							<tr>
								<td><input type="checkbox" name="subjectIds"
									value="<%=rec.getSubjectId()%>">
								</td>
								<td><%=rec.getSubjectName()%></td>
								
								<td class="<%=isOver ? "highlight" : ""%>">
									<%= formatTime(rec.getSubjectTotal()) %>
								</td>
								<td>
									<%= formatMinutes(rec.getSubjectGoal()) %>
								</td>
							</tr>
							<%
							}
							%>
							<input type="hidden" name="studentNo"
								value="<%=userRecordList.get(0).getStudentNo()%>">
							<%
							} else {
							%>
							<tr>
								<td colspan="4" class="no-data">📭 データがありません</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>

					<!-- ボタンエリア -->
					<%
					if (userRecordList != null && !userRecordList.isEmpty()) {
					%>
					<div class="button-area">
						<button type="submit" class="delete-btn">削除</button>
					</div>
				</form>

				<!-- 目標設定ボタン -->
				<div class="button-area">
					<form action="SetGoalServlet" method="get" style="margin: 0;">
						<input type="hidden" name="studentNo"
							value="<%=userRecordList.get(0).getStudentNo()%>">
						<button type="submit" class="goal-btn">目標設定</button>
					</form>
				</div>
				<%
				} else {
				%>
				</form>
				<%
				}
				%>

			</div>
		</div>

	</div>

	<!-- ===== 戻るボタン ===== -->
	<div class="back-btn-container">
    <a href="<%=request.getContextPath()%>/UserMainmenuServlet" class="back-btn">← メインメニューに戻る</a>
</div>

</body>
</html>
