<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO" %>
 
<jsp:useBean  id="infoTarjetas" scope="session" class="es.uco.iw.display.InfoTarjetasBean"></jsp:useBean>  


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestionar Tarjetas</title>
</head>
<body>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/Menu.jsp" %>

<main class = "main">

<%  

if (clienteBean == null || clienteBean.getDni().equals(""))  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no está logueado";
}else if(infoTarjetas.getTarjetas().isEmpty()){
	
%> 

<p>
	Aun no tienes tarjetas, porque no contratas una.
</p>
<form method=post action="ContratarTarjeta">
	<input type=text name="cuenta" class="hidden" value="">
	<input type=submit value="Contratar Tarjeta Nueva"> 
</form>


<% }else{ 
ArrayList<TarjetaDTO> ListaTarjetas = new ArrayList<TarjetaDTO>();
ListaTarjetas = infoTarjetas.getTarjetas();

%>

<div class="CuentasBancarias">
	
	<%
	for(TarjetaDTO tarjeta : ListaTarjetas){
	%>
	<div class="Cuenta">
	Numero de Tarjeta  <%=tarjeta.getNumTarjeta()%><br/>
	Tipo Tarjeta: <%=tarjeta.getTipotarjeta()%><br/>
	Id Cliente: <%=tarjeta.getIdCliente()%><br/>
	Cuenta Bancaria Vinculada: <%=tarjeta.getIdCuenta()%><br/>

	
	<br/>
	 	<div class ="opcion">
			<form method=post action="CancelarTarjeta">
				<input type=text name="idtarjeta" class="hidden" value="<%=tarjeta.getNumTarjeta()%>">
				<input type=submit value=Cancelar> 
			</form>
			 	
			
		
		</div>
	</div>
<%	}
}%>
</div>

</main>
</body>
</html>
