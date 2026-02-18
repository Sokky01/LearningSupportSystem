<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%-- セッション情報からグレードIDを取得 --%>
<%
Integer gradeIdObj = (Integer) request.getAttribute("gradeId");
int gradeId = (gradeIdObj != null) ? gradeIdObj : 1; // デフォルトはブロンズ

String grade = "bronze"; // デフォルト
switch (gradeId) {
case 5:
	grade = "diamond";
	break;
case 4:
	grade = "platinum";
	break;
case 3:
	grade = "gold";
	break;
case 2:
	grade = "silver";
	break;
case 1:
	grade = "bronze";
	break;
}
%>

<html>
<head>
<meta charset="UTF-8">
<title>学習ツール</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<!-- 【修正】グレード別の外部CSS を動的に読み込み -->
<link rel="stylesheet" href="css/<%= grade %>/learningTools.css">

<style>
/* ===== 学習ツール専用スタイル ===== */
.learning-tools-container {
	max-width: 900px;
	margin: 0 auto;
	padding: 20px;
}

.learning-tools-header {
	text-align: center;
	margin-bottom: 30px;
}

.learning-tools-header h2 {
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

/* 学習ツールコンテンツ */
.learning-tools-content {
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	padding: 40px;
	text-align: center;
}

.subject-name {
	font-size: 18px;
	color: #666;
	margin-bottom: 30px;
}

.subject-name strong {
	color: #333;
	font-size: 20px;
}

/* タイマーボックス */
.timer-box {
	background: linear-gradient(135deg, #fffccf 0%, #fff5b0 100%);
	border: 2px solid #f0e68c;
	border-radius: 10px;
	width: 250px;
	margin: 30px auto;
	padding: 20px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.timer-box p {
	font-size: 16px;
	font-weight: bold;
	color: #333;
	margin-bottom: 10px;
}

#timer {
	font-size: 3em;
	font-weight: bold;
	color: #333;
	margin: 10px 0;
	font-family: 'Courier New', monospace;
}

/* ボタンエリア */
.button-area {
	display: flex;
	justify-content: center;
	gap: 20px;
	margin-top: 30px;
	flex-wrap: wrap;
}

.finish-btn {
	padding: 15px 40px;
	background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
	color: #fff;
	text-decoration: none;
	border-radius: 8px;
	font-weight: bold;
	font-size: 18px;
	border: none;
	cursor: pointer;
	transition: all 0.3s;
	box-shadow: 0 4px 15px rgba(240, 147, 251, 0.4);
}

.finish-btn:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 20px rgba(240, 147, 251, 0.6);
}

.timer-btn {
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

.timer-btn:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
}

/* レスポンシブ対応 */
@media ( max-width : 768px) {
	.learning-tools-container {
		padding: 10px;
	}
	.learning-tools-header h2 {
		font-size: 22px;
	}
	.learning-tools-content {
		padding: 20px;
	}
	.timer-box {
		width: 200px;
	}
	#timer {
		font-size: 2.5em;
	}
	.button-area {
		flex-direction: column;
		align-items: center;
	}
	.finish-btn, .timer-btn {
		width: 100%;
		max-width: 300px;
	}
}
</style>

<script>
	let timerInterval;
	let startTime;
	let elapsedTime = 0;

	window.addEventListener("pageshow", (event) => {
		// 履歴キャッシュ(bfcache)から戻った場合も含む
		initializeTimer();
	});

	function initializeTimer() {
		const storedStart = localStorage.getItem("startTime");
		if (storedStart) {
			startTime = new Date(parseInt(storedStart));
		} else {
			startTime = new Date();
			localStorage.setItem("startTime", startTime.getTime());
		}

		startTimer();
	}

	function startTimer() {
		if (timerInterval)
			clearInterval(timerInterval);
		timerInterval = setInterval(() => {
			const now = new Date();
			elapsedTime = Math.floor((now - startTime) / 1000);
			displayTime(elapsedTime);
		}, 1000);
	}

	function displayTime(seconds) {
		const min = Math.floor(seconds / 60);
		const sec = seconds % 60;
		document.getElementById("timer").textContent = String(min).padStart(2,
				'0')
				+ ":" + String(sec).padStart(2, '0');
	}

	function stopTimer() {
		clearInterval(timerInterval);
	}

	function finishLearning() {
		// ▼ 学習終了の確認ダイアログ
		const result = confirm("学習を終了しますか？");

		if (!result) {
			// キャンセルされた場合 → 何もしない
			return;
		}

		stopTimer();

		// 学習開始時刻を削除
		localStorage.removeItem("startTime");

		const form = document.createElement("form");
		form.method = "POST";
		form.action = "CountUpServlet";

		const inputTime = document.createElement("input");
		inputTime.type = "hidden";
		inputTime.name = "learningTime";
		inputTime.value = elapsedTime;
		form.appendChild(inputTime);

		document.body.appendChild(form);
		form.submit();
	}
</script>
</head>

<body>
	<div class="container">

		<div class="learning-tools-container">
			<!-- ===== ヘッダー ===== -->
			<div class="learning-tools-header">
				<h2>学習ツール</h2>
				<p class="breadcrumb">メインメニュー　&gt;　学習開始　&gt;　学習ツール</p>
			</div>

			<!-- ===== 学習ツールコンテンツ ===== -->
			<div class="learning-tools-content">
				<div class="subject-name">
					<p>
						現在の学習内容：<strong><c:out value="${subjectName}" /></strong>
					</p>
				</div>

				<div class="timer-box">
					<p>現在の学習時間</p>
					<div id="timer">00:00</div>
				</div>

				<div class="button-area">
					<button type="button" onclick="finishLearning()" class="finish-btn">学習終了</button>
					<button type="button" onclick="location.href='TimerSettingServlet'"
						class="timer-btn">タイマー</button>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
