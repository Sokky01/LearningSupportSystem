<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>利用者メインメニュー</title>
</head>
<body>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>利用者用メインメニュー画面</title>
<link rel="stylesheet" href="css/userMainmenu.css">
</head>
<body>

<header>
    <div>利用者用メインメニュー画面</div>
    <div>ID 999999999</div>
</header>
	<div class="container">
	    <h1>学習支援サービス</h1>
	    <p class="subtitle">メインメニュー</p>
	
	    <div class="menu-row">
	        <form action="Learningstarttransition" method="get"><button>学習開始</button></form>
	        <button>学習記録</button>
	    </div>
	
	    <div class="menu-row">
	        <button>利用者情報</button>
	        <button>カレンダー</button>
	    </div>
	
	    <div class="menu-row">
	        <button>ランキング</button>
	        <button>ミッション</button>
	    </div>
	
	    <div class="menu-row">
	        <button>学習時間グラフ</button>
	    </div>
	</div>
	<form action="UserLogoutServlet" method="get">
	<button id="logout">ログアウト</button>

</body>
</html>