<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO" %>
<%@ page import ="es.uco.iw.negocio.tarjeta.TipoTarjeta, es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>

<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>  

<jsp:useBean  id="infoTarjetas" scope="session" class="es.uco.iw.display.InfoTarjetasBean"></jsp:useBean>  



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mis Tarjetas</title>
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
	
			<%@ include file="/include/header.jsp" %>
			
			<%
			if(infoTarjetas.getTarjetas().isEmpty()){
			
		%> 
		
		<p>
		AUN NO TIENES TARJETAS ¿QUE TAL SI EMPEZAMOS POR CREAR UNA?
		</p>
		
		<!--  <p>
		Crea una Tarjeta <a href="">AQUÃ</a>
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
			DueÃ±o: <%=tarjeta.getIdCliente()%><br/>
			Cuenta Asociada: <%=tarjeta.getIdCuenta()%><br/>	
			<br/>
			 
			 <form method="post" action="Tarjetas">
				<input type="text" name="tarjeta" value="<%=tarjeta.getNumTarjeta()%>" style=display:none>
				<input type="submit" value="Cancelar">
			</form>	
			 	
			<form method="post" action="ModificarTarjeta">
				<input type="text" name="idTarjetaModificar" value="<%=tarjeta.getNumTarjeta()%>" style=display:none>
				<input type="submit" value="Gestionar">
			</form>	
		</div>	 	
			<%
		
			}
		}
}
%>
</body>
</html>
