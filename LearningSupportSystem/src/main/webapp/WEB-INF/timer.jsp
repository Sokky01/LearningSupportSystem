<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="true" %>
<%-- 追加① --%>
<%
    String grade = (String) session.getAttribute("grade");
    if (grade == null) grade = "bronze"; // デフォルト
%>
<%-- ① --%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>タイマー画面</title>
  <link rel="stylesheet" href="css/<%= grade %>/timer.css" />

  <style>
    .section-title {
      font-size: 24px;
      font-weight: bold;
      text-align: center;
      margin-bottom: 20px;
    }
    .section-subtitle {
      font-size: 18px;
      font-weight: bold;
      text-align: center;
      margin-bottom: 10px;
    }
    .container {
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;
    }
    .timer-box {
      border: 1px solid #ccc;
      padding: 15px;
      border-radius: 8px;
      width: 300px;
      margin-bottom: 20px;
    }
    .buttons, .right-buttons {
      display: flex;
      justify-content: center;
      gap: 10px;
      margin-top: 15px;
    }
  </style>
</head>
<body>
<header>タイマー画面</header>

<main>
  <div class="section-title">タイマー</div>

  <div class="container">

    <!-- 左：タイマー設定 -->
    <div class="timer-box">
      <div class="section-subtitle">タイマー設定</div>
      <div>
        <label>分:</label>
        <input type="number" id="setMin" min="0" max="99" value="00" style="width:60px;">
        <label>秒:</label>
        <input type="number" id="setSec" min="0" max="59" value="00" style="width:60px;">
      </div>
      <div style="margin-top:10px; font-size:12px; color:#555;">
        ※ ここでスタートする時間を設定してください。
      </div>
    </div>

    <!-- 右：タイマー表示とラップ -->
    <div class="timer-box">
      <div class="section-subtitle">タイマー表示 & ラップ記録</div>
      <div id="timer-display" style="font-size:40px;">00 : 00</div>
      <hr style="width:80%;">
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

  <div class="right-buttons">
    <button id="back" onclick="if(confirm('タイマー情報はリセットされますがよろしいですか？')) window.location.href='TimerBackServlet';">戻る</button>
  </div>
</main>

<script>
let timer;
let time = 0;
let running = false;

let countDownMode = false;
let initialTime = 0;

// ラップ記録用
let laps = [];
let lastLapTime = 0;  // ←前回ラップ時点の合計時間

const display = document.getElementById("timer-display");
const lapDiv = document.getElementById("laps");

// 時間表示更新
function updateDisplay() {
  let min = Math.floor(time / 60);
  let sec = time % 60;
  display.textContent = `${String(min).padStart(2,"0")} : ${String(sec).padStart(2,"0")}`;
}

// 初期表示
updateDisplay();

// スタート
document.getElementById("start").onclick = () => {
  if (running) return;

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
  if (!running) return;

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

  laps.push(`${String(min).padStart(2,"0")}:${String(sec).padStart(2,"0")}`);
  lapDiv.innerHTML = laps.map((v,i) => `<div>ラップ${i+1}　${v}</div>`).join("");

  lapDiv.scrollTop = lapDiv.scrollHeight;
};
</script>

</body>
</html>
