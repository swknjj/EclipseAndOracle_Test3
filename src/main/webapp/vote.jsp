<%@ page import="com.example.test3.model.VoteDAO" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-08-04
  Time: 오전 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
    /* controller에서 처리 결과만 가져와서 성공 실패만 보여줌. */
        int result = (int) request.getAttribute("result");
        if (result > 0) {
    %>
<script>
    alert("투표하기 정보가 정상적으로 등록되었습니다!");
    location.href = "index.jsp";
</script>
    <%
        } else {
    %>
<script>
    alert("투표하기 실패!");
</script>
    <%
        }
    %>
</html>
