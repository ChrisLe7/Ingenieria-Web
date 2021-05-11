<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>
<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
</head>


<body>
	
	<% 
		System.out.println(clienteBean);
		System.out.println(clienteBean.getDni());
		
		boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
		String nextPage = "Login";
		String mensajeNextPage = "";
		if (logged)  {
			/* Significa que el usuario se encuentra logueado. */
			
			/*Opciones del menú*/
			
			%>
			<header class ="header">
			
				<div class = "container logo-nav-container">
					<a href = "Home" class = "logo"> HokuroBank</a>
					
					<nav class = "navigation">
						<ul class = "option">
						<li><a href = "Logout">Desconectar</a></li>
						<li><a href = "Perfil">Ver Perfil</a></li>
						<li><a href = "MisTransferencias">Realizar Transferencia</a></li>
						<li><a href = "MisCuentas">Gestionar Cuentas</a></li>
						<li><a href = "MisTarjetas">Gestionar Tarjetas</a></li>
						<li><a href = "RegistrarTarjeta">Crear Tarjeta</a></li>
						<% if ( (clienteBean != null) && (clienteBean.getRol().equals(RolUsuario.Administrador))) { %>
						<li><a href = "RegistrarUsuario">Agregar Cuenta</a></li>
						<% } %>
						</ul>
											
					</nav>
				
				</div>
			
			
			</header>
			
		<% 	
		}
		else {
			//No se encuentra logueado deberá de irse al controlador de login.
			nextPage = "/Login";
			mensajeNextPage = "Debera de Iniciar Sesion para acceder al Sistema";
		%>
		<jsp:forward page="<%=nextPage%>">
			<jsp:param value="<%=mensajeNextPage%>" name="message"/>
		</jsp:forward>

		<%} %>
    
</body>
</html>
