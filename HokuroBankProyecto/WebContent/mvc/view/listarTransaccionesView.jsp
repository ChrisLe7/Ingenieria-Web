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
	
%> 
<main class="main">
<p>
AUN NO HA REALIZADO NINGUNA TRANSACCION
</p>

<% }else{ 
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
	
}%>
	</main>
</body>
</html>
