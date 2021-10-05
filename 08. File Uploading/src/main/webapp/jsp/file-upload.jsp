<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 05.10.2021
  Time: 8:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File uploading</title>
</head>
<body>

    <form method="post" action="/file-upload" enctype="multipart/form-data">
        <input type="file" name="uploadingFile"/>
        <input type="submit" value="Отправить!"/>
    </form>
<%--    ${status}--%>

</body>
</html>
