<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- 追加③ --%>
<%
    String grade = (String) session.getAttribute("grade");
    if (grade == null) grade = "bronze"; // デフォルト
%>
<%-- ③ --%><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学習ツール</title>
    <link rel="stylesheet" type="text/css" href="css/<%= grade %>/learningTools.css">
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
        if (timerInterval) clearInterval(timerInterval);
        timerInterval = setInterval(() => {
            const now = new Date();
            elapsedTime = Math.floor((now - startTime) / 1000);
            displayTime(elapsedTime);
        }, 1000);
    }

    function displayTime(seconds) {
        const min = Math.floor(seconds / 60);
        const sec = seconds % 60;
        document.getElementById("timer").textContent =
            String(min).padStart(2, '0') + ":" + String(sec).padStart(2, '0');
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
        <h2>学習ツール</h2>
        <p>現在の学習内容：<c:out value="${subjectName}" /></p>

        <div class="timer-box">
            <p>現在の学習時間</p>
            <div id="timer">00:00</div>
        </div>

        <div class="button-area">
            <button type="button" onclick="finishLearning()">学習終了</button>
            <button type="button" onclick="location.href='TimerSettingServlet'">タイマー</button>
        </div>
    </div>
</body>
</html>
