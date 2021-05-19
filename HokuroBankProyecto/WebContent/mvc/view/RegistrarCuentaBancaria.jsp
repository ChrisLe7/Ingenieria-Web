<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO, es.uco.iw.negocio.tarjeta.TipoTarjeta" %>
<%@ page import ="es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO, es.uco.iw.negocio.usuario.PropiedadCuenta, es.uco.iw.negocio.cuentaBancaria.TipoCuentaBancaria" %>
<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.iw.display.UsuarioInfoBean"></jsp:useBean>  
<jsp:useBean  id="listadoClientes" scope="session" class="es.uco.iw.display.ListadoClientesBean"></jsp:useBean> 


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrar Cuenta</title>
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
}else {%>
	
			
<main class = "main">
			
	<form class="formulario" method="post" action="RegistrarCuentaBancaria">
			

		<label for="Tipo">Tipo de Cuenta: </label>
		<select class="select" name="tipoCuenta" required>
						<option value="<%=TipoCuentaBancaria.Ahorro%>">Ahorro</option>
						<option value="<%=TipoCuentaBancaria.Corriente%>">Corriente</option>

		</select>
		
		
		<%if(clienteBean.getRol().equals(RolUsuario.Administrador)){ 
			
	
		 ArrayList<UsuarioDTO> listaCuentas = listadoClientes.getUsuarios();
		 
			if (listaCuentas.isEmpty()){
				System.out.println("Lista Vacia");
			}
		 
		 %>
		
		<br>
		<label>Titular: </label>
			
			<select id="idTitular" name="idTitular" required> 
			
				<% for(UsuarioDTO user : listaCuentas){%>

				<option value="<%=user.getDni()%>"><%=user.getDni()%></option>
			
				<%} %>
			
			</select>
			
		<%}else{ %>
			
			<input type="text" name="idTitular" value = "<%=clienteBean.getDni()%>" style="display:none"> <br/>	
			
		<%} %>
			
		<input type="submit" value = "Confirmar"> 
		
	</form>

</main>


<% 	
}
%>
</body>
</html>
