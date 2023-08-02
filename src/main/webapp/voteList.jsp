<%@ page import="java.util.List" %>
<%@ page import="com.example.test3withcontroller.model.VoteDTO" %>
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
    <h2>투표검수조회</h2>
    <table>
        <tr>
            <th>성명</th>
            <th>생년월일</th>
            <th>나이</th>
            <th>성별</th>
            <th>후보번호</th>
            <th>투표시간</th>
            <th>유권자확인</th>
        </tr>
        <%
            List<VoteDTO> voteList = (List<VoteDTO>) request.getAttribute("voteList");
            for (VoteDTO voteDTO : voteList) {
        %>
        <tr>
            <td><%=voteDTO.getV_name()%></td>
            <td><%=voteDTO.getV_birth()%></td>
            <td><%=voteDTO.getV_age()%></td>
            <td><%=voteDTO.getV_gender()%></td>
            <td><%=voteDTO.getM_no()%></td>
            <td><%=voteDTO.getV_time()%></td>
            <td><%=voteDTO.getV_confirm()%></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<%@include file="footer.jsp"%>
</body>
</html>