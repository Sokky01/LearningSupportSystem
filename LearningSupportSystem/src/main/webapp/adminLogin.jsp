<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウントログイン画面</title>
<link rel="stylesheet" href="acountlogin.css">
<style>
   
</style>

</head>
<body>
<form action="AdminLoginServlet" method="get">
<h1>ログイン</h1>
<p>ご登録のIDとパスワードを入力しログインしてください。</p>
<h1>ID</h1>
<input type="text" name="AccountId" >

<h1><label for="input_pass">パスワード</label></h1>
<input type="password" id="input_pass" name="Password">
<p><label for="input_checkbox"><input type="checkbox" id="input_checkbox">パスワードを表示する</label></p>

<script>


//HTMLの読み込みが終わってから処理を開始
  document.addEventListener('DOMContentLoaded',function(event){

          //パスワード入力欄を取得  getElementById()メソッドを使い取得
          const pw =document.getElementById('input_pass');

         //表示切替ラジオを取得  getElementById()メソッドを使い取得
         const checkbox =document.getElementById('input_checkbox');

         //ラジオにイベントリスナーを設定
         checkbox.addEventListener('change',function(evevnt){

           //ラジオに連動して、パスワード入力欄のtype属性を変更
           if(this.checked){
                 pw.setAttribute('type','text');
           }else{
                 pw.setAttribute('type','password');
            }
          },false);

  },false);
</script>
<p><input type="submit" value="ログイン"></p>
</form>
</body>
</html>