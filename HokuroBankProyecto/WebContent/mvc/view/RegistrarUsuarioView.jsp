<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean  id="infoClienteBean" scope="session" class="es.uco.iw.display.UsuarioInfoBean"></jsp:useBean>

<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>
    
<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<!-- scripts y css -->
<script src="js/sha512.js" defer></script>
<script src="js/hashPassword.js" defer></script>
<title>Registrarse</title>

</head>

<body>

	
	<%@ include file="/include/header.jsp" %>
	<%@ include file="/include/Menu.jsp" %>
	
	<% if(!clienteBean.getRol().equals(RolUsuario.Administrador)){
			nextPage = "index.jsp";
			mensajeNextPage = "Usted no es administrador";	
		}
		else{	
	
			%> 
			
			
			<main class = "main">
			
			<div>
			
				<form method="post" action="RegistrarUsuario" class = "formulario">
				
					<label for="DNI">DNI: </label> <br/>
					
					<input type="text" name="DNI" pattern="[0-9]{8}[A-Za-z]"> <br/>
				
					<label for="Nombre">Nombre: </label> <br/>
					
					<input type="text" name="nombre" > <br/>
					
					<label for="Apellidos">Apellidos: </label> <br/>
					
					<input type="text" name="apellidos" > <br/>
					
					<label for="Email">Email: </label> <br/>
					
					<input type="email" name="email" > <br/>
					
					<label for="Direccion">Direccion: </label> <br/>
					
					<input type="text" name="direccion" > <br/>
					
					<label for="Telefono">Telefono: </label> <br/>
					
					<input type="tel" name="telefono" pattern="[0-9]{9}"><br/>
					
					<label for="Rol">Rol: </label> <br/>
					
					<label> Cliente <input type="radio" name="rol" value = "Cliente"></label> <br/>
					
					<label>Administrador <input type="radio" name="rol" value = "Administrador"> </label> <br/>
					
					<label for="Password">Contraseña: </label> <br/>
					
					<input type="password" id="password" name="password" ><br/>
				
					<input type="submit" id="submitBtn" value="Registrarse">
					
				</form>
			
			</div>
			
			</main>
			
			<%} %>

</body>
</html>
