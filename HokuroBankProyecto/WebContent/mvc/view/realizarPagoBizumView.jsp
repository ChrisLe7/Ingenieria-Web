<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="infoCuentasUsuario" scope="session" class="es.uco.iw.display.InfoCuentasBancariasBean"></jsp:useBean>

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

nextPage = "";
mensajeNextPage = "";
if (clienteBean == null || clienteBean.getDni().equals(""))  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no está logueado";
	%>
	<jsp:forward page="<%=nextPage%>">
		<jsp:param value="<%=mensajeNextPage%>" name="message"/>
	</jsp:forward>
	<% 
}else{ 
	ArrayList<CuentaBancariaDTO> ListaCuentas = new ArrayList<CuentaBancariaDTO>();

	
		ListaCuentas = infoCuentasUsuario.getCuentas();
		System.out.println("infoUsuario -> " + ListaCuentas.isEmpty());
	
	%>

	<main class="main">
	
		<div>
		
			<form class="formularioTransaccion" method="post" action="RealizarTransferenciaBizum">
			
				<label for="IdTelefonoDestino">Telefono del destinatario del Bizum: </label> <br/>
					
				<input type="text" name="idTelefonoDestino" pattern="[0-9]{9}" required> <br/>
				
				<label for="idCuentaOrigen">Id de la cuenta Origen: </label> <br/>		
					
				<select class="select" name="idCuentaOrigen" required>
					<%for(CuentaBancariaDTO cuenta : ListaCuentas){ %>
					 	 <option value="<%=cuenta.getTelefonoBizum()%>"><%=cuenta.getTelefonoBizum()%></option>
					<%} %>
				</select> <br/>
				
				<label for="Descripcion">Descripción: </label> <br/>		 
					
				<input type="text" name="descripcion" required> <br/>
				
				<label for="Cantidad">Cantidad: </label> <br/>	
					
				<input type="text" name="cantidad" pattern="[0-9]{+}" required> <br/>
			
				<!-- <label for="TipoOperacion">Tipo de Operacion: </label>	 -->
				
				<!-- <select name="tipoOperacion" required>
				  <option value=<%=TipoOperacion.Pagar %>>Pagar</option>
				  <option value=<%=TipoOperacion.Recibir %>>Recibir</option>
				</select>  -->
				
				<!-- Puesto para no romper el controlador, aunque no tiene efecto -->
				<input type="hidden" name="tipoOperacion" value="Pagar" style="display:none">
				
				<input class="button" type="submit" id="submitBtn" value="Realizar Transaccion">
			
			</form>
			
		</div>
	</main>
<%	}
	
%>
</body>
</html>
