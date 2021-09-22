<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 22.09.2021
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>

    <form action="/signUp" method="post" class="form">
        <p>Sign Up</p>
        <p>
            <label for="login">Login<input id="login" type="text" name="login"></label><br>
            <label for="password">Password<input id="password" type="password" name="password"></label><br>
            <label for="repassword">Repassword<input id="repassword" type="password" name="repassword"></label><br>
            <label for="lastName">Last name<input id="lastName" type="text" name="lastName"></label><br>
            <label for="firstName">First name<input id="firstName" type="text" name="firstName"></label><br>

<%--        <p><input type="submit"></p>--%>
        <p><button type="submit">Зарегистрироваться!</button></p><br>
    </form>

</body>
</html>
