<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>
<jsp:useBean  id="infoClienteBean" scope="session" class="es.uco.iw.display.UsuarioInfoBean"></jsp:useBean>

<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- scripts y css -->
<title>Perfil</title>
	<script src="js/modificarPerfilBtn.js" defer></script> 
	<script src="js/sha512.js" defer></script>
	<script src="js/hashPassword.js" defer></script>
</head>

<body>

<%  
boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
String nextPage = "";
String mensajeNextPage = "";
System.out.println("HOla");
if (!logged)  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no esta logueado";
	%>
	<jsp:forward page="<%=nextPage%>">
		<jsp:param value="<%=mensajeNextPage%>" name="message"/>
	</jsp:forward>
	<% 
}else {%>
	
	<%@ include file="/include/header.jsp" %>
	
	<%

%> 

<div>
<!-- ATENCION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
<!-- cambiar el action al controlador correspondiente -->
	<form method="post" action="Perfil">
		
		<!-- En el caso de que no funcione, tener en cuenta el "name" del input!!!!!!!!!!!!!!!!!!!!!!!!! -->
		<label for="Contraseña">Contraseña: </label> <input type="password" name="password"  required><br/>
		
		<input type="submit" value="Confirmar">
	
	</form>

</div>

<% } %>

</body>
</html>