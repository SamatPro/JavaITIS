<%@ page import="java.util.List" %>
<%@ page import="ru.kpfu.itis.models.User" %><%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 29.09.2021
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<%--Так делать не стоит!!!--%>
<%--<table>--%>
<%--    <tr>--%>
<%--        <th>ID</th>--%>
<%--        <th>Имя</th>--%>
<%--        <th>Фамилия</th>--%>
<%--    </tr>--%>

<%--    <%--%>
<%--        List<User> users = (List<User>) request.getAttribute("users");--%>
<%--        for (User user: users) {--%>
<%--    %>--%>
<%--            <tr><td>--%>
<%--            <%=user.getId()%>--%>
<%--            </td><td>--%>
<%--            <%=user.getFirstName()%>--%>
<%--            </td><td>--%>
<%--            <%=user.getLastName()%>--%>
<%--            </td></tr>--%>
<%--    <%}%>--%>
<%--</table>--%>

<table>
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Название поста</th>
    </tr>
<c:forEach var="user" items="${users}">
    <tr>
        <td>
            <c:out value="${user.id}"/>
        </td>
        <td>
            <c:out value="${user.firstName}"/>
        </td>
        <td>
            <c:out value="${user.lastName}"/>
        </td>
        <td>
            <c:out value="${user.post.title}"/>
        </td>
    </tr>
</c:forEach>


</body>
</html>
