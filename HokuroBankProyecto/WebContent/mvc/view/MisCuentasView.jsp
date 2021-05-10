<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>
<jsp:useBean  id="infoCuentas" scope="session" class="es.uco.iw.display.InfoCuentasBancariasBean"></jsp:useBean>

<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mis Cuentas</title>
</head>
<body>

<%  
boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
String nextPage = "";
String mensajeNextPage = "";
if (clienteBean == null || clienteBean.getDni().equals(""))  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no está logueado";
}else if(infoCuentas.getCuentas().isEmpty()){
	
%> 

<p>
AUN NO TIENES CUENTAS BANCARIAS ¿QUE TAL SI EMPEZAMOS POR CREAR UNA?
</p>

<!--  <p>
Crea una Cuenta Bancaria <a href="">AQUÍ</a>
</p>-->

<% }else{ 
ArrayList<CuentaBancariaDTO> ListaCuentas = new ArrayList<CuentaBancariaDTO>();
ListaCuentas = infoCuentas.getCuentas();

for(CuentaBancariaDTO cuenta : ListaCuentas){
%>

<div class="CuentasBancarias">

	<h1> Cuenta  <%=cuenta.getIdCuentaBancaria()%></h1>
	Saldo: <%=cuenta.getSaldo()%><br/>
	Tipo: <%=cuenta.getTipoCuentaBancaria().toString()%><br/>
	Estado del Bizum: <%if(cuenta.estadoBizum()){ 
	%> Activo <br/>
	 
	 <form method="post" action="ModificarCuenta">
		<input type="text" name="modificar_bizum" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
		<input type="submit" value="Deshabilitar Bizum">
	</form>	
	 	

	 	
	<%
	}else{ 
		
	%> No Activo <%} %><br/>

	<form method="post" action="ModificarCuenta">
		<input type="text" name="modificar_bizum" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
		<input type="submit" value="Habilitar Bizum">
	</form>	


<%}
	if(clienteBean.getRol().equals(RolUsuario.Administrador)){
%>
	 <form method="post" action="ModificarCuenta">	
	 	<input type="text" name="modificar_saldo" value="<%=cuenta.getIdCuentaBancaria()%>" style=display:none>
		<input type="submit" value="Modificar Saldo">
	</form>	

</div>
<% 
	}
}%>
</body>
</html>