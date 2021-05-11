<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO" %>
<%@ page import ="es.uco.iw.negocio.tarjeta.TipoTarjeta, es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>
<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO , es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>

<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>  
<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.iw.display.UsuarioInfoBean"></jsp:useBean>  
<jsp:useBean  id="infoTarjetas" scope="session" class="es.uco.iw.display.InfoTarjetasBean"></jsp:useBean>  



<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registrar Tarjeta</title>
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
<% 	
	UsuarioDTO user = UsuarioInfoBean.getUsuario();
	
	ArrayList<PropiedadCuenta> ListaCuentas = new ArrayList<PropiedadCuenta>();
	ListaCuentas = user.getCuentasBancarias();
	%>	
	<%@ include file="/include/header.jsp" %>
			
	<form method="post" action="RegistrarTarjeta">
	
		<label for="Pin">Pin: </label>
		
		<input type="text" name="pin" pattern="[0-9]{4}" required>
		
		<label for="Tipo">Tipo de Tarjeta: </label>
		
			<label><input type="radio" name="tipoTarjeta" value = "Credito" required> Credito</label> <br/>
			
			<label><input type="radio" name="tipoTarjeta" value = "Debito" > Debito</label> <br/>
		
			<label><input type="radio" name="tipoTarjeta" value = "Prepago"> Prepago</label> <br/>	
		
		<label for="Cuenta">Cuenta a la que enlazarla: </label>
		
			<% 
	
			for(CuentaBancariaDTO cuenta : ListaCuentas){%>
			
			<label><input type="radio" name="idCuenta" value = "<%=cuenta.idCuentaBancaria %>"> Id: <%=cuenta.idCuentaBancaria %></label> <br/>
			
			<%} %>
			
	</form>


<% 	
}
%>
</body>
</html>