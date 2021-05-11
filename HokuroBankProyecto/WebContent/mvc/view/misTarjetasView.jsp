<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO" %>

<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>  

<jsp:useBean  id="infoTarjetas" scope="session" class="es.uco.iw.display.InfoTarjetasBean"></jsp:useBean>  


<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO , es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mis Tarjetas</title>
</head>
<body>

<%  
boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
String nextPage = "";
System.out.println("Estoy en la vista");
String mensajeNextPage = "";
if (clienteBean == null || clienteBean.getDni().equals(""))  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no está logueado";
}else if(infoTarjetas.getTarjetas().isEmpty()){
	
%> 

<p>
AUN NO TIENES TARJETAS ¿QUE TAL SI EMPEZAMOS POR CREAR UNA?
</p>

<!--  <p>
Crea una Tarjeta <a href="">AQUÍ</a>
</p>-->

<% }else{ 
ArrayList<TarjetaDTO> ListaTarjetas = new ArrayList<TarjetaDTO>();
ListaTarjetas = infoTarjetas.getTarjetas();

for(TarjetaDTO tarjeta : ListaTarjetas){
%>

<div class="Tarjetas">

	<h1> Tarjeta  <%=tarjeta.getNumTarjeta()%></h1>
	Pin: <%=tarjeta.getPin()%><br/>
	Tipo: <%=tarjeta.getTipotarjeta().toString()%><br/>
	Dueño: <%=tarjeta.getIdCliente()%><br/>
	Cuenta Asociada: <%=tarjeta.getIdCuenta()%><br/>	
	<br/>
	 
	 <form method="post" action="Tarjetas">
		<input type="text" name="tarjeta" value="<%=tarjeta.getNumTarjeta()%>" style=display:none>
		<input type="submit" value="Cancelar">
	</form>	
	 	
	<form method="post" action="Tarjetas">
		<input type="text" name="gestionar" value="<%=tarjeta.getNumTarjeta()%>" style=display:none>
		<input type="submit" value="Gestionar">
	</form>	
</div>	 	
	<%

	}
}%>
</body>
</html>
