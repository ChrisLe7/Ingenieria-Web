<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>
<jsp:useBean  id="infoCuentas" scope="session" class="es.uco.iw.display.InfoCuentasBancariasBean"></jsp:useBean>
<jsp:useBean  id="InfoTransacciones" scope="session" class="es.uco.iw.display.InfoTransaccionesBean"></jsp:useBean>

<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO , es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>
<%@ page import ="es.uco.iw.negocio.transaccion.TransaccionDTO, es.uco.iw.negocio.transaccion.TipoOperacion " %>
<%@ page import ="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mis Transacciones</title>
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
}else if(InfoTransacciones.getTarjetas().isEmpty()){
	System.out.println("Error");
%> 
<main class="main">
<p>
AUN NO HA REALIZADO NINGUNA TRANSACCION
</p>

<% }else{ 
	System.out.println("Error2");
ArrayList<TransaccionDTO> ListaTransacciones = new ArrayList<TransaccionDTO>();
ListaTransacciones = InfoTransacciones.getTarjetas();

for(TransaccionDTO transaccion : ListaTransacciones){
%>

<div class="Transacciones">

	<h1> Transacción  <%=transaccion.getIdTransaccion()%></h1>
	
	Cantidad: <%=transaccion.getCantidad()%><br/>
	
	Tipo: <%=transaccion.getTipoOperacion().toString()%><br/>
	
	Fecha: <%=transaccion.getFecha()%> <br/>
	
	Comentario: <%=transaccion.getComentario()%> <br/>
	
	Cuenta Origen: <%=transaccion.getIdCuentaOrigen()%><br/>
	
	Cuenta Destino: <%=transaccion.getIdCuentaDestino()%><br/>
	
</div>
<%	}
%>
	
	<div class="overlay" id="overlay">
	
		<div class="popup" id="popup">
		
			<form method="post" action="" id="filter">
				<a href="#" id="button-close-popup" class="button-close-popup"><i class="fas fa-times">Cerrar</i></a>
				
				<label for="idTransaccion">Id de la Transaccion:</label><br/>
				
				<input type="text" name="idTransaccion"><br/>
				
				<label for="tipoOperacion">Tipo de Operación:</label><br/>	
				
				<select name="tipoOperacion">
				  <option value="<%=TipoOperacion.Pagar %>">Pagar</option>
				  <option value="<%=TipoOperacion.Recibir %>">Recibir</option>
				</select><br/>
				
				<label for="Cantidad">Cantidad:</label><br/>
				
				<input type="text" name="cantidad" pattern="[0-9]{+}" ><br/>
				
				<label for="Fecha">Fecha de la transaccion:</label><br/>
				
				<input type="date" name="fecha"><br/>
				
				<label for="idCuentaOrigen">Id de la Cuenta: </label><br/>	
				
				<input type="text" name="idCuenta"><br/>
				
				<input id="button-submit" type="submit" value="Filtrar" name="filter">
				
			</form>
		
		</div>
	
	</div>
	
<% 
	
}%>
	</main>
</body>
</html>
