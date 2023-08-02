<%@ page import="java.util.List" %>
<%@ page import="com.example.test3withcontroller.model.MemberDTO" %>
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
    <h2>후보조회</h2>
    <table>
        <tr>
            <th>후보번호</th>
            <th>성명</th>
            <th>소속정당</th>
            <th>학력</th>
            <th>주민번호</th>
            <th>지역구</th>
            <th>대표전화</th>
        </tr>
        <%
            List<MemberDTO> memberList = (List<MemberDTO>) request.getAttribute("memberList");
            for (MemberDTO memberDTO : memberList) {
        %>
        <tr>
            <td><%=memberDTO.getM_no()%></td>
            <td><%=memberDTO.getM_name()%></td>
            <td><%=memberDTO.getP_name()%></td>
            <td><%=memberDTO.getP_school()%></td>
            <td><%=memberDTO.getM_jumin()%></td>
            <td><%=memberDTO.getM_city()%></td>
            <td><%=memberDTO.getP_tel()%></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<%@include file="footer.jsp"%>
</body>
</html>