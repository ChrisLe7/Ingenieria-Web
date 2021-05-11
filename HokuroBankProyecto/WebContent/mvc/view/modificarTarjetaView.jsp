<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>
<jsp:useBean  id="infoTarjetas" scope="session" class="es.uco.iw.display.InfoTarjetasBean"></jsp:useBean>  
<%@ page import ="es.uco.iw.negocio.tarjeta.TipoTarjeta, es.uco.iw.negocio.tarjeta.TarjetaDTO , es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modificar Tarjeta</title>
</head>
<body>

<%  
boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
String nextPage = "";
String mensajeNextPage = "";
if (clienteBean == null || clienteBean.getDni().equals(""))  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no está logueado";
}else{
	TarjetaDTO tarjeta = infoTarjetas.get(0);
%> 

<div>

Bienvenido a Modificar Tarjeta con ID: <%=tarjeta.getNumTarjeta()%> <br/>

Su pin actual es <%=tarjeta.getPin()%> <br/>

Para modificarlo, cambie el pin de la etiqueta inferior y confirme el cambio<br/>

<form method="post" action="Tarjetas">

	<label for="Pin">Pin: <input type="text" id="pinTarjeta" name="pin" value="<%=tarjeta.getPin()%>" pattern="[0-9]{4}" required></label>
	<input type="text" name="idCuenta" value="<%=tarjeta.getNumTarjeta()%>" style=display:none>
	
	<input type="submit" value="Confirmar">

</form>

</div>

<%} %>

</body>
</html>