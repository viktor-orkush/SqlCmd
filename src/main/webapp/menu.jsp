<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>SqlCmd</title>
    </head>
    <body>
    <ul>

        <c:forEach var="item" items = "${items}" >
            <li><a href="${item}">${item}</a> </li>
        </c:forEach>
</body>
</html>