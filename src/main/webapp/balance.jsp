<%--
  Created by IntelliJ IDEA.
  User: mgallegosm
  Date: 20-05-24
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cl.alkewallet.billeteradigital.model.User" %>
<%
  User user = (User) request.getAttribute("user");
  if (user == null) {
    throw new ServletException("User not found within scope");
  }
%>
<!DOCTYPE html>
<html>
<head>
  <title>Saldo Disponible</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
  <h1>Saldo Disponible</h1>
  <p>ID de Usuario: ${user.userId}</p>
  <p>Nombre: ${user.userName}</p>
  <p>Saldo: ${user.balance}</p>
  <form action="index.jsp">
    <button type="submit">Regresar</button>
  </form>
</div>
</body>
</html>

