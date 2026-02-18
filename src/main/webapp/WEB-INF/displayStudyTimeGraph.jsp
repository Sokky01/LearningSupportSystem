<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>学習時間グラフ表示画面</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/userMainmenu.css">
<link rel="stylesheet" href="css/unified-theme.css">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/icon/icon.png">

<!-- Chart.js CDN -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!-- Data labels plugin for Chart.js -->
<script
	src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.2.0"></script>

<style>
/* ===== グラフ表示専用スタイル ===== */
.graph-container {
	max-width: 1200px;
	margin: 0 auto;
	padding: 20px;
}

.graph-header {
	text-align: center;
	margin-bottom: 30px;
}

.graph-header h2 {
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

/* グラフコンテンツ */
.graph-content {
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
	padding: 30px;
}

/* 年月表示 */
.subtitle {
	font-size: 18px;
	margin: 0 0 20px 0;
	text-align: center;
	color: #666;
	font-weight: bold;
}

/* メニュー */
.menu {
	display: flex;
	justify-content: center;
	align-items: center;
	gap: 20px;
	margin-bottom: 30px;
	flex-wrap: wrap;
	background: #f8f9fa;
	padding: 15px;
	border-radius: 8px;
}

.menu label {
	font-weight: bold;
	color: #333;
}

.menu select {
	padding: 8px 12px;
	border: 2px solid #ddd;
	border-radius: 5px;
	font-size: 14px;
	background: #fff;
}

.menu select:focus {
	outline: none;
	border-color: #667eea;
}

#refreshBtn {
	padding: 8px 20px;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: #fff;
	border: none;
	border-radius: 8px;
	font-weight: bold;
	cursor: pointer;
	transition: all 0.3s;
}

#refreshBtn:hover {
	transform: translateY(-2px);
	box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

/* Canvas（グラフ） */
canvas {
	width: 100% !important;
	height: 420px !important;
	margin: 20px 0;
}

/* 補足テキスト */
.note {
	font-size: 0.9rem;
	color: #666;
	margin-top: 8px;
	text-align: center;
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
	border: none;
	cursor: pointer;
	font-size: 16px;
}

.back-btn:hover {
	background: #5a6268;
}

/* レスポンシブ対応 */
@media ( max-width : 768px) {
	.graph-container {
		padding: 10px;
	}
	.graph-header h2 {
		font-size: 22px;
	}
	.graph-content {
		padding: 15px;
	}
	.menu {
		flex-direction: column;
		gap: 10px;
	}
	canvas {
		height: 300px !important;
	}
}
</style>
</head>

<body>
	<jsp:include page="common/header.jsp" />
	<div class="container">

		<div class="graph-container">
			<!-- ===== ヘッダー ===== -->
			<div class="graph-header">
				<h2>学習時間グラフ</h2>
				<p class="breadcrumb">メインメニュー　&gt;　学習時間グラフ</p>
			</div>

			<!-- ===== グラフコンテンツ ===== -->
			<div class="graph-content">
				<div id="chartYear" class="subtitle">&nbsp;</div>

				<div class="menu">
					<label for="chartSelect">グラフ選択:</label> <select id="chartSelect">
						<option value="monthlyDaily">1. 日別学習時間合計</option>
						<option value="bySubject">2. 科目別の学習時間合計</option>
					</select> <label for="rangeSelect">表示期間:</label> <select id="rangeSelect">
						<option value="7">過去7日</option>
						<option value="30" selected>過去30日</option>
					</select>

					<button id="refreshBtn" type="button">更新</button>
				</div>

				<canvas id="studyChart" aria-label="学習時間のグラフ" role="img"></canvas>
			</div>
		</div>

	</div>

	<!-- ===== 戻るボタン ===== -->
	<div class="back-btn-container">
    <a href="<%=request.getContextPath()%>/UserMainmenuServlet" class="back-btn">← メインメニューに戻る</a>
</div>

	<script>
		// ===== JavaScript処理（変更なし） =====
		var learningHistory = null;
		<%String lhJson = (String) request.getAttribute("learningHistoryJson");%>
		<%if (lhJson != null) {%>
		learningHistory =
<%=lhJson%>
	;
		<%} else {%>
		learningHistory = [ {
			"studyDate" : "2026-01-01",
			"minutes" : 30,
			"subject" : "数学"
		}, {
			"studyDate" : "2026-01-02",
			"minutes" : 45,
			"subject" : "英語"
		}, {
			"studyDate" : "2026-01-03",
			"minutes" : 20,
			"subject" : "数学"
		}, {
			"studyDate" : "2026-01-04",
			"minutes" : 60,
			"subject" : "理科"
		}, {
			"studyDate" : "2026-01-05",
			"minutes" : 10,
			"subject" : "英語"
		}, {
			"studyDate" : "2026-01-06",
			"minutes" : 0,
			"subject" : "社会"
		}, {
			"studyDate" : "2026-01-07",
			"minutes" : 50,
			"subject" : "数学"
		} ];
		<%}%>

		if (typeof learningHistory === 'string') {
			try {
				learningHistory = JSON.parse(learningHistory);
			} catch (e) {
				learningHistory = [];
			}
		}

		function formatDateToYMD(dt) {
			var y = dt.getFullYear();
			var m = (dt.getMonth() + 1).toString().padStart(2, '0');
			var d = dt.getDate().toString().padStart(2, '0');
			return y + '-' + m + '-' + d;
		}

		function extractDate(item) {
			var candidates = [ item.studyDate, item.date, item.study_date,
					item.YearMonthDate, item.yearMonthDate,
					item.year_month_date ];
			for (var i = 0; i < candidates.length; i++) {
				var v = candidates[i];
				if (v === undefined || v === null)
					continue;
				if (typeof v === 'number')
					return new Date(v);
				if (typeof v === 'string') {
					var isoMatch = v.match(/^\d{4}-\d{2}-\d{2}/);
					if (isoMatch)
						return new Date(isoMatch[0] + 'T00:00:00');
					if (/^\d+$/.test(v))
						return new Date(Number(v));
					var msMatch = v.match(/\d{10,}/);
					if (msMatch)
						return new Date(Number(msMatch[0]));
					var dt = new Date(v);
					if (!isNaN(dt.getTime()))
						return dt;
				}
			}
			return null;
		}

		function extractMinutes(item) {
			var v = item.minutes || item.min || item.learningTime
					|| item.LearningTime || item.learning_time
					|| item.learning_time_minutes;
			var n = Number(v);
			return isNaN(n) ? 0 : n;
		}

		function extractSubject(item) {
			if (item.subject)
				return String(item.subject);
			if (item.subjectName)
				return String(item.subjectName);
			if (item.kamoku)
				return String(item.kamoku);
			if (item.subjectId !== undefined && item.subjectId !== null)
				return '科目ID:' + item.subjectId;
			if (item.SubjectId !== undefined && item.SubjectId !== null)
				return '科目ID:' + item.SubjectId;
			return '不明';
		}

		function normalizeList(list) {
			if (!Array.isArray(list))
				return [];
			var out = [];
			list
					.forEach(function(item) {
						var dt = extractDate(item);
						var dateStr = dt ? formatDateToYMD(dt)
								: (item.studyDate || item.date
										|| item.yearMonthDate || '').toString()
										.slice(0, 10);
						var mins = extractMinutes(item);
						var subj = extractSubject(item);
						out.push({
							date : dateStr,
							minutes : mins,
							subject : subj
						});
					});
			return out;
		}

		function calcMonthlyDaily(list) {
			var now = new Date();
			var year = now.getFullYear();
			var month = now.getMonth();
			var daysInMonth = new Date(year, month + 1, 0).getDate();
			var result = [];
			for (var d = 1; d <= daysInMonth; d++) {
				var dd = new Date(year, month, d);
				var yyyy = dd.getFullYear();
				var mm = (dd.getMonth() + 1).toString().padStart(2, '0');
				var day = dd.getDate().toString().padStart(2, '0');
				var key = yyyy + '-' + mm + '-' + day;
				result.push({
					date : key,
					minutes : 0
				});
			}
			list.forEach(function(item) {
				var sd = item.date || item.studyDate || '';
				var mins = Number(item.minutes || 0) || 0;
				if (!sd)
					return;
				var d = new Date(sd + 'T00:00:00');
				if (d.getFullYear() === year && d.getMonth() === month) {
					var key = sd;
					var idx = result.findIndex(function(r) {
						return r.date === key;
					});
					if (idx >= 0)
						result[idx].minutes += mins;
				}
			});
			return result;
		}

		function calcBySubject(list) {
			var map = {};
			list.forEach(function(item) {
				var subj = item.subject || item.subjectName || item.kamoku
						|| '不明';
				var mins = Number(item.minutes || 0) || 0;
				if (!map[subj])
					map[subj] = 0;
				map[subj] += mins;
			});
			var labels = Object.keys(map);
			var values = labels.map(function(l) {
				return map[l];
			});
			return {
				labels : labels,
				values : values
			};
		}

		var ctx = document.getElementById('studyChart').getContext('2d');
		var studyChart = null;

		function renderMonthlyDaily(data) {
			var labels = data.map(function(d) {
				return d.date;
			});
			var values = data.map(function(d) {
				return d.minutes;
			});

			function formatLabelDayOnly(ymd) {
				if (!ymd)
					return '';
				var parts = ymd.split('-');
				if (parts.length >= 3) {
					var d = parseInt(parts[2], 10);
					return d + '日';
				}
				var dt = new Date(ymd + 'T00:00:00');
				if (!isNaN(dt.getTime()))
					return dt.getDate() + '日';
				return ymd;
			}

			var displayLabels = labels.map(formatLabelDayOnly);

			var displayYearMonth = null;
			for (var i = 0; i < labels.length; i++) {
				if (labels[i]) {
					var parts = labels[i].split('-');
					if (parts.length >= 2) {
						var y = parts[0];
						var m = parseInt(parts[1], 10);
						if (y && y.match(/^\d{4}$/) && !isNaN(m)) {
							displayYearMonth = y + '年' + m + '月';
							break;
						}
					}
				}
			}
			if (!displayYearMonth) {
				var now = new Date();
				displayYearMonth = now.getFullYear() + '年'
						+ (now.getMonth() + 1) + '月';
			}
			var yearElem = document.getElementById('chartYear');
			if (yearElem)
				yearElem.textContent = displayYearMonth;

			var maxVal = 0;
			for (var i = 0; i < values.length; i++) {
				if (values[i] > maxVal)
					maxVal = values[i];
			}

			function niceStep(max, ticks) {
				ticks = ticks || 5;
				if (max <= 0)
					return 1;
				var raw = Math.ceil(max / ticks);
				var pow10 = Math.pow(10, Math.floor(Math.log10(raw)));
				var d = raw / pow10;
				var nice;
				if (d <= 1)
					nice = 1;
				else if (d <= 2)
					nice = 2;
				else if (d <= 5)
					nice = 5;
				else
					nice = 10;
				return nice * pow10;
			}

			var step = niceStep(maxVal, 5);
			var suggestedMax = Math.ceil(maxVal / step) * step;
			if (suggestedMax === 0)
				suggestedMax = step;

			var chartData = {
				labels : displayLabels,
				datasets : [ {
					label : '学習時間 (分)',
					data : values,
					backgroundColor : 'rgba(54, 162, 235, 0.6)',
					borderColor : 'rgba(54, 162, 235, 1)',
					borderWidth : 1
				} ]
			};
			var options = {
				responsive : true,
				maintainAspectRatio : false,
				layout : {
					padding : {
						bottom : 24
					}
				},
				scales : {
					x : {
						title : {
							display : true,
							text : '年月日',
							font : {
								size : 14
							},
							padding : {
								top : 8
							}
						},
						ticks : {
							autoSkip : false
						}
					},
					y : {
						beginAtZero : true,
						suggestedMax : suggestedMax,
						ticks : {
							stepSize : step,
							callback : function(value) {
								return value + '分';
							}
						},
						title : {
							display : true,
							text : '分'
						}
					}
				},
				plugins : {
					legend : {
						display : false
					},
					datalabels : {
						display : false
					}
				}
			};
			if (studyChart) {
				studyChart.destroy();
			}
			studyChart = new Chart(ctx, {
				type : 'bar',
				data : chartData,
				options : options
			});
		}

		function renderBySubject(obj) {
			if (typeof Chart !== 'undefined'
					&& typeof Chart.register === 'function'
					&& window.ChartDataLabels) {
				try {
					Chart.register(ChartDataLabels);
				} catch (e) {
				}
			}

			var chartData = {
				labels : obj.labels,
				datasets : [ {
					label : '学習時間 (分)',
					data : obj.values,
					backgroundColor : obj.labels
							.map(function(_, i) {
								var colors = [ '#4dc9f6', '#f67019',
										'#f53794', '#537bc4', '#acc236',
										'#166a8f', '#00a950' ];
								return colors[i % colors.length];
							}),
					borderWidth : 1
				} ]
			};
			var options = {
				responsive : true,
				maintainAspectRatio : false,
				plugins : {
					legend : {
						position : 'right'
					},
					datalabels : {
						color : '#ffffff',
						formatter : function(value, ctx) {
							var label = ctx.chart.data.labels[ctx.dataIndex];
							return label;
						},
						font : {
							weight : 'bold',
							size : 12
						},
						anchor : 'center',
						align : 'center',
						clamp : true
					}
				}
			};
			if (studyChart) {
				studyChart.destroy();
			}
			studyChart = new Chart(ctx, {
				type : 'pie',
				data : chartData,
				options : options,
				plugins : [ window.ChartDataLabels ]
			});
		}

		function updateChart() {
			var sel = document.getElementById('chartSelect');
			var range = parseInt(document.getElementById('rangeSelect').value,
					10);
			if (sel.value === 'monthlyDaily') {
				var md = calcMonthlyDaily(learningHistory || []);
				if (range > 0) {
					var filtered = md.slice(0, Math.min(range, md.length));
					renderMonthlyDaily(filtered);
				} else {
					renderMonthlyDaily(md);
				}
			} else if (sel.value === 'bySubject') {
				var bySub = calcBySubject(learningHistory || []);
				renderBySubject(bySub);
			}
		}

		document.addEventListener('DOMContentLoaded', function() {
			updateChart();
			document.getElementById('chartSelect').addEventListener('change',
					updateChart);
			document.getElementById('rangeSelect').addEventListener('change',
					updateChart);
			document.getElementById('refreshBtn').addEventListener('click',
					updateChart);
		});
	</script>

</body>
</html>
