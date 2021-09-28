<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalDateTime" %><%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 21.09.2021
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <form action="/home" method="post" class="form">
        <p>Sign Up</p>
        <p>
            <label for="login">Login<input id="login" type="text" name="login" value="hhfhfh"></label>
            <label for="password">Password<input id="password" type="password" name="password"></label>
            <label for="repassword">Repassword<input id="repassword" type="password" name="repassword" value=""></label>
            <label for="email">Email<input id="email" type="email" name="email" value=""></label>
        <p><input type="submit"></p>
    </form>
<p>
    ${validation}

</p>

<%
    out.println(LocalDateTime.now());
%>

    <form action="/home" method="get" class="form">
        <p>Sign Up</p>
        <p>
            <label for="search">Login<input id="search" type="text" name="query" placeholder="Введите ваш запрос"></label>
        </p>
        <p><input type="submit" value="GET!"></p>
    </form>

</body>
</html>
