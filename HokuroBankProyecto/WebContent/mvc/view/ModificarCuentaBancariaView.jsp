<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>
<jsp:useBean  id="infoCuentas" scope="session" class="es.uco.iw.display.InfoCuentasBancariasBean"></jsp:useBean>
<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modificar Cuenta</title>
</head>
<body>

<%  
boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
String nextPage = "";
String mensajeNextPage = "";
if (clienteBean == null || clienteBean.getDni().equals(""))  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no está logueado";
}else if(!clienteBean.getRol().equals(RolUsuario.Administrador)){
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no es administrador";	
}else{
	CuentaBancariaDTO cuenta = infoCuentas.get(0);
%> 

<div>

Bienvenido a Modificar Saldo la cuenta del cliente : <%=cuenta.getIdTitular()%> <br/>

Su saldo actual es <%=cuenta.getSaldo()%> <br/>

Para modificarlo, cambie el saldo y confirme el cambio<br/>

<form method="post" action="ModificarCuenta">

	<label for="Saldo">Saldo: <input type="text" id="saldoCuenta" name="saldo" value="<%=cuenta.getSaldo()%>"></label>
	<input type="text" name="idCuenta" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
	
	<input type="submit" value="Confirmar">

</form>

</div>

<%} %>

</body>
</html>