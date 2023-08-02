<%@ page import="java.util.List" %>
<%@ page import="com.example.test3withcontroller.model.RankingDTO" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <style>
        * {
            padding: 0;
            margin: 0;
        }
        #contents {
            padding: 20px;
            background-color: burlywood;
            text-align: center;
        }
        table {
            margin-left: auto;
            margin-right: auto;
        }
        table, tr, th, td {
            border: 1px black solid;
            padding: 5px;
            text-align: center;
        }
    </style>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="nav.jsp"%>

<div id="contents">
    <h2>후보자등수</h2>
    <table>
        <tr>
            <th>후보번호</th>
            <th>성명</th>
            <th>총투표건수</th>
        </tr>
        <%
            List<RankingDTO> voteRanking = (List<RankingDTO>) request.getAttribute("voteRanking");
            for (RankingDTO rankingDTO : voteRanking) {
        %>
        <tr>
            <td><%=rankingDTO.getM_no()%></td>
            <td><%=rankingDTO.getM_name()%></td>
            <td><%=rankingDTO.getV_count()%></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<%@include file="footer.jsp"%>
</body>
</html>