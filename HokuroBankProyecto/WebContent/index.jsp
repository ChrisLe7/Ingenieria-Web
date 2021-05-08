<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
</head>


<body>
	
	<% 
		Boolean logged = true;
		String nextPage = "Login";
		String mensajeNextPage = "";
		if (logged)  {
			/* Significa que el usuario se encuentra logueado. */
			
			/*Opciones del menú*/
			
			%>
			<header class ="Cabecera">
			
				<div class = "HomeMenu">
					<a href = "Home" class = "logo"> HokuroBank</a>
					
					<nav class = "MenuNavegacion">
						<ul class = "opciones">
						<li><a href = "Logout">Desconectar</a></li>
						<li><a href = "Perfil">Ver Perfil</a></li>
						<li><a href = "MisTransferencias">Realizar Transferencia</a></li>
						<li><a href = "MisCuentas">Gestionar Cuentas</a></li>
						<li><a href = "MisTarjetas">Gestionar Tarjetas</a></li>
						</ul>
											
					</nav>
				
				</div>
			
			
			</header>
			
		<% 	
		}
		else {
			//No se encuentra logueado deberá de irse al controlador de login.
			nextPage = "Login";
			mensajeNextPage = "Debera de Iniciar Sesion para acceder al Sistema";
		%>
		<jsp:forward page="<%=nextPage%>">
			<jsp:param value="<%=mensajeNextPage%>" name="message"/>
		</jsp:forward>

		<%} %>
    
</body>
</html>