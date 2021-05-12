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
<script src="js/sha512.js" defer></script>
<script src="js/hashPassword.js" defer></script>
<title>Registrarse</title>

</head>

<body>

<%  
boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
String nextPage = "";
String mensajeNextPage = "";
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
	
	<% if(!clienteBean.getRol().equals(RolUsuario.Administrador)){
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no es administrador";	
}else{	
	
%> 

<div>

	<form method="post" action="RegistrarUsuario">
	
		<label for="DNI">DNI: </label> <br/>
		
		<input type="text" name="DNI" pattern="[0-9]{8}[A-Z]"> <br/>
	
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
		
		<label><input type="radio" name="rol" value = "Cliente"> Cliente</label> <br/>
		
		<label><input type="radio" name="rol" value = "Administrador"> Administrador</label> <br/>
		
		<label for="Password">Contraseña: </label> <br/>
		
		<input type="password" name="password" ><br/>
	
		<input type="submit" id="submitBtn" value="Registrarse">
		
	</form>

</div>


<%} 

}%>

</body>
</html>
