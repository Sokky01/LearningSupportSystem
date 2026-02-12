<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.AdminAccountListBeans"%>
<%
    // 不要なセッション取得処理を削除しました（エラーの原因になるため）

    // Servletから送られた "accountList" を受け取る
    List<AdminAccountListBeans> accountList = (List<AdminAccountListBeans>) request.getAttribute("accountList");

    // 検索用のパラメータ取得
    String schoolGradeName = request.getParameter("schoolGradeName");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminAccountList.css">
</head>
<body>

<div class="wrapper">
    <div class="container">
        <div class="page-title">管理者アカウント一覧画面</div>
        <div class="breadcrumb">メインメニュー ＞ ＞アカウント管理 ＞＞ 管理者アカウント一覧画面</div>

        <div class="filter-area">
            <form action="AdminViewServlet" method="get">
                絞り込み：
                <select name="schoolGradeName">
                    <option value="" <%= (schoolGradeName == null || schoolGradeName.equals("")) ? "selected" : "" %>>すべて</option>
                    <option value="R4" <%= ("R4".equals(schoolGradeName) ? "selected" : "") %>>R4</option>
                    <option value="R3" <%= ("R3".equals(schoolGradeName) ? "selected" : "") %>>R3</option>
                    <option value="R2" <%= ("R2".equals(schoolGradeName) ? "selected" : "") %>>R2</option>
                    </select>
                <input type="submit" value="検索">
            </form>
        </div>

        <div class="table-area">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>名前</th>
                        <th>クラス</th>
                        <th>更新</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- ★修正2: nullチェックを追加（データがない場合のエラー防止） --%>
                    <% if (accountList != null) {
                        for (AdminAccountListBeans beans : accountList) { %>
                        <tr>
                            <td><%= beans.getManagerId() %></td>
                            <td><%= beans.getManagerName() %></td>
                            <td><%= beans.getClassId() %></td>
                            <td>
                                <form action="AdminUpdateServlet" method="get">
                                    <input type="hidden" name="managerId" value="<%= beans.getManagerId() %>">
                                    <input type="submit" value="更新" class="back-btn">
                                </form>
                            </td>
                        </tr>
                    <%  } 
                       } else { %>
                        <tr><td colspan="4">データがありません</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <div class="back-area">
            <button type="button" onclick="location.href='AdminMainmenuServlet'">戻る</button>
        </div>
    </div>
</div>

</body>
</html>