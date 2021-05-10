<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="js/hashPassword.js" defer></script>
    <title>Login</title>
</head>
<body>

	<%@ include file="/include/header.jsp" %>
    <main class = "main"> 
        <!-- Formulario para iniciar sesión -->
        <h1 class="formulario_titulo">Introduzca sus Datos para Registrarse.</h1> 


        <form method="post" action="Login" id="formularioUsuario" class="formulario" >
            <h1>Formulario Registro</h1>
            <input type="text" name="DNI" placeholder="Dni" required><br/>
            <input type="password" id="password" name="Password" placeholder="Password" required><br/>
            <input class="button" type="submit" id="submitBtn" value="Iniciar Sesión">
        </form>


    </main>

</body>
</html>