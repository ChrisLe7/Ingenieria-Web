<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO, es.uco.iw.negocio.tarjeta.TipoTarjeta" %>
<%@ page import ="es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO, es.uco.iw.negocio.usuario.PropiedadCuenta, es.uco.iw.negocio.cuentaBancaria.TipoCuentaBancaria" %>
<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>
    
    <jsp:useBean  id="listadoClientes" scope="session" class="es.uco.iw.display.ListadoClientesBean"></jsp:useBean> 
    <jsp:useBean  id="infoCuentas" scope="session" class="es.uco.iw.display.InfoCuentasBancariasBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestionar Cotitulares</title>
</head>
<body>
	<%@ include file="/include/header.jsp" %>
<%@ include file="/include/Menu.jsp" %>
	<!-- Incluimos la cabecera de la aplicación -->
	

<%  

if (logged == false)  {
	nextPage = "index.jsp";
	mensajeNextPage = "Usted no esta logueado";
	%>
	<jsp:forward page="<%=nextPage%>">
		<jsp:param value="<%=mensajeNextPage%>" name="message"/>
	</jsp:forward>
	<% 
}else {

	CuentaBancariaDTO cuenta = infoCuentas.get(0);
	%>
		<main class = "main">
			
			<div class="Cuenta">
					Numero de Cuenta  <%=cuenta.getIdCuentaBancaria()%><br/>
					Saldo: <%=cuenta.getSaldo()%><br/>
					Tipo: <%=cuenta.getTipoCuentaBancaria().toString()%><br/>
					Estado del Bizum: 
					<%if(cuenta.getEstadoBizum()){ 
						%> Activo <% } 
					else {
				%> No Activo <%	}%>
					<br/>
					Titular <%=cuenta.getIdTitular()%><br/>
					CoTitular <%=cuenta.getIdCotitular()%><br/>
					<% if (!cuenta.getIdCotitular().equals("")) {%>
						<form method="post" action="EliminarCotitular">
							<input type="hidden" name="idCoTitular" value="<%=cuenta.getIdCotitular()%>">
							<input type="hidden" name="idCuenta" value="<%=cuenta.getIdCuentaBancaria()%>">
							<input type="submit" value = "Eliminar Cotitular"> 
						</form>
					<%}%>
			
				<form method="post" action="ActualizarTitular">
						
			
					<%if(clienteBean.getRol().equals(RolUsuario.Administrador)){ 
						
				
					 ArrayList<UsuarioDTO> listaCuentas = listadoClientes.getUsuarios();
						if (listaCuentas == null) {
							System.out.println("ES NULO");
						}  
						
						if (listaCuentas.isEmpty()){
							System.out.println("Lista Vacia");
						}
					 
					 %>
					
					
					<label>CoTitular: </label>
						
						<select id="idCoTitular" name="idCoTitular" required> 
						
							<% for(UsuarioDTO user : listaCuentas){%>
			
							<option value="<%=user.getDni()%>"><%=user.getDni()%></option>
						
							<%} %>
						
						</select>
						
					<%}else{ %>
						
						<input type="text" name="idCoTitular" value = "<%=clienteBean.getDni()%>" style="display:none"> <br/>	
						
					<%} %>
						
					<input type="submit" value = "Confirmar"> 
					
				</form>
			</div>
		</main>


<% 	
}
%>
</body>
</html>