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
System.out.println("BEAN FALLARA");
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
	
	<%
	
	UsuarioDTO user = infoClienteBean.getUsuario();
	
	if (user == null) {
		System.out.println("VACIO");
	}
%> 
<!-- Los divs pueden cambiarse de clases a identificadores -->
<div id="observarP" >

	<form method="post" action="Perfil">
	
		<label for="DNI">DNI: <%=user.getDni()%></label> <br/>
		
		<label for="Nombre">Nombre: <%=user.getNombre()%></label> <br/>
		
		<label for="Apellidos">Apellidos: <%=user.getApellidos()%></label> <br/>
		
		<label for="Email">Email: <%=user.getEmail()%></label> <br/>
		
		<label for="Dirección">Dirección: <%=user.getDireccion()%></label> <br/>
		
		<label for="Teléfono">Teléfono: <%=user.getTelefono()%></label> <br/>		 

	</form>
	
	<!--  Si se clicka, este div debe de hacerse invisible y el de abajo debe dejarse visible -->
	<button id="observarPButton" onclick="">Habilitar Modificaciones</button>

</div>

<!-- El style se puede poner en el css -->
<div id="modificarP" style="display:none">

	<form method="post" action="Perfil">
	
		<label for="DNI">DNI: </label> <%=user.getDni()%><br/>
		
		<label for="Contraseña">Contraseña: </label> <input type="password" name="password" required><br/>
		
		<label for="Nombre">Nombre: <%=user.getNombre()%></label> <br/>
		
		<label for="Apellidos">Apellidos: <%=user.getApellidos()%></label> <br/>
		
		<label for="Email">Email: </label> <input type="text" name="correo" value="<%=user.getEmail()%>"><br/>
		
		<label for="Dirección">Dirección: </label> <input type="text" name="direccion" value="<%=user.getDireccion()%>"><br/>
		
		<label for="Teléfono">Teléfono: </label> <input type="tel" name="telefono" pattern="[0-9]{9}" value="<%=user.getTelefono()%>"><br/>
		 
		
		
		<input type="submit" id="submitBtn" value="Enviar Modificación">
	
	</form>
	
	<!--  Si se clicka, este div debe de hacerse invisible y el de arriba debe dejarse visible -->
	<button id="modificarPButton" onclick="">Deshabilitar Modificaciones</button>

</div>


<% } %>

</body>
</html>
