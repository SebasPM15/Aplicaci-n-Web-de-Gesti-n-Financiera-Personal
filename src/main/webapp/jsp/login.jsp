<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>VENTANA DE LOGIN</title>
<style>
html, body {
    margin: 0;
    padding: 0;
    height: 100%;
    display: flex;
    flex-direction: column;
}

body {
    background-color: gray;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.container {
    flex: 1; /* Permite que el contenedor se expanda para llenar el espacio disponible */
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    text-align: center;
}

.form_area {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    background-color: #B0C4DE; /* Azul marino claro */
    height: auto;
    width: auto;
    border: 2px solid #264143;
    border-radius: 20px;
    box-shadow: 3px 4px 0px 1px #CD5C5C; /* Rojo suave */
    padding: 20px;
}

.title {
    color: #264143;
    font-family: "Oswald", sans-serif;
    font-weight: 900;
    font-size: 1.5em;
    margin-top: 20px;
}

.sub_title {
    font-family: "Oswald", sans-serif;
    font-weight: 600;
    margin: 5px 0;
}

.form_group {
    display: flex;
    flex-direction: column;
    align-items: baseline;
    margin: 10px;
}

.form_style {
    outline: none;
    border: 2px solid #264143;
    box-shadow: 3px 4px 0px 1px #CD5C5C; /* Rojo suave */
    width: 290px;
    padding: 12px 10px;
    border-radius: 4px;
    font-size: 15px;
}

.form_style:focus, .btn:focus {
    transform: translateY(4px);
    box-shadow: 1px 2px 0px 0px #CD5C5C; /* Rojo suave */
}

.btn {
    padding: 15px;
    margin: 25px 0px;
    width: 290px;
    font-size: 15px;
    background: #A0522D; /* Marrón suave */
    border-radius: 10px;
    font-weight: 800;
    box-shadow: 3px 3px 0px 0px #CD5C5C; /* Rojo suave */
    border: none;
    color: white;
    cursor: pointer;
}

.btn:hover {
    opacity: .9;
}

.link {
    font-weight: 800;
    color: #264143;
    padding: 5px;
    text-decoration: none;
}

.logo {
    position: absolute;
    width: 200px; /* Ajusta el tamaño según sea necesario */
    height: auto;
    top: 20px; /* Ajusta la distancia desde el top según sea necesario */
    left: 50%;
    transform: translateX(-50%); /* Centra la imagen horizontalmente */
}

footer {
    text-align: center;
    padding: 10px;
    background-color: #f1f1f1;
    border-top: 1px solid #ddd;
}
</style>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
    href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Oswald:wght@200..700&family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap"
    rel="stylesheet">
</head>
<body>
    <img
        src="https://cem.epn.edu.ec/imagenes/logos_institucionales/big_jpg/EPN_logo_big.jpg"
        alt="Logo EPN" class="logo">
    <div class="container">
        <div class="form_area">
            <p class="title">LOGIN</p>
            <form method="POST" action="../AutenticarController?ruta=ingresar">
                <div class="form_group">
                    <label class="sub_title" for="name">Nombre</label> <input
                        name="nombre" placeholder="Ingresa tu nombre" class="form_style"
                        type="text" required>
                </div>
                <div class="form_group">
                    <label class="sub_title" for="password">Clave</label> <input
                        name="clave" placeholder="Ingresa tu clave" id="password"
                        class="form_style" type="password" required>
                </div>
                <div>
                    <button class="btn">LOGIN</button>
                </div>
            </form>
        </div>
    </div>
    <footer>
        <p>Grupo 3 (Canguros) &copy; 2024</p>
    </footer>
</body>
</html>
