<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // コンテキストパス（プロジェクト名）を取得
    String path = request.getContextPath();
%>

<style>
/* ===== ナビゲーションバー全体のスタイル ===== */
.navbar {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); /* 他のJSPボタンと同じ紫系グラデーション */
    color: white;
    width: 100%;
    box-shadow: 0 4px 6px rgba(0,0,0,0.1); /* 柔らかい影 */
    font-family: 'Helvetica Neue', Arial, sans-serif; /* モダンなフォントスタック */
    margin-bottom: 30px; /* コンテンツとの間隔を少し広げる */
}

/* コンテナ：コンテンツ幅を制限し、中央に配置 */
.navbar .nav-container {
    display: flex;
    justify-content: space-between; /* 左右に要素を離して配置 */
    align-items: center;
    max-width: 1200px; /* コンテンツの最大幅を設定 */
    margin: 0 auto; /* 中央寄せ */
    padding: 0 20px; /* 左右の余白 */
}

/* リンクのグループ（左側と右側） */
.nav-links-left, .nav-links-right {
    display: flex;
    align-items: center;
}

/* ===== リンクのスタイル (共通) ===== */
.navbar a {
    color: #ecf0f1 !important;/* 明るいグレーっぽい白 */
    text-decoration: none; /* 下線を消す */
    padding: 15px 18px; /* タップしやすい大きめの余白 */
    display: inline-block;
    font-size: 15px;
    font-weight: 500;
    transition: all 0.3s ease; /* スムーズな変化 */
    border-radius: 4px; /* わずかな角丸 */
    background-color: transparent !important;
}

/* ホバー時のエフェクト（共通） */
.navbar a:hover {
    color: white;
    background-color: rgba(255, 255, 255, 0.1); /* 半透明の白い背景を重ねる */
}

/* ===== ログアウトボタン専用スタイル ===== */
.navbar .logout-btn {
    background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%); /* 赤色グラデーション */
    padding: 10px 20px; /* 他のリンクより少しコンパクトに強調 */
    margin-left: 10px; /* 左側の要素との間隔 */
    border-radius: 20px; /* より丸みを持たせてボタン感を出す */
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.navbar .logout-btn:hover {
    background: linear-gradient(135deg, #c0392b 0%, #a93226 100%); /* ホバー時は濃い赤色グラデーション */
    box-shadow: 0 4px 8px rgba(0,0,0,0.2); /* 浮き上がるような影 */
    transform: translateY(-1px); /* わずかに上に移動 */
}

/* レスポンシブ調整: スマホ表示など */
@media (max-width: 992px) { /* タブレット以下 */
    .navbar .nav-container {
        flex-wrap: wrap; /* 折り返しを許可 */
        padding: 10px;
    }
    .nav-links-left {
        flex-wrap: wrap;
        justify-content: center;
        width: 100%; /* 左側グループを全幅に */
        margin-bottom: 10px;
    }
    .nav-links-right {
        width: 100%; /* 右側グループも全幅に */
        justify-content: center;
    }
    .navbar a {
        padding: 10px 12px; /* 余白を少し小さく */
        font-size: 14px;
        margin: 2px; /* 周囲にわずかな隙間 */
    }
    .navbar .logout-btn {
        margin-left: 0; /* マージンをリセット */
        width: auto; /* 幅を自動調整 */
        padding: 10px 30px; /* 横長にして押しやすく */
    }
}
</style>

<nav class="navbar">
    <div class="nav-container">
        <div class="nav-links-left">
            <a href="<%=path%>/UserMainmenuServlet">メインメニュー</a>
            
            <a href="<%=path%>/Learningstarttransition">学習開始</a>
            <a href="<%=path%>/UserViewLearningRecordServlet">学習記録</a>
            <a href="<%=path%>/DisplayMissionsServlet">ミッション</a>
            <a href="<%=path%>/DisplayRankingServlet">ランキング</a>
            <a href="<%=path%>/DisplayStudyTimeGraph">学習時間グラフ</a>
            <a href="<%=path%>/AccountInfoDisplayServlet">利用者情報</a>
        </div>
        
        <div class="nav-links-right">
            <a href="<%=path%>/AdminLogoutServlet" class="logout-btn">ログアウト</a>
        </div>
    </div>
</nav>