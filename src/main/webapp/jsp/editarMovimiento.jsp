<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Movimiento</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/uiverse@1.0.0/css/uiverse.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Oswald:wght@300;400;600&display=swap" rel="stylesheet">
<style>
html, body {
    height: 100%;
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
}

body {
    background-color: #f0f0f0;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.card {
    width: 300px;
    padding: 20px;
    text-align: center;
    background: #fff;
    border-radius: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    margin: auto;
    flex: 1;
}

.card > * {
    margin: 0;
}

.card__title {
    font-size: 24px;
    font-weight: 900;
    font-family: "Oswald", sans-serif;
    color: #333;
    margin-bottom: 10px;
}

.card__content {
    font-size: 14px;
    font-family: "Oswald", sans-serif;
    line-height: 20px;
    color: #333;
    margin-bottom: 20px;
}

.card__form {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.card__form input,
.card__form select {
    outline: 0;
    background: rgb(255, 255, 255);
    box-shadow: transparent 0px 0px 0px 1px inset;
    padding: 0.6em;
    border-radius: 14px;
    border: 1px solid #333;
    color: black;
}

.card__form label {
    text-align: left;
    font-weight: bold;
    font-family: "Oswald", sans-serif;
    color: #333;
    margin-bottom: -5px;
}

.card__form button,
.card__form input[type="submit"] {
    border: 0;
    background: #111;
    color: #fff;
    padding: 0.68em;
    border-radius: 14px;
    font-weight: bold;
    font-family: "Oswald", sans-serif;
}

.sign-up:hover {
    opacity: 0.8;
} 

a {
    display: block;
    margin-top: 10px;
    color: #333;
    text-decoration: none;
}

a:hover {
    text-decoration: underline;
}

.error-message {
    color: red;
    font-family: "Oswald", sans-serif;
    margin-bottom: 15px;
}

footer {
    text-align: center;
    padding: 10px;
    background-color: #f1f1f1;
    border-top: 1px solid #ddd;
}
</style>
</head>
<body>
<div class="card">
    <%-- Mostrar mensaje de error si existe --%>
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
    <form action="RegistrarMovimientosController" method="post">
        <input type="hidden" name="ruta" value="actualizarMovimiento">
		<input type="hidden" name="id" value="${movimiento.idMov}">
        <span class="card__title">Editar Movimiento</span>
        <p class="card__content">Modifica los datos del movimiento.</p>
        <div class="card__form">
            <input placeholder="Concepto" type="text" id="concepto" name="concepto" value="${movimiento.concepto}" required>
            <input type="date" id="fecha" name="fecha" value="${movimiento.fecha}" required>
            <label for="categoria">Categoría:</label>
            <select id="categoria" name="categoria" required>
                <%-- Asegúrate de que "categorias" esté disponible en el request --%>
                <c:forEach var="categoria" items="${categorias}">
                    <option value="${categoria.idCat}">${categoria.tipoCat}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Guardar Cambios">
            <a href="DashboardController?ruta=verDashboard">Volver al Dashboard</a>
        </div>
    </form>
</div>
<footer>
    <p>Grupo 3 (Canguros) &copy; 2024</p>
</footer>
</body>
</html>
