<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.RankingBeans" %>

<% 
    List<RankingBeans> rankingList = (List<RankingBeans>) request.getAttribute("rankingList");
    RankingBeans myRanking = (RankingBeans) request.getAttribute("myRanking");
    Integer myRank = (Integer) request.getAttribute("myRank");
    
    // エラーメッセージの取得と削除
    String errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) {
        session.removeAttribute("errorMessage");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ランキング - 学習管理システム</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/userMainmenu.css">
    <link rel="stylesheet" href="css/unified-theme.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">
    <style>
        /* ===== ランキング専用装飾 ===== */
        .ranking-container {
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
        }

        .ranking-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .ranking-header h2 {
            font-size: 28px;
            color: #333;
            margin-bottom: 10px;
        }

        .breadcrumb {
            color: #666;
            font-size: 14px;
            margin-bottom: 20px;
            text-align: left;
        }

        /* スクロール可能なランキングリスト */
        .ranking-list {
            max-height: 400px;
            overflow-y: auto;
            overflow-x: hidden;
            border: 2px solid #ddd;
            border-radius: 8px;
            background: #fff;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            /* スクロールバーを非表示にする */
            scrollbar-width: none; /* Firefox */
            -ms-overflow-style: none; /* IE/Edge */
            /* ホバー時のスライドで白背景が見えないように */
            position: relative;
            padding: 0;
        }

        /* Webkit系ブラウザ（Chrome, Safari）のスクロールバーを非表示 */
        .ranking-list::-webkit-scrollbar {
            display: none;
        }

        .rank-item {
            display: flex;
            align-items: center;
            padding: 15px 20px;
            border-bottom: 1px solid #eee;
            transition: all 0.3s ease;
            position: relative;
            margin-left: -10px;
            padding-left: 30px;
            cursor: pointer;
        }
        
        .rank-item a {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 1;
        }

        .rank-item:hover {
            background: #e3f2fd;
            box-shadow: 0 2px 8px rgba(0,0,0,0.15);
            margin-left: 0px;
        }
        
        .rank-1,
        .rank-2,
        .rank-3 {
            margin-left: -10px;
            padding-left: 30px;
        }
        
        .rank-1:hover {
            background: linear-gradient(135deg, #FFE55C 0%, #FFB84D 100%);
            box-shadow: 0 4px 12px rgba(255, 215, 0, 0.4);
            margin-left: 0px;
        }
        
        .rank-2:hover {
            background: linear-gradient(135deg, #D3D3D3 0%, #B8B8B8 100%);
            box-shadow: 0 4px 12px rgba(192, 192, 192, 0.4);
            margin-left: 0px;
        }
        
        .rank-3:hover {
            background: linear-gradient(135deg, #D4915D 0%, #9B5A28 100%);
            box-shadow: 0 4px 12px rgba(205, 127, 50, 0.4);
            margin-left: 0px;
        }

        .rank-item:last-child {
            border-bottom: none;
        }

        /* メダル装飾 */
        .rank-1 { 
            background: linear-gradient(135deg, #FFD700 0%, #FFA500 100%);
            font-weight: bold;
            color: #333;
        }

        .rank-2 { 
            background: linear-gradient(135deg, #C0C0C0 0%, #A9A9A9 100%);
            font-weight: bold;
            color: #333;
        }

        .rank-3 { 
            background: linear-gradient(135deg, #CD7F32 0%, #8B4513 100%);
            font-weight: bold;
            color: #fff;
        }

        .rank-no {
            min-width: 60px;
            font-size: 18px;
            font-weight: bold;
            color: #555;
        }

        .rank-1 .rank-no,
        .rank-2 .rank-no,
        .rank-3 .rank-no {
            color: inherit;
        }

        .rank-medal {
            margin-right: 10px;
            font-size: 24px;
        }

        .nickname {
            flex: 1;
            font-size: 16px;
            position: relative;
            z-index: 2;
            pointer-events: none;
        }

        .nickname a {
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
            transition: color 0.2s;
            pointer-events: auto;
        }

        .nickname a:hover {
            color: #0056b3;
            text-decoration: underline;
        }

        .rank-1 .nickname a,
        .rank-2 .nickname a {
            color: #333;
        }

        .rank-3 .nickname a {
            color: #fff;
        }

        .point {
            font-size: 16px;
            color: #666;
            font-weight: bold;
        }

        .rank-1 .point,
        .rank-2 .point,
        .rank-3 .point {
            color: inherit;
        }

        /* 自分の順位（固定表示） */
        .my-rank-section {
            margin-top: 30px;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.2);
            text-align: center;
        }

        .my-rank-section h3 {
            margin: 0 0 10px 0;
            font-size: 18px;
            color: #fff;
        }

        .my-rank-info {
            font-size: 20px;
            font-weight: bold;
            color: #333;
            background: #fff;
            padding: 15px;
            border-radius: 5px;
        }

        .my-rank-info .rank-value {
            font-size: 32px;
            color: #764ba2;
            margin: 0 5px;
            font-weight: bold;
        }

        /* 空データメッセージ */
        .no-data {
            text-align: center;
            padding: 40px;
            color: #999;
            font-size: 16px;
        }

        /* 戻るボタン */
        .back-btn-container {
            text-align: center;
            margin: 30px 0;
            padding: 20px 0;
            clear: both;
        }

        .back-btn {
            display: inline-block;
            padding: 12px 30px;
            background: #6c757d;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background 0.3s;
        }

        .back-btn:hover {
            background: #5a6268;
        }

        /* エラーメッセージ */
        .error-message {
            background: #f8d7da;
            color: #721c24;
            padding: 15px 20px;
            border-radius: 5px;
            border: 1px solid #f5c6cb;
            margin-bottom: 20px;
            text-align: center;
            font-weight: bold;
        }

        /* レスポンシブ対応 */
        @media (max-width: 768px) {
            .ranking-container {
                padding: 10px;
            }

            .ranking-header h2 {
                font-size: 22px;
            }

            .rank-item {
                padding: 12px 15px;
            }

            .rank-no {
                min-width: 50px;
                font-size: 16px;
            }

            .nickname {
                font-size: 14px;
            }

            .point {
                font-size: 14px;
            }

            .my-rank-info {
                font-size: 18px;
            }

            .my-rank-info .rank-value {
                font-size: 28px;
            }

            .header button {
                font-size: 12px;
                padding: 8px 10px;
            }

            .back-btn-container {
                margin: 20px 0;
                padding: 15px 0;
            }
        }
    </style>
</head>

<body>
<jsp:include page="common/header.jsp" />
<div class="container">

    <!-- ===== ヘッダー ===== -->
   	

    <div class="ranking-container">
        <!-- ===== ヘッダー ===== -->
        <div class="ranking-header">
            <h2>ランキング</h2>
            <p class="breadcrumb">メインメニュー　&gt;　ランキング</p>
        </div>

        <!-- ===== エラーメッセージ ===== -->
        <% if (errorMessage != null) { %>
        <div class="error-message">
            ⚠️ <%= errorMessage %>
        </div>
        <% } %>

        <!-- ===== ランキング一覧 ===== -->
        <div class="ranking-list">
            <%
            if (rankingList != null && !rankingList.isEmpty()) {
                int rank = 1;
                for (RankingBeans r : rankingList) {
                    String rankClass = "";
                    String medal = "";
                    
                    if (rank == 1) {
                        rankClass = "rank-1";
                        medal = "🥇";
                    } else if (rank == 2) {
                        rankClass = "rank-2";
                        medal = "🥈";
                    } else if (rank == 3) {
                        rankClass = "rank-3";
                        medal = "🥉";
                    }
            %>

            <div class="rank-item <%= rankClass %>">
                <% if (!medal.isEmpty()) { %>
                    <span class="rank-medal"><%= medal %></span>
                <% } %>
                <span class="rank-no"><%= rank %>位</span>
                <span class="nickname"><%= r.getNickName() %></span>
                <span class="point"><%= r.getPointTotal() %> pt</span>
                <a href="<%=request.getContextPath()%>/NavigateToLearningRecordPageServlet?studentNo=<%= r.getStudentNo() %>"></a>
            </div>

            <%
                    rank++;
                }
            } else {
            %>
                <div class="no-data">
                    <p>📭 ランキングデータがありません</p>
                </div>
            <%
            }
            %>
        </div>

        <!-- ===== 自分の順位 ===== -->
        <div class="my-rank-section">
            <h3>あなたの順位</h3>
            <div class="my-rank-info">
                <span class="rank-value"><%= myRank != null ? myRank : "-" %></span>位
                <% if (myRanking != null) { %>
                    / <%= myRanking.getNickName() %>
                    （<%= myRanking.getPointTotal() %> pt）
                <% } else { %>
                    / データなし
                <% } %>
            </div>
        </div>
    </div>

</div>

<!-- ===== 戻るボタン（container外） ===== -->
<div class="back-btn-container">
    <a href="<%=request.getContextPath()%>/UserMainmenuServlet" class="back-btn">← メインメニューに戻る</a>
</div>

</body>
</html>