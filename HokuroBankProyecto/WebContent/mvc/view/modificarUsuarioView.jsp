<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="infoClienteBean" scope="session" class="es.uco.iw.display.UsuarioInfoBean"></jsp:useBean>

<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!-- scripts y css -->
<script src="js/modificarPerfilBtn.js" defer></script> 
<script src="js/sha512.js" defer></script>
<script src="js/hashPassword.js" defer></script>
<title>Perfil</title>

</head>

<body>

<%@ include file="/include/header.jsp" %>
<%@ include file="/include/Menu.jsp" %>
<%  


if (clienteBean == null || clienteBean.getDni().equals(""))  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no está logueado";
}else{
	
	UsuarioDTO user = infoClienteBean.getUsuario();
	
	if (user == null) {
		System.out.println("VACIO");
	}
%> 
<!-- Los divs pueden cambiarse de clases a identificadores -->
<main class="main">
<h1 class="formulario_titulo"></h1> 
<div class="formulario" id="observarP" >

	<form method="post" action="Perfil">
	
		<label for="DNI">DNI: <%=user.getDni()%></label> <br/>
		
		<label for="Nombre">Nombre: <%=user.getNombre()%></label> <br/>
		
		<label for="Apellidos">Apellidos: <%=user.getApellidos()%></label> <br/>
		
		<label for="Email">Email: <%=user.getEmail()%></label> <br/>
		
		<label for="Dirección">Dirección: <%=user.getDireccion()%></label> <br/>
		
		<label for="Teléfono">Teléfono: <%=user.getTelefono()%></label> <br/>		 

	</form>
	
	<!--  Si se clicka, este div debe de hacerse invisible y el de abajo debe dejarse visible -->
	<button id="observarPButton">Habilitar Modificaciones</button>

</div>

<!-- El style se puede poner en el css -->
<div class="formulario" id="modificarP" style="display:none">

	<form method="post" action="Perfil">
	
		<label for="DNI">DNI: </label> <%=user.getDni()%><br/>
		
		<label for="Nombre">Nombre: <%=user.getNombre()%></label> <br/>
		
		<label for="Apellidos">Apellidos: <%=user.getApellidos()%></label> <br/>
		
		<label for="Email">Email: </label> <input type="text" name="correo" value="<%=user.getEmail()%>"><br/>
		
		<label for="Dirección">Dirección: </label> <input type="text" name="direccion" value="<%=user.getDireccion()%>"><br/>
		
		<label for="Teléfono">Teléfono: </label> <input type="tel" name="telefono" pattern="[0-9]{9}" value="<%=user.getTelefono()%>"><br/>
		 
		
		
		<input type="submit" id="submitBtn" value="Enviar Modificación">
	
	</form>
	
	<!--  Si se clicka, este div debe de hacerse invisible y el de arriba debe dejarse visible -->
	<button id="modificarPButton">Deshabilitar Modificaciones</button>

</div>

</main>
<% } %>

</body>
</html>
