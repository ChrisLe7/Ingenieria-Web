<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean  id="infoClienteBean" scope="session" class="es.uco.iw.display.UsuarioInfoBean"></jsp:useBean>

<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- scripts y css -->
<title>Modificar Contraseña</title>
	<script src="js/modificarPerfilBtn.js" defer></script> 
	<script src="js/sha512.js" defer></script>
	<script src="js/hashPassword.js" defer></script>
</head>

<body>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/Menu.jsp" %>
<%  


if (!logged)  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no esta logueado";
	%>
	<jsp:forward page="<%=nextPage%>">
		<jsp:param value="<%=mensajeNextPage%>" name="message"/>
	</jsp:forward>
	<% 
}else {%>
	
	
	<%

%> 
<main class="main">
	<div class="boxContrasena">
	<!-- ATENCION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
	<!-- cambiar el action al controlador correspondiente -->
		<form  method="post" action="ModificarPassword">
			
			<!-- En el caso de que no funcione, tener en cuenta el "name" del input!!!!!!!!!!!!!!!!!!!!!!!!! -->
			<label for="Contraseña ">Contraseña: </label> <input type="password" id="password" name="password"  required>
			
			<input class="boton" type="submit" id="submitBtn" value="Confirmar">
		
		</form>
	
	</div>
</main>
<% } %>

</body>
</html>