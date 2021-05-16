<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="infoCuentas" scope="session" class="es.uco.iw.display.InfoCuentasBancariasBean"></jsp:useBean>
<jsp:useBean  id="InfoTransacciones" scope="session" class="es.uco.iw.display.InfoTransaccionesBean"></jsp:useBean>

<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO , es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>
<%@ page import ="es.uco.iw.negocio.transaccion.TransaccionDTO, es.uco.iw.negocio.transaccion.TipoOperacion " %>
<%@ page import ="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/popup.css">
<script src="js/popup.js" defer></script>
<script src="js/crearFiltro.js" defer></script>
<script src="js/mostrarMovimiento.js" defer></script>
<title>Mis Transacciones</title>
</head>
<body>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/Menu.jsp" %>
<main class="main">

<button class="button-open-popup" id="button-open-popup">Filtros</button>

<%  

System.out.println("Estoy en la vista");

if (clienteBean == null || clienteBean.getDni().equals(""))  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no está logueado";
}else if(InfoTransacciones.getTransacciones().isEmpty()){
	System.out.println("Error");
%> 

<p>
AUN NO HA REALIZADO NINGUNA TRANSACCION
</p>

<% }else{ 
	System.out.println("Error2");
ArrayList<TransaccionDTO> ListaTransacciones = new ArrayList<TransaccionDTO>();
ListaTransacciones = InfoTransacciones.getTransacciones();

for(TransaccionDTO transaccion : ListaTransacciones){
%>

<div class="Transacciones">

	<h1> Transacción  <%=transaccion.getIdTransaccion()%></h1>
	
	Cantidad: <%=transaccion.getCantidad()%><br/>
	
	Tipo: <%=transaccion.getTipoOperacion().toString()%><br/>
	
	Fecha: <%=transaccion.getFecha()%><br/>
	
	Comentario: <%=transaccion.getComentario()%><br/>
	
	Cuenta Origen: <%=transaccion.getIdCuentaOrigen()%><br/>
	
	Cuenta Destino: <%=transaccion.getIdCuentaDestino()%><br/>
	
	<form>
		<input type="hidden" name="idTransaccion" value="<%=transaccion.getIdTransaccion()%>">
		<input type="hidden" name="cantidad" value="<%=transaccion.getCantidad()%>">
		<input type="hidden" name="fecha" value="<%=transaccion.getFecha()%>">
		<input type="hidden" name="idCuentaOrigen" value="<%=transaccion.getIdCuentaOrigen()%>">
		<input type="hidden" name="idCuentaDestino" value="<%=transaccion.getIdCuentaDestino()%>">
		<input type="hidden" name="idCuentaSeleccionada" value="<%=InfoTransacciones.getIdCuenta()%>">
	</form>

</div>
<%	}
}
%>
	
	<div class="overlay" id="overlay">
	
		<div class="popup" id="popup">
		
			<form method="post" action="" id="filter">
				<a href="#" id="button-close-popup" class="button-close-popup"><i class="fas fa-times">Cerrar</i></a>
				
				<label for="idTransaccion">Id de la Transaccion:</label><br/>
				
				<input type="text" name="idTransaccion"><br/>
				
				<label for="tipoOperacion">Tipo de Operación:</label><br/>	
				
				<!-- <select name="tipoOperacion">
				  <option value="<%=TipoOperacion.Pagar %>">Pagar</option>
				  <option value="<%=TipoOperacion.Recibir %>">Recibir</option>
				</select><br/>  -->
				
				<label><input type="radio" name="tipoOperacion" value="Pagar">Pagar</label>
				
				<label><input type="radio" name="tipoOperacion" value="Recibir">Recibir</label><br/>
				
				<label for="Cantidad">Cantidad Minima:</label><br/>
				
				<input type="text" name="cantidadMin" pattern="[0-9]+(.[0-9]{1,2})?" ><br/>
				
				<label for="Cantidad">Cantidad Maxima:</label><br/>

				<input type="text" name="cantidadMax" pattern="[0-9]+(.[0-9]{1,2})?" ><br/>
				
				<label for="FechaInicio">Fecha de inicio del margen:</label><br/>
				
				<input type="date" name="fechaInicio"><br/>
				
				<label for="FechaFin">Fecha fin del margen:</label><br/>

				<input type="date" name="fechaFin"><br/>
				
				<label for="idCuentaInvolucrada">Id de la Cuenta: </label><br/>	
				
				<input type="text" name="idCuentaInvolucrada"><br/>
				
				<input type="hidden" name="idCuenta" value="<%=InfoTransacciones.getIdCuenta()%>">
				
				<input id="button-submit" type="submit" value="Filtrar" name="filter">
				
			</form>
		
		</div>
	
	</div>
	
	</main>
</body>
</html>
