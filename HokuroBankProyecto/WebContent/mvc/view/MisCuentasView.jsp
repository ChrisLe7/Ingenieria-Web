<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="infoCuentas" scope="session" class="es.uco.iw.display.InfoCuentasBancariasBean"></jsp:useBean>

<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO , es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>
<%@ page import ="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mis Cuentas</title>
</head>
<body>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/Menu.jsp" %>

<main class="main">

<%  

if (clienteBean == null || clienteBean.getDni().equals(""))  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no está logueado";
}else if(infoCuentas.getCuentas().isEmpty()){
	
%> 

<p>
	Aun no tienes una cuenta bancaría, ¿que tal si se contacta con nuestros administradores?
</p>


<% }else{ 
ArrayList<CuentaBancariaDTO> ListaCuentas = new ArrayList<CuentaBancariaDTO>();
ListaCuentas = infoCuentas.getCuentas();

%>

<div class="CuentasBancarias">
	
	<%
	for(CuentaBancariaDTO cuenta : ListaCuentas){
	%>
	<div class="Cuenta">
	Numero de Cuenta  <%=cuenta.getIdCuentaBancaria()%><br/>
	Saldo: <%=cuenta.getSaldo()%><br/>
	Tipo: <%=cuenta.getTipoCuentaBancaria().toString()%><br/>
	Estado del Bizum: 
	<%if(cuenta.getEstadoBizum()){ 
		%> Activo <% } 
	else {
		%> No Activo <%	}%>
	
	<br/>
	 
	 <form method="post" action="ModificarCuenta">
		<input type="text" name="modificar_bizum" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
		<% if (cuenta.getEstadoBizum()) {%>
			<input type="submit" value="Deshabilitar Bizum">
		<% } else if (!cuenta.getEstadoBizum()) {%>
			<input type="tel" name="telefono" pattern="[0-9]{9}" required>
			<input type="submit" value="Habilitar Bizum">
		<% } %>
	</form>	
	
	 <form method="post" action="MisTransferencias">
		<input type="text" name="idCuenta" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
		<input type="submit" value="Mis Transferencias">
	</form>	
    <form method="post" action="MisPagosBizum">
		<input type="text" name="idCuenta" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
		<input type="submit" value="Mis Transferencias">
	</form>	
	 	
	
	 	
	<%

		if(clienteBean.getRol().equals(RolUsuario.Administrador)){
%>
		 <form method="post" action="ModificarCuenta">	
		 	<input type="text" name="modificar_saldo" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
			<input type="submit" value="Modificar Saldo">
		 </form>	
	
		<form method=post action="CancelarCuentaBancaria">
			<input type=text name="idCuenta" class="hidden" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
			<input type=submit value="Cancelar Cuenta Bancaria"> 
		</form>

		<form method=post action="GestionarTitulares">
			<input type=text name="idCuenta" class="hidden" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
			<input type=submit value="Gestionar Titulares"> 
		</form>
<% 		}%>
	</div>
<%	}
}%>
</div>
</main>
</body>
</html>
