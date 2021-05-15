<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO, es.uco.iw.negocio.tarjeta.TipoTarjeta" %>
<%@ page import ="es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO, es.uco.iw.negocio.usuario.PropiedadCuenta" %>
<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>


<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.iw.display.UsuarioInfoBean"></jsp:useBean>  



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrar Tarjeta</title>
</head>
<body>
	<!-- Incluimos la cabecera de la aplicación -->
	



<% 	
	UsuarioDTO user = UsuarioInfoBean.getUsuario();
	
	ArrayList<PropiedadCuenta> listaCuentas = user.getCuentasBancarias();
	if (listaCuentas.isEmpty()) {
		String nextPage = "/index.jsp";
		String mensajeNextPage = "No posee cuentas, contrate una antes de solicitar una tarjeta";
	%>
		<jsp:forward page="<%=nextPage%>">
			<jsp:param value="<%=mensajeNextPage%>" name="message"/>
		</jsp:forward>
	<% 
	}
	
	else {%>	
	<%@ include file="/include/header.jsp" %>
	<%@ include file="/include/Menu.jsp" %>
	
	
	<main class = "main">
			
	<form method="post" action="RegistrarTarjeta" class = "creartarjeta">	
	
		<label for="Pin">Pin: </label>
		
		<input type="password" name="pin" pattern="[0-9]{4}" required>
		
		<label for="Tipo">Tipo de Tarjeta: </label>
		
			<label><input type="radio" name="tipoTarjeta" value = "Credito" required> Credito</label> <br/>
			
			<label><input type="radio" name="tipoTarjeta" value = "Debito" > Debito</label> <br/>
		
			<label><input type="radio" name="tipoTarjeta" value = "Prepago"> Prepago</label> <br/>	
		
		<label for="Cuenta">Cuenta a la que enlazarla: </label>
		
			<% 
	
			for(PropiedadCuenta cuenta : listaCuentas){%>
			
			<label><input type="radio" name="idCuenta" value = "<%=cuenta.getIdCuentaBancaria() %>"> Id: <%=cuenta.getIdCuentaBancaria() %></label> <br/>
			
			<%} %>
		<input type="submit" id="submitBtn" value="Crear Tarjeta">
	</form>



	</main>
 	<%}%>

</body>
</html>
