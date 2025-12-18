<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- taglibの行は削除しました --%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>利用者情報画面</title>
    <link rel="stylesheet" href="css/account_info.css?v=7">
</head>
<body>

<header>
    <div>利用者情報画面</div>
</header>

<nav class="navbar">
    <a href="<%= request.getContextPath() %>/UserMainmenuServlet">メインメニュー</a>
    <a href="<%= request.getContextPath() %>/Learningstarttransition">学習開始</a>
    <a href="#">学習記録</a>
    <a href="#">学習時間グラフ</a>
    <a href="#">ミッション</a>
    <a href="#">ランキング</a>
</nav>

<div class="breadcrumb">
    <span><a href="<%= request.getContextPath() %>/UserMainmenuServlet">メインメニュー</a> &gt;&gt; 利用者情報</span>
</div>

    <div class="container">
    <h1>プロフィール</h1>

    <!-- ここが「枠」 -->
    <div class="profile-box">
        <div class="profile-list">
            <div class="profile-row">
                <span class="label">ID</span>
                <span class="value">*****</span>
            </div>
            <div class="profile-row">
                <span class="label">名前</span>
                <span class="value">*****</span>
            </div>
            <div class="profile-row">
                <span class="label">ニックネーム</span>
                <span class="value">*****</span>
            </div>
            <div class="profile-row">
                <span class="label">公開設定</span>
                <span class="value">非公開</span>
            </div>
            <div class="profile-row">
                <span class="label">パスワード</span>
                <span class="value">*****</span>
            </div>
            <div class="profile-row">
                <span class="label">グレード</span>
                <span class="value">*****</span>
            </div>
        </div>
    </div>
</div>




            
            <div class="floating-actions">
    <form action="AccountEditServlet" method="get">
        <button id="settings" type="submit">設定変更</button>
    </form>

    <button id="backBtn" onclick="location.href='<%= request.getContextPath() %>/UserMainmenuServlet'">戻る</button>
</div>
        </div>
    </div>

</body>
</html>