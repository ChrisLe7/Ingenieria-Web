<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO, es.uco.iw.negocio.tarjeta.TipoTarjeta" %>
<%@ page import ="es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO, es.uco.iw.negocio.usuario.PropiedadCuenta" %>
<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>

<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>  
<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.iw.display.UsuarioInfoBean"></jsp:useBean>  
<jsp:useBean  id="listadoClientes" scope="session" class="es.uco.iw.display.ListadoClientesBean"></jsp:useBean> 


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrar Cuenta</title>
</head>
<body>
	<!-- Incluimos la cabecera de la aplicación -->
	

<%  
boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
String nextPage = "";
String mensajeNextPage = "";
if (logged == false)  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no esta logueado";
	%>
	<jsp:forward page="<%=nextPage%>">
		<jsp:param value="<%=mensajeNextPage%>" name="message"/>
	</jsp:forward>
	<% 
}else {%>
	
			
					<main class = "main">
			
	<form method="post" action="RegistrarCuentaBancaria">
			

		<label for="Tipo">Tipo de Cuenta: </label>
		
			<label><input type="radio" name="tipoCuenta" value = "ahorro" required> Ahorro</label> <br/>
			
			<label><input type="radio" name="tipoCuenta" value = "corriente" > Corriente</label> <br/>		
			
		<%if(clienteBean.getRol().equals(RolUsuario.Administrador)){ 
			
	
		 ArrayList<UsuarioDTO> listaCuentas = listadoClientes.getUsuarios();%>
		
		<label>Titular: </label>
			
			<select id="idTitular" name="idTitular"> 
			
				<% for(UsuarioDTO user : listaCuentas){%>
			
				<option value="<%=user.getDni()%>">	
			
				<%} %>
			
			</select>
			
		<%}else{ %>
			
			<input type="text" name="idTitular" value = "<%=clienteBean.getDni()%>" style="display:none"> <br/>	
			
		<%} %>
			
		<input type="submit" value = "Confirmar"> 
		
	</form>

</main>


<% 	
}
%>
</body>
</html>
