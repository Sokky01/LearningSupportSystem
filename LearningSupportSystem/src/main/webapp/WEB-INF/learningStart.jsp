<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="lrs.lrs0701.model.LearningstarttransitionBeans" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>学習開始画面</title>
<link rel="stylesheet" href="css/learningStart.css">
</head>
<body>

<header>
    <div>学習開始画面</div>
</header>

<nav class="navbar">
    <a href="<%= request.getContextPath() %>/UserMainmenuServlet">メインメニュー</a>
    <a href="#">アカウント情報</a>
    <a href="#">学習記録情報</a>
    <a href="#">学習時間グラフ</a>
    <a href="#">ミッション</a>
    <a href="#">ランキング</a>
</nav>

<div class="breadcrumb">
    <span>メインメニュー &gt;&gt; 学習開始</span>
</div>

<div class="container">
    <div class="title-box">
        <h1>学習を開始しましょう</h1>
    </div>

    <div class="content-box">
        <label for="subject">学習する教科を選択：</label>
        <select id="subject" name="subject">
            <option value="">選択してください</option>
            <% 
                List<LearningstarttransitionBeans> subjectList =
                    (List<LearningstarttransitionBeans>) request.getAttribute("subjectList");
                if (subjectList != null) {
                    for (LearningstarttransitionBeans subject : subjectList) {
            %>
                <option value="<%= subject.getSubjectid() %>">
                    <%= subject.getSubjectname() %>
                </option>
            <% 
                    }
                }
            %>
        </select>

        <form action="CountUpServlet" method="get" onsubmit="return checkBeforeStart();">
            <input type="hidden" name="subjectId" id="selectedSubjectId">
            <button type="submit" id="startBtn">学習開始</button>
        </form>

        <p class="hint">
            クリックすると選択した教科の学習画面に移動します。<br>
            学習が完了すると自動で記録されます。
        </p>
    </div>

    <button id="backBtn" onclick="location.href='userMainmenu.jsp'">戻る</button>
</div>

<script>
    // 選択された教科IDをhiddenにセット
    document.getElementById('subject').addEventListener('change', function() {
        document.getElementById('selectedSubjectId').value = this.value;
    });

    function checkBeforeStart() {
        // ▼ 例：subjectId が設定されていない場合のチェック
        const subjectId = document.getElementById("selectedSubjectId").value;

        if (!subjectId) {
            alert("科目が選択されていません。選択してください。");
            return false;  // 送信しない
        }

        // ▼ 確認ダイアログを出したい場合（任意）
        return confirm("学習を開始しますか？");

        // true が返る → そのまま送信
        // false → 送信キャンセル
    }
</script>

</body>
</html>
