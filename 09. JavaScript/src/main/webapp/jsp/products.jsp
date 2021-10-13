<%--
  Created by IntelliJ IDEA.
  User: Samat
  Date: 12.10.2021
  Time: 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>

</head>
<body>

    <input id="title" type="text" name="title" placeholder="Название продукта"/>
    <input id="cost" type="number" name="cost" placeholder="Стоимость продукта"/>
    <input id="createdAt" type="datetime-local" name="createdAt" placeholder="Дата изготовления"/>
    <button id="sendProduct" onclick="sendProduct()">Отправить</button>

    <script>
        function sendProduct(){
            let title = document.getElementById('title').value
            let cost = document.getElementById('cost').value
            let createdAt = document.getElementById('createdAt').value
            alert(title);
            var product = {
                title: title,
                cost: cost,
                createdAt: createdAt
            }
            $.ajax({
                url: '/products',           /* Куда пойдет запрос */
                method: 'post',             /* Метод передачи (post или get) */
                dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
                data: {product: JSON.stringify(product)}, /* Параметры передаваемые в запросе. */
                success: function(data){   /* функция которая будет выполнена после успешного запроса.  */
                    alert(data);            /* В переменной data содержится ответ от /products. */
                }
            })
        }
    </script>

<%--<script>--%>
<%--    function sendProduct() {--%>
<%--        var xhr = new XMLHttpRequest();--%>

<%--        var body = 'title=' + document.getElementById('title') +--%>
<%--            '&cost=' + document.getElementById('cost') +--%>
<%--            '&createdAt' + document.getElementById('createdAt');--%>

<%--        xhr.open("POST", '/products', true);--%>
<%--        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');--%>

<%--        xhr.send(body);--%>
<%--    }--%>
<%--</script>--%>

</body>
</html>
