<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean  id="listadoClientes" scope="session" class="es.uco.iw.display.ListadoClientesBean"></jsp:useBean> 

<%@ page import ="java.util.ArrayList, es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- scripts y css -->
<title>Resetear Contraseña</title>

	<script src="js/crearPasswordAleatoria.js" defer></script> 
	<script src="js/sha512.js" defer></script>
	<script src="js/hashPassword.js" defer></script>
</head>

<body>

<%@ include file="/include/header.jsp" %>
<%@ include file="/include/Menu.jsp" %>

<main class="main">
	<div>
	<!-- ATENCION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
	<!-- cambiar el action al controlador correspondiente -->
		<form method="post" action="ResetPassword">
			
			<label for="Email">Email: </label> <input type="email" name="email"  required><br/>
			<input type="password" id="prehashPassword" name="prehashPassword" style=display:none><br/>
			<input type="password" id="password" name="password" style=display:none><br/>
			<% if (clienteBean.getRol().equals(RolUsuario.Administrador)) {
				//En este caso deberiamos mostrar el listado de los usuarios a resetear o que se haya cogido antes. Lo recomendable 
				//seria hacerlo aquí y que en el controlador cogerlo.
				 ArrayList<UsuarioDTO> listaCuentas = listadoClientes.getUsuarios();%>
			%>
				
		
						<label>Titular: </label>
					
					<select id="idTitular" name="idCliente"> 
					
						<% for(UsuarioDTO user : listaCuentas){%>
					
						<option value="<%=user.getDni()%>">	
					
						<%} %>
					
					</select>
			<%} else {%>
				<input type="password" name="idCliente" value="<%=clienteBean.getDni()%>"style=display:none><br/>
			<% }%>
			<input type="submit" id="submitBtn" value="Confirmar">
		
		</form>
	
	</div>
</main>


</body>
</html>