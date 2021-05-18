<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="infoUsuario" scope="session" class="es.uco.iw.display.UsuarioInfoBean"></jsp:useBean>

<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO , es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>
<%@ page import ="es.uco.iw.negocio.usuario.PropiedadCuenta" %>
<%@ page import ="es.uco.iw.negocio.transaccion.TipoOperacion" %>
<%@ page import ="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Realizar Transacciones</title>
</head>
<body>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/Menu.jsp" %>

<%  

System.out.println("Estoy en la vista");

if (clienteBean == null || clienteBean.getDni().equals(""))  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no está logueado";
	%>
	<jsp:forward page="<%=nextPage%>">
		<jsp:param value="<%=mensajeNextPage%>" name="message"/>
	</jsp:forward>
	<% 
}else{ 
	ArrayList<PropiedadCuenta> ListaCuentas = new ArrayList<PropiedadCuenta>();
	if (infoUsuario.getUsuario() == null) {
		System.out.println("Vacio");
	}
	else {
		ListaCuentas = infoUsuario.getUsuario().getCuentasBancarias();
	}
	%>

		<main class="main">
	
		<div>
		
			<form class="formularioTransaccion" method="post" action="RealizarTransaccion">
			
			<label for="idCuentaDestino">Id de la cuenta Destion: </label>
				
			<input type="text" name="idCuentaDestino" required><br/>
			
			<label for="idCuentaOrigen">Id de la cuenta Origen: </label>		
				
			<select class="select" name="idCuentaOrigen" required>
				<%for(PropiedadCuenta cuenta : ListaCuentas){ %>
				 	 <option value="<%=cuenta.getIdCuentaBancaria()%>"><%=cuenta.getIdCuentaBancaria()%></option>
				<%} %>
			</select>
			
			<br/><label for="Descripcion">Descripción: </label>		
				
			<input type="text" name="descripcion" required>
			
			<br/><label for="Cantidad">Cantidad: </label>	
				
			<input type="text" name="cantidad" pattern="[0-9]+(.[0-9]{1,2})?" required><br/>
		
			<!-- <label for="TipoOperacion">Tipo de Operacion: </label>	 -->
				
			<!-- <select name="tipoOperacion" required>
			  <option value=<%=TipoOperacion.Pagar %>>Pagar</option>
			  <option value=<%=TipoOperacion.Recibir %>>Recibir</option>
			</select>  -->
			
			<!-- Puesto para no romper el controlador, aunque no tiene efecto -->
			<input type="hidden" name="tipoOperacion" value="Pagar" style="display:none">
			
			<br/><input class="button" type="submit" id="submitBtn" value="Realizar Transaccion">
			
			</form>
			
		</div>
	</main>
<%	}
	
%>
</body>
</html>