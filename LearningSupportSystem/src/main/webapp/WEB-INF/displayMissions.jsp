<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%-- セッション情報から日別ミッションリストを取得 --%>
<%@ page import="mms.model.DisplayMissionsBeans" %>
<%@ page import="mms.model.DisplayMissionsBeansList" %>
<% DisplayMissionsBeansList dmbl = (DisplayMissionsBeansList)request.getAttribute("dmbl"); %>
<html>
<head>
<meta charset="UTF-8">
<title>ミッション表示画面</title>
<link rel="stylesheet" href="./css/userMainmenu.css">

</head>
<body>

<div class="container">

	<div class="header">
		<button onclic="../../userMainmenu.jsp">メインメニュー</button>
		<button onclic="">学習開始</button>
		<button onclic="">学習記録</button>
		<button onclic="">利用者情報</button>
		<button onclic="">ランキング</button>
		<button>ミッション</button>
		<button onclic="">学習時間グラフ</button>
	</div>

	<h2>ミッション</h2>
	<p class="breadcrumb">メインメニュー &gt;&gt; ミッション</p>

	<div class="content">
		<%
		// requestにミッションのリストがセットされている かつ リストが作成されている かつ ミッションが一件以上存在する
		if(dmbl != null && dmbl.getMissions() != null && !dmbl.getMissions().isEmpty()) {
			//リスト中の全ミッションを取り出し、ミッションタイプを数値から文字列に変換する処理
			for (DisplayMissionsBeans m : dmbl.getMissions()) {
				
				String missionType = "";
				switch (m.getMissionType()) {
				case 0:
					missionType = "【科目】";
					break;
				case 1:
					missionType = "【総合】";
					break;
				case 2:
					missionType = "【資格】";
					break;
				case 3:
					missionType = "【その他】";
					break;
				default:
					missionType = "【未分類】";
				}
		
		%>
		
		<div class="mission <%= m.isAchieved() ? "Achieved" : ""%>">
			<strong><%= missionType %></strong> <%= m.getMissionName() %>
			<span class="point">(<%= m.getMissionPoint() %> pt)</span>
			<% if (m.isAchieved()) { %>
				<span>（達成済み）</span>
			<% } %>
		</div>
		<%
			}
		} else {
		%>
			<p>ミッションがありません</p>
		<%
		}
		%>
	</div>

</div>
<a href="userMainmenu.jsp" class="back-btn">戻る</a>
</body>
</html>