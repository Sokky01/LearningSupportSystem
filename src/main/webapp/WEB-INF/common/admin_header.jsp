<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.AdminLoginBeans" %>
<%
// コンテキストパス（プロジェクト名）を取得
String path = request.getContextPath();

// 管理者情報取得
AdminLoginBeans adminLogin = (AdminLoginBeans) session.getAttribute("Loginbeans");
int adminId = (adminLogin != null) ? adminLogin.getAccountId() : 0;
%>

<style>
/* ===== 管理者用ナビゲーションバー ===== */
.admin-navbar {
    background: linear-gradient(135deg, #495057 0%, #343a40 100%);
    color: white;
    width: 100%;
    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    font-family: 'Helvetica Neue', Arial, sans-serif;
    margin-bottom: 0;
}

/* コンテナ */
.admin-navbar .nav-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 20px;
}

/* 左側：タイトル */
.admin-navbar .nav-title {
    font-size: 20px;
    font-weight: 600;
    color: white;
    padding: 15px 0;
}

/* 中央：リンクグループ */
.admin-navbar .nav-links-center {
    display: flex;
    align-items: center;
    flex: 1;
    justify-content: center;
    gap: 5px;
}

/* 右側：ID・ログアウト */
.admin-navbar .nav-links-right {
    display: flex;
    align-items: center;
    gap: 15px;
}

/* ===== リンクのスタイル（共通） ===== */
.admin-navbar a {
    color: #ecf0f1;
    text-decoration: none;
    padding: 15px 18px;
    display: inline-block;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.3s ease;
    border-radius: 4px;
    background-color: transparent;
}

.admin-navbar a:hover {
    color: white;
    background-color: rgba(255, 255, 255, 0.1);
}

/* 無効化されたリンク */
.admin-navbar a.disabled {
    color: #6c757d;
    cursor: not-allowed;
    opacity: 0.5;
    pointer-events: none;
}

/* ID表示 */
.admin-navbar .admin-id-badge {
    background: rgba(255, 255, 255, 0.15);
    padding: 8px 16px;
    border-radius: 4px;
    font-size: 13px;
    font-weight: 500;
    color: white;
}

/* ログアウトボタン */
.admin-navbar .logout-btn {
    background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
    padding: 10px 20px;
    border-radius: 20px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    color: white !important;
}

.admin-navbar .logout-btn:hover {
    background: linear-gradient(135deg, #c0392b 0%, #a93226 100%);
    box-shadow: 0 4px 8px rgba(0,0,0,0.2);
    transform: translateY(-1px);
}

/* レスポンシブ対応 */
@media (max-width: 1200px) {
    .admin-navbar .nav-links-center {
        gap: 2px;
    }
    
    .admin-navbar a {
        padding: 12px 12px;
        font-size: 13px;
    }
}

@media (max-width: 992px) {
    .admin-navbar .nav-container {
        flex-wrap: wrap;
        padding: 10px;
    }
    
    .admin-navbar .nav-title {
        width: 100%;
        text-align: center;
        padding: 10px 0;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        margin-bottom: 10px;
    }
    
    .admin-navbar .nav-links-center {
        width: 100%;
        flex-wrap: wrap;
        justify-content: center;
        order: 2;
        margin-top: 10px;
    }
    
    .admin-navbar .nav-links-right {
        width: 100%;
        justify-content: center;
        order: 3;
        margin-top: 10px;
        padding-top: 10px;
        border-top: 1px solid rgba(255, 255, 255, 0.1);
    }
    
    .admin-navbar a {
        padding: 10px 12px;
        font-size: 13px;
    }
}
</style>

<nav class="admin-navbar">
    <div class="nav-container">
        <!-- 左側：タイトル -->
        <div class="nav-title">
            📊 管理者システム
        </div>
        
        <!-- 中央：ナビゲーションリンク -->
        <div class="nav-links-center">
            <a href="<%=path%>/AdminMainmenuServlet">メインメニュー</a>
            <a href="#" class="disabled">学習記録情報</a>
            <a href="<%=path%>/AdminViewServlet">アカウント管理</a>
            <a href="#" class="disabled">クラス情報管理</a>
            <a href="#" class="disabled">科目情報管理</a>
        </div>
        
        <!-- 右側：ID・ログアウト -->
        <div class="nav-links-right">
            <div class="admin-id-badge">ID: <%= adminId %></div>
            <a href="<%=path%>/AdminLogoutServlet" class="logout-btn">ログアウト</a>
        </div>
    </div>
</nav>
