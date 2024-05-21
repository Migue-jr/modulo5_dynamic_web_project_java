<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bienvenido a Alke Wallet</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <h1>Bienvenido a Alke Wallet</h1>
    <% if (request.getParameter("error") != null) { %>
        <p id="error-message"><%= request.getParameter("error") %></p>
    <% } else { %>
        <p id="error-message"></p>
    <% } %>
    <form action="wallet" method="get">
        <label for="userId">Ingrese su User ID:</label>
        <input type="text" id="userId" name="userId" />
        <button type="submit" name="action" value="balance">Ver Saldo</button>
        <button type="submit" name="action" value="deposit">Depositar</button>
        <button type="submit" name="action" value="withdraw">Retirar</button>
        <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } %>
    </form>
</div>
<script src="/js/scripts.js"></script>
</body>
</html>




