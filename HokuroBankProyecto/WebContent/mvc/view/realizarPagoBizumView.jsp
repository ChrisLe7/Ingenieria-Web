<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>
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

<%  
boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
String nextPage = "";
System.out.println("Estoy en la vista");
String mensajeNextPage = "";
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
		
			<form method="post" action="RealizarTransferenciaBizum">
			
				<label for="IdTelefonoDestino">Telefono del destinatario del Bizum: </label> <br/>
					
				<input type="text" name="idTelefonoDestino" pattern="[0-9]{9}" required> <br/>
				
				<label for="idCuentaOrigen">Id de la cuenta Origen: </label> <br/>		
					
				<select name="idCuentaOrigen" required>
					<%for(PropiedadCuenta cuenta : ListaCuentas){ %>
					 	 <option value="<%=cuenta.getIdCuentaBancaria()%>"><%=cuenta.getIdCuentaBancaria()%></option>
					<%} %>
				</select> <br/>
				
				<label for="Descripcion">Descripción: </label> <br/>		 
					
				<input type="text" name="descripcion" required> <br/>
				
				<label for="Cantidad">Cantidad: </label> <br/>	
					
				<input type="text" name="cantidad" pattern="[0-9]{+}" required> <br/>
			
				<label for="TipoOperacion">Tipo de Operacion: </label> <br/>	
					
				<select name="tipoOperacion" required>
				  <option value=<%=TipoOperacion.Pagar %>>Pagar</option>
				  <option value=<%=TipoOperacion.Recibir %>>Recibir</option>
				</select> <br/>
				
				<input class="button" type="submit" id="submitBtn" value="Realizar Transaccion">
			
			</form>
			
		</div>
	</main>
<%	}
	
%>
</body>
</html>
