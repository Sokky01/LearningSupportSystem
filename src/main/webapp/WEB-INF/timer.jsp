<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="true"%>
<!DOCTYPE html>

<%-- セッション情報からグレードIDを取得して CSS に反映 --%>
<%
Integer gradeIdObj = (Integer) session.getAttribute("gradeId");
int gradeId = (gradeIdObj != null) ? gradeIdObj : 1; // デフォルトはブロンズ

String grade = "bronze"; // デフォルト
// グレードIDを英語に変換
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

<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>タイマー画面</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">

<!-- 【修正】グレード別の外部CSS を動的に読み込み -->
<link rel="stylesheet" href="css/<%= grade %>/timer.css">

<style>
/* ===== タイマー専用スタイル ===== */
.timer-container {
	max-width: 1000px;
	margin: 0 auto;
	padding: 20px;
}

.timer-header {
	text-align: center;
	margin-bottom: 30px;
}

.timer-header h2 {
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

/* タイマーコンテンツエリア */
.timer-content {
	display: flex;
	justify-content: space-around;
	flex-wrap: wrap;
	gap: 30px;
	margin-bottom: 30px;
}

/* タイマーボックス */
.timer-box {
	background: #fff;
	border: 3px solid #003366;
	border-radius: 15px;
	width: min(40vw, 320px);
	height: min(40vw, 320px);
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 20px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
	position: relative;
}

.section-subtitle {
	font-size: 18px;
	font-weight: bold;
	text-align: center;
	margin-bottom: 15px;
	color: #333;
}

/* タイマー設定ボックス */
.timer-setting-box {
	background: rgba(255, 255, 255, 0.9);
	border: 2px solid #ccc;
	border-radius: 15px;
	padding: 25px;
	width: min(40vw, 320px);
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.timer-setting-box label {
	font-weight: bold;
	margin: 0 5px;
}

.timer-setting-box input[type="number"] {
	width: 70px;
	padding: 8px;
	font-size: 16px;
	border: 2px solid #ddd;
	border-radius: 5px;
	text-align: center;
}

.timer-note {
	margin-top: 15px;
	font-size: 12px;
	color: #555;
	text-align: center;
}

/* タイマー表示 */
#timer-display {
	font-size: 48px;
	font-weight: bold;
	font-family: 'Courier New', monospace;
	color: #003366;
	margin: 20px 0;
}

/* ラップ記録エリア */
#laps {
	width: 90%;
	height: 120px;
	overflow-y: auto;
	border: none;
	background-color: transparent;
	padding: 10px;
	font-size: 14px;
	text-align: left;
	box-sizing: border-box;
	scrollbar-width: thin;
	scrollbar-color: rgba(100, 100, 100, 0.4) transparent;
}

#laps::-webkit-scrollbar {
	width: 6px;
}

#laps::-webkit-scrollbar-track {
	background: transparent;
}

#laps::-webkit-scrollbar-thumb {
	background-color: rgba(100, 100, 100, 0.4);
	border-radius: 3px;
}

#laps::-webkit-scrollbar-thumb:hover {
	background-color: rgba(100, 100, 100, 0.6);
}

#laps div {
	margin: 5px 0;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

/* ボタンエリア */
.buttons {
	display: flex;
	justify-content: center;
	flex-wrap: wrap;
	gap: 15px;
	margin: 30px 0;
}

.buttons button {
	font-size: 16px;
	padding: 12px 30px;
	border: 1px solid #aaa;
	border-radius: 8px;
	background-color: #f0f0f0;
	cursor: pointer;
	font-weight: bold;
	transition: all 0.3s;
}

.buttons button:hover {
	background-color: #e0e0e0;
	transform: translateY(-2px);
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
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
	.timer-container {
		padding: 10px;
	}
	.timer-header h2 {
		font-size: 22px;
	}
	.timer-content {
		flex-direction: column;
		align-items: center;
	}
	.timer-box, .timer-setting-box {
		width: 90%;
		max-width: 350px;
	}
	.timer-box {
		height: auto;
		border-radius: 15px;
	}
	#timer-display {
		font-size: 36px;
	}
}
</style>
</head>

<body>
	<div class="container">

		<div class="timer-container">
			<!-- ===== ヘッダー ===== -->
			<div class="timer-header">
				<h2>タイマー</h2>
				<p class="breadcrumb">メインメニュー　&gt;　学習開始　&gt;　学習ツール　&gt;　タイマー</p>
			</div>

			<!-- ===== タイマーコンテンツ ===== -->
			<div class="timer-content">
				<!-- 左：タイマー設定 -->
				<div class="timer-setting-box">
					<div class="section-subtitle">タイマー設定</div>
					<div style="text-align: center;">
						<label>分:</label> <input type="number" id="setMin" min="0"
							max="99" value="00"> <label>秒:</label> <input
							type="number" id="setSec" min="0" max="59" value="00">
					</div>
					<div class="timer-note">※ ここでスタートする時間を設定してください。</div>
				</div>

				<!-- 右：タイマー表示とラップ -->
				<div class="timer-box">
					<div class="section-subtitle">タイマー表示 & ラップ記録</div>
					<div id="timer-display">00 : 00</div>
					<hr style="width: 80%; border: 1px solid #ccc;">
					<div id="laps">
						<div>ラップ1　00:00</div>
						<div>ラップ2　00:00</div>
						<div>ラップ3　00:00</div>
					</div>
				</div>
			</div>

			<!-- ボタン -->
			<div class="buttons">
				<button id="start">スタート</button>
				<button id="stop">ストップ</button>
				<button id="reset">リセット</button>
				<button id="lap">ラップ</button>
			</div>
		</div>

	</div>

	<!-- ===== 戻るボタン ===== -->
	<div class="back-btn-container">
		<button type="button" class="back-btn"
			onclick="if(confirm('タイマー情報はリセットされますがよろしいですか？')) window.location.href='TimerBackServlet';">←
			戻る</button>
	</div>

	<script>
		let timer;
		let time = 0;
		let running = false;

		let countDownMode = false;
		let initialTime = 0;

		// ラップ記録用
		let laps = [];
		let lastLapTime = 0; // ←前回ラップ時点の合計時間

		const display = document.getElementById("timer-display");
		const lapDiv = document.getElementById("laps");

		// 時間表示更新
		function updateDisplay() {
			let min = Math.floor(time / 60);
			let sec = time % 60;
			display.textContent = `${String(min).padStart(2, "0")} : ${String(
					sec).padStart(2, "0")}`;
		}

		// 初期表示
		updateDisplay();

		// スタート
		document.getElementById("start").onclick = () => {
			if (running)
				return;

			let m = parseInt(document.getElementById("setMin").value) || 0;
			let s = parseInt(document.getElementById("setSec").value) || 0;

			if (time === 0) {
				time = m * 60 + s;
				initialTime = time;
			}

			countDownMode = time > 0;
			running = true;

			updateDisplay();

			timer = setInterval(() => {
				if (countDownMode) {
					time--;
					if (time < 0) {
						clearInterval(timer);
						running = false;
						alert("目標時間になりました！");
						return;
					}
				} else {
					time++;
				}
				updateDisplay();
			}, 1000);
		};

		// ストップ
		document.getElementById("stop").onclick = () => {
			clearInterval(timer);
			running = false;
		};

		// リセット
		document.getElementById("reset").onclick = () => {
			clearInterval(timer);
			running = false;
			time = 0;
			initialTime = 0;
			lastLapTime = 0;
			laps = [];
			updateDisplay();
			lapDiv.innerHTML = `<div>ラップ1　00:00</div><div>ラップ2　00:00</div><div>ラップ3　00:00</div>`;
		};

		// ラップ（区間ラップ方式）
		document.getElementById("lap").onclick = () => {
			if (!running)
				return;

			let totalElapsed;

			if (countDownMode) {
				totalElapsed = initialTime - time;
			} else {
				totalElapsed = time;
			}

			// 前回ラップとの差を取る（区間タイム）
			let diff = totalElapsed - lastLapTime;
			lastLapTime = totalElapsed; // 次回のために保存

			let min = Math.floor(diff / 60);
			let sec = diff % 60;

			laps.push(`${String(min).padStart(2, "0")}:${String(sec).padStart(
					2, "0")}`);
			lapDiv.innerHTML = laps.map((v, i) => `<div>ラップ${i + 1}　${v}</div>`)
					.join("");

			lapDiv.scrollTop = lapDiv.scrollHeight;
		};
	</script>

</body>
</html>
