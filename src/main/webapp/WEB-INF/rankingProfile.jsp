<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.RankingProfileBeans" %>

<% 
    RankingProfileBeans profile = (RankingProfileBeans) request.getAttribute("profile");
    List<Map<String, Object>> recentHistory = (List<Map<String, Object>>) request.getAttribute("recentHistory");
    List<Map<String, Object>> weeklyData = (List<Map<String, Object>>) request.getAttribute("weeklyData");
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学習記録ページ - <%= profile != null ? profile.getNickName() : "ユーザー" %></title>
    <link rel="stylesheet"href="<%=request.getContextPath()%>/css/userMainmenu.css">
	<link rel="stylesheet" href="css/unified-theme.css">
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">
	
    <style>
        /* ===== プロフィールページ専用スタイル ===== */
        .profile-container {
            max-width: 1000px;
            margin: 0 auto;
            padding: 20px;
        }

        .profile-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .profile-header h2 {
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

        /* ユーザー基本情報カード */
        .user-info-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
            margin-bottom: 30px;
            color: #fff;
        }

        .user-info-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .user-name {
            font-size: 32px;
            font-weight: bold;
        }

        .user-stats {
            display: flex;
            gap: 30px;
            flex-wrap: wrap;
        }

        .stat-item {
            background: rgba(255,255,255,0.2);
            padding: 15px 25px;
            border-radius: 8px;
            text-align: center;
        }

        .stat-label {
            font-size: 14px;
            opacity: 0.9;
            margin-bottom: 5px;
        }

        .stat-value {
            font-size: 24px;
            font-weight: bold;
        }

        /* セクションタイトル */
        .section-title {
            font-size: 22px;
            color: #333;
            margin: 30px 0 15px 0;
            padding-bottom: 10px;
            border-bottom: 3px solid #667eea;
        }

        /* グラフコンテナ */
        .chart-container {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }

        .chart-wrapper {
            position: relative;
            height: 300px;
        }

        /* 学習記録テーブル */
        .history-table {
            width: 100%;
            background: #fff;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .history-table table {
            width: 100%;
            border-collapse: collapse;
        }

        .history-table th {
            background: #667eea;
            color: #fff;
            padding: 15px;
            text-align: left;
            font-weight: bold;
        }

        .history-table td {
            padding: 12px 15px;
            border-bottom: 1px solid #eee;
        }

        .history-table tr:hover {
            background: #f5f7ff;
        }

        .history-table tr:last-child td {
            border-bottom: none;
        }

        .no-data {
            text-align: center;
            padding: 40px;
            color: #999;
        }

        /* 戻るボタン */
        .back-btn-container {
            text-align: center;
            margin: 30px 0;
            padding: 20px 0;
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

        /* レスポンシブ対応 */
        @media (max-width: 768px) {
            .profile-container {
                padding: 10px;
            }

            .user-info-header {
                flex-direction: column;
                gap: 15px;
            }

            .user-stats {
                justify-content: center;
            }

            .stat-item {
                padding: 10px 15px;
            }

            .history-table {
                font-size: 14px;
            }

            .history-table th,
            .history-table td {
                padding: 10px;
            }
        }
    </style>
    
    <!-- Chart.js CDN -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
<jsp:include page="common/header.jsp" />
<div class="container">



    <div class="profile-container">
        <!-- ===== ページヘッダー ===== -->
        <div class="profile-header">
            <h2>学習記録ページ</h2>
            <p class="breadcrumb">メインメニュー　 &gt;　ランキング　&gt;　学習記録ページ</p>
        </div>

        <% if (profile != null) { %>
        
        <!-- ===== ユーザー基本情報 ===== -->
        <div class="user-info-card">
            <div class="user-info-header">
                <div class="user-name"><%= profile.getNickName() %></div>
            </div>
            <div class="user-stats">
                <div class="stat-item">
                    <div class="stat-label">グレード</div>
                    <div class="stat-value"><%= profile.getGradeName() != null ? profile.getGradeName() : "未設定" %></div>
                </div>
                <div class="stat-item">
                    <div class="stat-label">学年</div>
                    <div class="stat-value"><%= profile.getMaxGrade() %>年</div>
                </div>
                <div class="stat-item">
                    <div class="stat-label">総ポイント</div>
                    <div class="stat-value"><%= profile.getPointTotal() %> pt</div>
                </div>
                <div class="stat-item">
                    <div class="stat-label">学習記録（1週間）</div>
                    <div class="stat-value"><%= recentHistory != null ? recentHistory.size() : 0 %> 件</div>
                </div>
            </div>
        </div>

        <!-- ===== 学習時間グラフ（過去7日間） ===== -->
        <h3 class="section-title">📊 過去7日間の学習時間</h3>
        <div class="chart-container">
            <div class="chart-wrapper">
                <canvas id="learningChart"></canvas>
            </div>
        </div>

        <!-- ===== 最近の学習記録 ===== -->
        <h3 class="section-title">📚 過去1週間の学習記録</h3>
        <div class="history-table">
            <% if (recentHistory != null && !recentHistory.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>日付</th>
                        <th>科目</th>
                        <th>学習時間</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Map<String, Object> record : recentHistory) { %>
                    <tr>
                        <td><%= sdf.format(record.get("date")) %></td>
                        <td><%= record.get("subject") %></td>
                        <td><%= ((Number)record.get("learningTime")).intValue() / 60 %> 分</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% } else { %>
            <div class="no-data">
                <p>📭 過去1週間の学習記録がありません</p>
            </div>
            <% } %>
        </div>

        <% } else { %>
        <div class="no-data">
            <p>ユーザー情報が見つかりませんでした</p>
        </div>
        <% } %>
    </div>

</div>

<!-- ===== 戻るボタン ===== -->
<div class="back-btn-container">
    <a href="<%=request.getContextPath()%>/DisplayRankingServlet" class="back-btn">← ランキングに戻る</a>
</div>

<script>
// 今日を含む過去7日間の日付ラベルを生成
const today = new Date();
const labels = [];
for (let i = 6; i >= 0; i--) {
    const date = new Date(today);
    date.setDate(date.getDate() - i);
    const month = date.getMonth() + 1;
    const day = date.getDate();
    labels.push(month + '/' + day);
}

// グラフデータの準備（7日間すべての日付を含む）
const weeklyDataMap = {};
<%
if (weeklyData != null) {
    for (Map<String, Object> data : weeklyData) {
%>
    // ★修正：秒 → 分
    weeklyDataMap['<%= sdf.format(data.get("date")) %>'] =
        Math.floor(<%= data.get("totalTime") %> / 60);
<%
    }
}
%>

// 7日間のデータを作成（データがない日は0）
const chartData = [];
for (let i = 6; i >= 0; i--) {
    const date = new Date(today);
    date.setDate(date.getDate() - i);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const dateStr = year + '/' + month + '/' + day;
    
    chartData.push(weeklyDataMap[dateStr] || 0);
}

// Y軸の最大値を動的に計算
const maxValue = Math.max(...chartData);
let yAxisMax;
let stepSize;

if (maxValue === 0) {
    // データがない場合
    yAxisMax = 60;
    stepSize = 30;
} else if (maxValue <= 60) {
    // 1時間以内
    yAxisMax = 60;
    stepSize = 30;
} else if (maxValue <= 120) {
    // 2時間以内
    yAxisMax = 120;
    stepSize = 60;
} else if (maxValue <= 180) {
    // 3時間以内
    yAxisMax = 180;
    stepSize = 60;
} else if (maxValue <= 240) {
    // 4時間以内
    yAxisMax = 240;
    stepSize = 60;
} else if (maxValue <= 300) {
    // 5時間以内
    yAxisMax = 300;
    stepSize = 60;
} else {
    // 5時間超: 最大値の1.2倍に設定（余白20%）
    yAxisMax = Math.ceil(maxValue * 1.2 / 60) * 60;  // 60の倍数に切り上げ
    stepSize = 60;
}

// Chart.jsでグラフを描画
const ctx = document.getElementById('learningChart').getContext('2d');
const chart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: labels,
        datasets: [{
            label: '学習時間（分）',
            data: chartData,
            backgroundColor: 'rgba(102, 126, 234, 0.7)',
            borderColor: 'rgba(102, 126, 234, 1)',
            borderWidth: 2,
            borderRadius: 5
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                display: true,
                position: 'top',
                onClick: null  // クリックで非表示にならないようにする
            },
            tooltip: {
                backgroundColor: 'rgba(0, 0, 0, 0.8)',
                padding: 12,
                titleFont: {
                    size: 14
                },
                bodyFont: {
                    size: 13
                },
                callbacks: {
                    label: function(context) {
                        return '学習時間: ' + context.parsed.y + '分';
                    }
                }
            }
        },
        scales: {
            y: {
                beginAtZero: true,
                max: yAxisMax,  // 動的に計算したY軸の最大値
                ticks: {
                    callback: function(value) {
                        return value + '分';
                    },
                    stepSize: stepSize  // 動的に計算した目盛り間隔
                },
                grid: {
                    color: 'rgba(0, 0, 0, 0.05)'
                }
            },
            x: {
                grid: {
                    display: false
                }
            }
        },
        layout: {
            padding: {
                top: 10  // グラフ上部に余白を追加
            }
        }
    }
});
</script>

</body>
</html>