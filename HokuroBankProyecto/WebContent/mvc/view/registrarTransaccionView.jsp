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
	ArrayList<CuentaBancariaDTO> ListaCuentas = new ArrayList<CuentaBancariaDTO>();
	ListaCuentas = infoCuentas.getCuentas();
%>

<main class="main">

	<div>
	
		<form method="post" action="RealizarTransaccion">
		
		<input type="text" name="idCuentaDestino" required>
		
		<select name="idCuentaOrigen" required>
			<%for(CuentaBancariaDTO cuenta : ListaCuentas){ %>
		  <option value="<%=cuenta.getIdCuentaBancaria()%>"><%=cuenta.getIdCuentaBancaria()%></option>
		  <%} %>
		</select>
		
		<input type="text" name="descripcion" required>
		
		<input type="text" name="cantidad" required>
	
		<select name="tipoOperacion" required>
		  <option value="pagar">Pagar</option>
		  <option value="recibir">Recibir</option>
		</select>
		
		</form>
		
	</div>
</main>
<%	}
	
%>
</body>
</html>