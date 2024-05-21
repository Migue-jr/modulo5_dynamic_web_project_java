<%--
  Created by IntelliJ IDEA.
  User: mgallegosm
  Date: 20-05-24
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Depositar</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script type="text/javascript">
        function validateForm() {
            var amount = document.getElementById("amount").value;
            if (amount <= 0) {
                alert("El monto debe ser un número positivo.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Depositar Fondos</h2>
    <form action="wallet" method="post">
        <input type="hidden" name="action" value="deposit" />
        <div class="form-group">
            <label for="userId">Cuenta de Origen:</label>
            <input type="text" id="userId" name="userId" value="<%= request.getParameter("userId") %>" readonly />
        </div>
        <div class="form-group">
            <label for="targetUserId">Cuenta de Destino (depósitos / transferencias):</label>
            <input type="text" id="targetUserId" name="targetUserId" />
        </div>
        <div class="form-group">
            <label for="amount">Monto:</label>
            <input type="text" id="amount" name="amount" />
        </div>
        <button type="submit">Depositar</button>
        <% if (request.getAttribute("error") != null) { %>
        <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } %>
    </form>
    <form action="index.jsp" method="get">
        <button type="submit">Regresar</button>
    </form>
</div>
</body>
</html>
