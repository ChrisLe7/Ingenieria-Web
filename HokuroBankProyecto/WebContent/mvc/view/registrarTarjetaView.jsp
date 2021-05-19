<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO, es.uco.iw.negocio.tarjeta.TipoTarjeta" %>
<%@ page import ="es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO, es.uco.iw.negocio.usuario.PropiedadCuenta" %>
<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>


<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.iw.display.UsuarioInfoBean"></jsp:useBean>  



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrar Tarjeta</title>
</head>
<body>
	<!-- Incluimos la cabecera de la aplicación -->
	



<% 	
	UsuarioDTO user = UsuarioInfoBean.getUsuario();
	
	ArrayList<PropiedadCuenta> listaCuentas = user.getCuentasBancarias();
	if (listaCuentas.isEmpty()) {
		String nextPage = "/index.jsp";
		String mensajeNextPage = "No posee cuentas, contrate una antes de solicitar una tarjeta";
	%>
		<jsp:forward page="<%=nextPage%>">
			<jsp:param value="<%=mensajeNextPage%>" name="message"/>
		</jsp:forward>
	<% 
	}
	
	else {%>	
	<%@ include file="/include/header.jsp" %>
	<%@ include file="/include/Menu.jsp" %>
	
	
	<main class = "main">
			
		<div>
			<form class="formularioTransaccion" method="post" action="RegistrarTarjeta" class = "creartarjeta">	
	
				<label for="Pin">Pin: </label>
				
				<input type="password" name="pin" pattern="[0-9]{4}" required><br/>
				
				<label for="Tipo">Tipo de Tarjeta: </label><br/>
					<select class="select" name="tipoTarjeta" required>
						<option value="Credito">Credito</option>
						<option value="Debito">Debito</option>

					</select>
					<br/> 
				<label for="Cuenta">Cuenta a la que enlazarla: </label> <br/>
				
					<select class="select" name="idCuenta" required>
						<%for(PropiedadCuenta cuenta : listaCuentas){ %>
						 	 <option value="<%=cuenta.getIdCuentaBancaria()%>"><%=cuenta.getIdCuentaBancaria()%></option>
						<%} %>
					</select>
						
					<br/>
				<br/><input type="submit" id="submitBtn" value="Crear Tarjeta">
			</form>		
		</div>



	</main>
 	<%}%>

</body>
</html>
