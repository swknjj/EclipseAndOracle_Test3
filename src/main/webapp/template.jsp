<%-- 이 페이지는 실행과는 관련없고 템플릿 복사를 위한 파일입니다. --%>
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
            background-color: burlywood;
            padding: 20px;
        }
    </style>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="nav.jsp"%>

<div id="contents">
    <%-- 해당 페이지의 내용이 들어가는 부분 --%>
</div>
<%@include file="footer.jsp"%>
</body>
</html>