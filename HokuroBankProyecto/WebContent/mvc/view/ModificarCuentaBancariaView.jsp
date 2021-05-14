<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>
<jsp:useBean  id="infoCuentas" scope="session" class="es.uco.iw.display.InfoCuentasBancariasBean"></jsp:useBean>
<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modificar Cuenta</title>
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
	CuentaBancariaDTO cuenta = infoCuentas.get(0);
%> 
<main class="main">
<div>

Bienvenido a Modificar Saldo la cuenta del cliente : <%=cuenta.getIdTitular()%> <br/>

Su saldo actual es <%=cuenta.getSaldo()%> <br/>

Para modificarlo, cambie el saldo y confirme el cambio<br/>

<form method="post" action="ModificarCuenta">

	<label for="Saldo">Saldo: <input type="text" id="cantidad" name="saldo" value="<%=cuenta.getSaldo()%>"></label>
	<input type="text" name="idCuenta" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
	
	<input type="submit" value="Confirmar">

</form>

</div>
	</main>
<%} 

}%>

</body>
</html>
