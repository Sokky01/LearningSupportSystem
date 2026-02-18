<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>更新完了</title>
    <link rel="stylesheet" href="css/admin-theme.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">
    
    <style>
    /* 完了画面専用スタイル */
    body {
        background: var(--admin-bg);
        display: flex;
        align-items: center;
        justify-content: center;
        min-height: 100vh;
        margin: 0;
    }
    
    .result-container {
        max-width: 500px;
        width: 90%;
        background: var(--admin-bg-white);
        border: 1px solid var(--admin-border);
        border-radius: 8px;
        padding: 60px 40px;
        text-align: center;
        box-shadow: var(--admin-shadow-hover);
    }

    .icon {
        font-size: 64px;
        margin-bottom: 20px;
    }

    .message {
        font-size: 24px;
        font-weight: 600;
        color: var(--admin-success);
        margin-bottom: 30px;
    }

    .description {
        color: var(--admin-text-muted);
        font-size: 14px;
        margin-bottom: 40px;
    }

    .back-btn {
        background: var(--admin-secondary);
        color: white;
        border: none;
        padding: 12px 40px;
        font-size: 15px;
        font-weight: 500;
        border-radius: 4px;
        cursor: pointer;
        transition: all 0.2s;
    }

    .back-btn:hover {
        background: var(--admin-primary);
        box-shadow: var(--admin-shadow-hover);
        transform: translateY(-2px);
    }

    @media (max-width: 768px) {
        .result-container {
            padding: 40px 20px;
        }
        
        .icon {
            font-size: 48px;
        }
        
        .message {
            font-size: 20px;
        }
    }
    </style>
</head>
<body>

<div class="result-container">
    <div class="icon">✅</div>
    <p class="message">更新完了しました</p>
    <p class="description">利用者アカウント情報が正常に更新されました。</p>

    <form action="UserViewServlet" method="post">
        <button type="submit" class="back-btn">← 一覧に戻る</button>
    </form>
</div>

</body>
</html>
