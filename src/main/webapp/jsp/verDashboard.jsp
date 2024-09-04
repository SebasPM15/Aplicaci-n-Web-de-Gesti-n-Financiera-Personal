<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="modelo.entidades.Cuenta, modelo.entidades.Categoria, modelo.entidades.Movimiento, modelo.entidades.Ingreso, modelo.entidades.Egreso, modelo.entidades.Usuario, modelo.entidades.Transferencia" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@300;400;600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/uiverse@1.0.0/css/uiverse.min.css">
    <style>
        body {
            font-family: "Roboto", sans-serif;
            font-size: 16px;
            background-image: url('https://img.freepik.com/foto-gratis/cerca-colorido-abstracto-ahumado_53876-148112.jpg');
            background-size: cover;
            padding: 20px;
            margin: 0;
        }

        h1, h2, h3 {
            font-family: "Oswald", sans-serif;
            color: #333;
        }

        .btn {
            text-decoration: none;
            padding: 10px 20px;
            margin: 10px;
            display: inline-block;
            border-radius: 5px;
            color: #fff;
            cursor: pointer;
        }

        .btn-primary {
            background-color: #007bff;
        }

        .btn-secondary {
            background-color: #6c757d;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            font-family: "Roboto", sans-serif;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .navbar {
            overflow: hidden;
            background-color: #f8f9fa; /* Blanco */
            border-bottom: 2px solid #ddd;
            padding: 10px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .navbar img {
            height: 60px; /* Ajusta el tamaño según sea necesario */
        }

        .navbar .title {
            font-family: "Oswald", sans-serif;
            font-weight: 600;
            font-size: 24px;
            color: #007bff; /* Color del título */
            margin-left: 20px;
        }

        .navbar a {
            display: inline-block;
            color: #007bff;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
            font-family: "Oswald", sans-serif;
            font-weight: 600;
            background-color: #e9ecef; /* Fondo de los enlaces */
            border-radius: 5px;
            margin: 0 5px;
        }

        .navbar a:hover {
            background-color: #ddd;
            color: #000;
            cursor: pointer;
        }

        .filter-form {
            margin-bottom: 20px;
        }

        .filter-form label {
            font-family: "Roboto", sans-serif;
            font-size: 16px;
            margin-right: 10px;
        }

        .filter-form select, .filter-form input {
            padding: 8px;
            margin-right: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
        }

        .filter-form button {
            padding: 8px 16px;
            border-radius: 5px;
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
        }

        .filter-form button:hover {
            background-color: #0056b3;
        }

        .filter-info {
            margin-bottom: 20px;
            font-family: "Roboto", sans-serif;
            font-size: 16px;
            background-color: #f8f9fa;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <img src="https://cem.epn.edu.ec/imagenes/logos_institucionales/big_jpg/EPN_logo_big.jpg" alt="Logo EPN">
        <span class="title">Dashboard</span>
        <div>
            <span class="welcome">Bienvenido, <%= session.getAttribute("nombreUsuario") %>!</span>
            <a href="RegistrarMovimientosController?ruta=crearIngreso" class="btn btn-primary">Registrar Ingreso</a>
            <a href="RegistrarMovimientosController?ruta=crearEgreso" class="btn btn-primary">Registrar Egreso</a>
            <a href="RegistrarMovimientosController?ruta=crearTransferencia" class="btn btn-primary">Registrar Transferencia</a>
            <a href="AutenticarController?ruta=cerrarSesion" class="btn btn-secondary">Cerrar sesión</a>
        </div>
    </div>

    <h2>Cuentas</h2>
    <table>
        <thead>
            <tr>
                <th>Cuenta</th>
                <th>Saldo</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
            for (Cuenta cuenta : cuentas) {
            %>
            <tr>
                <td><%= cuenta.getTipo() %></td>
                <td><%= cuenta.getSaldo() %></td>
            </tr>
            <%
            }
            %>
        </tbody>
    </table>

    <h2>Categorías</h2>
    <h3>Ingresos</h3>
    <table>
        <thead>
            <tr>
                <th>Categoría</th>
                <th>Saldo</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
            Map<Integer, Double> saldosPorCategoria = (Map<Integer, Double>) request.getAttribute("saldosPorCategoria");
            for (Categoria categoria : categorias) {
                Double saldo = saldosPorCategoria.get(categoria.getIdCat());
                if (saldo != null && saldo > 0) {
            %>
            <tr>
                <td><%= categoria.getTipoCat() %></td>
                <td><%= saldo %></td>
            </tr>
            <%
                }
            }
            %>
        </tbody>
    </table>

    <h3>Gastos</h3>
    <table>
        <thead>
            <tr>
                <th>Categoría</th>
                <th>Saldo</th>
            </tr>
        </thead>
        <tbody>
            <%
            for (Categoria categoria : categorias) {
                Double saldo = saldosPorCategoria.get(categoria.getIdCat());
                if (saldo != null && saldo < 0) {
            %>
            <tr>
                <td><%= categoria.getTipoCat() %></td>
                <td><%= saldo %></td>
            </tr>
            <%
                }
            }
            %>
        </tbody>
    </table>

    <h2>Movimientos</h2>

    <form class="filter-form" action="DashboardController" method="get">
        <label for="fecha">Filtrar por fecha:</label>
        <input type="date" id="fecha" name="fecha">

        <label for="cuenta">Filtrar por cuenta:</label>
        <select id="cuenta" name="cuenta">
            <option value="">Todas</option>
            <%
            for (Cuenta cuenta : cuentas) {
            %>
            <option value="<%= cuenta.getIdCuenta() %>"><%= cuenta.getTipo() %></option>
            <%
            }
            %>
        </select>

        <label for="categoria">Filtrar por categoría:</label>
        <select id="categoria" name="categoria">
            <option value="">Todas</option>
            <%
            for (Categoria categoria : categorias) {
            %>
            <option value="<%= categoria.getIdCat() %>"><%= categoria.getTipoCat() %></option>
            <%
            }
            %>
        </select>

        <button type="submit">Filtrar</button>
        <a href="DashboardController" class="btn btn-secondary">Mostrar Todos</a>
    </form>

    <div class="filter-info">
        <%
        String cuentaSeleccionada = request.getParameter("cuenta");
        String categoriaSeleccionada = request.getParameter("categoria");
        String fechaSeleccionada = request.getParameter("fecha");

        if (cuentaSeleccionada != null && !cuentaSeleccionada.isEmpty()) {
            Cuenta cuenta = cuentas.stream()
                                   .filter(c -> String.valueOf(c.getIdCuenta()).equals(cuentaSeleccionada))
                                   .findFirst()
                                   .orElse(null);
            if (cuenta != null) {
        %>
            <p>Filtrando por la cuenta: <strong><%= cuenta.getTipo() %></strong></p>
        <%
            }
        }

        if (categoriaSeleccionada != null && !categoriaSeleccionada.isEmpty()) {
            Categoria categoria = categorias.stream()
                                            .filter(c -> String.valueOf(c.getIdCat()).equals(categoriaSeleccionada))
                                            .findFirst()
                                            .orElse(null);
            if (categoria != null) {
        %>
            <p>Filtrando por la categoría: <strong><%= categoria.getTipoCat() %></strong></p>
        <%
            }
        }

        if (fechaSeleccionada != null && !fechaSeleccionada.isEmpty()) {
        %>
            <p>Filtrando para la fecha: <strong><%= fechaSeleccionada %></strong></p>
        <%
        }
        %>
    </div>

    <table>
        <thead>
            <tr>
                <th>Tipo</th>
                <th>Concepto</th>
                <th>Fecha</th>
                <th>Monto</th>
                <th>Categoría</th>
                <th>Cuenta Origen</th>
                <th>Cuenta Destino</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
            if (movimientos != null && !movimientos.isEmpty()) {
                for (Movimiento movimiento : movimientos) {
                    String tipoMovimiento = movimiento instanceof Ingreso ? "Ingreso" :
                                            movimiento instanceof Egreso ? "Egreso" : "Transferencia";
            %>
            <tr>
                <td><%= tipoMovimiento %></td>
                <td><%= movimiento.getConcepto() %></td>
                <td><%= movimiento.getFecha() %></td>
                <td><%= movimiento.getMonto() %></td>
                <td><%= movimiento.getCategoria().getTipoCat() %></td>
                <td><%= movimiento.getCuenta() != null ? movimiento.getCuenta().getTipo() : "" %></td>
                <td><%= movimiento.getCuentaDestino() != null ? movimiento.getCuentaDestino().getTipo() : "" %></td>
                <td>
                    <a href="RegistrarMovimientosController?ruta=editarMovimiento&id=<%= movimiento.getIdMov() %>" class="btn btn-primary">Editar</a>
                    <a href="RegistrarMovimientosController?ruta=eliminarMovimiento&id=<%= movimiento.getIdMov() %>" onclick="return confirm('¿Estás seguro de que deseas eliminar este movimiento?');" class="btn btn-secondary">Eliminar</a>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="7">No hay movimientos disponibles.</td>
            </tr>
            <%
            }
            %>
        </tbody>
    </table>
</body>
</html>
