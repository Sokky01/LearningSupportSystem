<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%-- セッション情報から日別ミッションリストを取得 --%>
<%@ page import="model.DisplayMissionsBeans"%>
<%@ page import="model.DisplayMissionsBeansList"%>
<%
DisplayMissionsBeansList dmbl = (DisplayMissionsBeansList) request.getAttribute("dmbl");
%>

<html>
<head>
<meta charset="UTF-8">
<title>ミッション表示画面</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<style>
/* ===== ミッション専用スタイル ===== */
.missions-container {
	max-width: 900px;
	margin: 0 auto;
	padding: 20px;
}

.missions-header {
	text-align: center;
	margin-bottom: 30px;
}

.missions-header h2 {
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

/* ミッション一覧 */
.missions-content {
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	max-height: 500px;
	overflow-y: auto;
	scrollbar-width: none;
	-ms-overflow-style: none;
}

.missions-content::-webkit-scrollbar {
	display: none;
}

/* 個別ミッションカード */
.mission {
	padding: 20px;
	margin-bottom: 15px;
	border-radius: 8px;
	border-left: 5px solid #667eea;
	background: #f8f9ff;
	transition: all 0.3s ease;
	position: relative;
}

.mission:hover {
	transform: translateX(5px);
	box-shadow: 0 4px 12px rgba(102, 126, 234, 0.2);
}

.mission:last-child {
	margin-bottom: 0;
}

/* ミッションタイプ別の色分け */
.mission.type-subject {
	border-left-color: #667eea;
	background: linear-gradient(135deg, #f8f9ff 0%, #e8ebff 100%);
}

.mission.type-general {
	border-left-color: #f093fb;
	background: linear-gradient(135deg, #fff8fd 0%, #ffe8f7 100%);
}

.mission.type-qualification {
	border-left-color: #4facfe;
	background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%);
}

.mission.type-other {
	border-left-color: #43e97b;
	background: linear-gradient(135deg, #f0fff4 0%, #dcfce7 100%);
}

/* 達成済みミッション */
.mission.Achieved {
	background: linear-gradient(135deg, #d1d5db 0%, #e5e7eb 100%);
	border-left-color: #9ca3af;
	opacity: 0.7;
}

.mission.Achieved:hover {
	transform: none;
	box-shadow: none;
}

/* ミッションタイプバッジ */
.mission-type {
	display: inline-block;
	font-weight: bold;
	font-size: 14px;
	padding: 4px 12px;
	border-radius: 20px;
	margin-right: 10px;
}

.type-subject .mission-type {
	background: #667eea;
	color: #fff;
}

.type-general .mission-type {
	background: #f093fb;
	color: #fff;
}

.type-qualification .mission-type {
	background: #4facfe;
	color: #fff;
}

.type-other .mission-type {
	background: #43e97b;
	color: #fff;
}

.Achieved .mission-type {
	background: #9ca3af;
	color: #fff;
}

/* ミッション名 */
.mission-name {
	font-size: 16px;
	font-weight: 500;
	color: #333;
	margin: 5px 0;
}

/* ポイント表示 */
.mission-point {
	display: inline-block;
	font-size: 18px;
	font-weight: bold;
	color: #667eea;
	margin-left: 10px;
}

.Achieved .mission-point {
	color: #9ca3af;
}

/* 達成済みバッジ */
.achieved-badge {
	display: inline-block;
	background: #10b981;
	color: #fff;
	padding: 4px 12px;
	border-radius: 20px;
	font-size: 12px;
	font-weight: bold;
	margin-left: 10px;
}

/* データなしメッセージ */
.no-missions {
	text-align: center;
	padding: 60px 20px;
	color: #999;
	font-size: 16px;
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
}

.back-btn:hover {
	background: #5a6268;
}

/* レスポンシブ対応 */
@media ( max-width : 768px) {
	.missions-container {
		padding: 10px;
	}
	.missions-header h2 {
		font-size: 22px;
	}
	.mission {
		padding: 15px;
	}
	.mission-name {
		font-size: 14px;
	}
	.mission-point {
		font-size: 16px;
	}
}
</style>
</head>

<body>
	<jsp:include page="common/header.jsp" />
	<div class="container">



		<div class="missions-container">
			<!-- ===== ヘッダー ===== -->
			<div class="missions-header">
				<h2>ミッション</h2>
				<p class="breadcrumb">メインメニュー　&gt;　ミッション</p>
			</div>

			<!-- ===== ミッション一覧 ===== -->
			<div class="missions-content">
				<%
				// requestにミッションのリストがセットされている かつ リストが作成されている かつ ミッションが一件以上存在する
				if (dmbl != null && dmbl.getMissions() != null && !dmbl.getMissions().isEmpty()) {
					// リスト中の全ミッションを取り出し、ミッションタイプを数値から文字列に変換する処理
					for (DisplayMissionsBeans m : dmbl.getMissions()) {

						String missionType = "";
						String missionClass = "";

						switch (m.getMissionType()) {
						case 0:
					missionType = "科目";
					missionClass = "type-subject";
					break;
						case 1:
					missionType = "総合";
					missionClass = "type-general";
					break;
						case 2:
					missionType = "資格";
					missionClass = "type-qualification";
					break;
						case 3:
					missionType = "その他";
					missionClass = "type-other";
					break;
						default:
					missionType = "未分類";
					missionClass = "type-other";
						}
				%>

				<div
					class="mission <%=missionClass%> <%=m.isAchieved() ? "Achieved" : ""%>">
					<span class="mission-type"><%=missionType%></span> <span
						class="mission-name"><%=m.getMissionName()%></span> <span
						class="mission-point"><%=m.getMissionPoint()%> pt</span>
					<%
					if (m.isAchieved()) {
					%>
					<span class="achieved-badge">✓ 達成済み</span>
					<%
					}
					%>
				</div>

				<%
				}
				} else {
				%>
				<div class="no-missions">
					<p>📭 現在利用可能なミッションがありません</p>
				</div>
				<%
				}
				%>
			</div>
		</div>

	</div>

	<!-- ===== 戻るボタン ===== -->
	<div class="back-btn-container">
		<a href="<%=request.getContextPath()%>/UserMainmenuServlet"
			class="back-btn">← メインメニューに戻る</a>
	</div>

</body>
</html>