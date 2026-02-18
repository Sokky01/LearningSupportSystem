<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String errorMsg=(String) request.getAttribute("errorMsg");%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ログイン画面</title>
<link rel="stylesheet" href="css/unified-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<style>
/* ===== ログイン画面専用スタイル ===== */
body {
    font-family: "Yu Gothic", "Hiragino Sans", sans-serif;
    background: #dff5ff;
    margin: 0;
    padding: 0;
    min-height: 100vh;
    overflow-x: hidden;
    background-image:
        radial-gradient(circle at 80% 20%, #c2ffb0 0%, #dff5ff 60%),
        radial-gradient(circle at 20% 30%, #fff5b7 0%, #dff5ff 60%),
        radial-gradient(circle at 60% 70%, #e7d4ff 0%, #dff5ff 60%),
        radial-gradient(circle at 10% 85%, #ffdede 0%, #dff5ff 60%);
    background-size: 100% 100%;
    background-attachment: fixed;
    background-repeat: no-repeat;
    display: flex;
    justify-content: center;
    align-items: center;
}

.login-container {
    background: rgba(255, 255, 255, 0.95);
    border: 3px solid #667eea;
    border-radius: 16px;
    padding: 40px 50px;
    max-width: 450px;
    width: 90%;
    box-shadow: 0 8px 32px rgba(102, 126, 234, 0.3);
}

.login-container h1 {
    font-size: 28px;
    font-weight: 600;
    color: #2c3e50;
    margin-bottom: 10px;
    text-align: center;
    border-bottom: 3px solid #667eea;
    padding-bottom: 15px;
}

.login-container p {
    text-align: center;
    color: #7f8c8d;
    font-size: 14px;
    margin-bottom: 30px;
}

.form-group {
    margin-bottom: 25px;
}

.form-group label {
    display: block;
    font-weight: 600;
    color: #2c3e50;
    margin-bottom: 8px;
    font-size: 16px;
}

.form-group input[type="text"],
.form-group input[type="password"] {
    width: 100%;
    padding: 12px 16px;
    border: 2px solid #bdc3c7;
    border-radius: 8px;
    font-size: 15px;
    font-family: inherit;
    background-color: #ffffff;
    color: #2c3e50;
    transition: all 0.3s ease;
    box-sizing: border-box;
}

.form-group input[type="text"]:focus,
.form-group input[type="password"]:focus {
    outline: none;
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.checkbox-container {
    display: flex;
    align-items: center;
    margin-bottom: 25px;
}

.checkbox-container input[type="checkbox"] {
    margin-right: 8px;
    width: 18px;
    height: 18px;
    cursor: pointer;
}

.checkbox-container label {
    font-size: 14px;
    color: #7f8c8d;
    cursor: pointer;
    user-select: none;
}

.error-message {
    background-color: #f8d7da;
    border-left: 4px solid #e74c3c;
    color: #721c24;
    padding: 12px 16px;
    border-radius: 8px;
    margin-bottom: 20px;
    font-size: 14px;
}

.submit-btn {
    width: 100%;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border: none;
    padding: 14px 24px;
    border-radius: 8px;
    font-size: 18px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.submit-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 20px rgba(102, 126, 234, 0.5);
}

.submit-btn:active {
    transform: translateY(0);
}

/* レスポンシブ対応 */
@media (max-width: 768px) {
    .login-container {
        padding: 30px 25px;
    }
    
    .login-container h1 {
        font-size: 24px;
    }
}
</style>
</head>
<body>

<div class="login-container">
    <form action="AdminLoginServlet" method="post">
        <h1>学習支援システム</h1>
        <h2>ログイン</h2>
        <p>ご登録のIDとパスワードを入力してください。</p>
        
        <% if(errorMsg != null && !errorMsg.isEmpty()){ %>
        <div class="error-message">
            <%= errorMsg %>
        </div>
        <% } %>
        
        <div class="form-group">
            <label for="AccountId">ID</label>
            <input type="text" id="AccountId" name="AccountId" required>
        </div>
        
        <div class="form-group">
            <label for="input_pass">パスワード</label>
            <input type="password" id="input_pass" name="Password" required>
        </div>
        
        <div class="checkbox-container">
            <input type="checkbox" id="input_checkbox">
            <label for="input_checkbox">パスワードを表示する</label>
        </div>
        
        <input type="submit" value="ログイン" class="submit-btn">
    </form>
</div>

<script>
// HTMLの読み込みが終わってから処理を開始
document.addEventListener('DOMContentLoaded', function(event) {
    // パスワード入力欄を取得
    const pw = document.getElementById('input_pass');
    
    // 表示切替チェックボックスを取得
    const checkbox = document.getElementById('input_checkbox');
    
    // チェックボックスにイベントリスナーを設定
    checkbox.addEventListener('change', function(event) {
        // チェックボックスに連動して、パスワード入力欄のtype属性を変更
        if(this.checked) {
            pw.setAttribute('type', 'text');
        } else {
            pw.setAttribute('type', 'password');
        }
    }, false);
}, false);
</script>

</body>
</html>