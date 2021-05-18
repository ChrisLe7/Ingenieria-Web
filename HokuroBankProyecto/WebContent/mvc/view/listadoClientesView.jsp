<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean  id="listadoClientes" scope="session" class="es.uco.iw.display.ListadoClientesBean"></jsp:useBean>

<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario, es.uco.iw.negocio.usuario.UsuarioDTO , es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>
<%@ page import ="java.util.ArrayList" %>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Clientes</title>
</head>
<body>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/Menu.jsp" %>

<main class="main">

	<%  

	if (clienteBean == null || clienteBean.getDni().equals("") || clienteBean.getRol().equals(RolUsuario.Cliente))  {
		nextPage = "index.jsp";
		mensajeNextPage = "Usted no está logueado";
		if (clienteBean.getRol().equals(RolUsuario.Cliente)) {
			mensajeNextPage = "Usted no tiene permiso para acceder a la gestión de Usuarios";
		}
		
	}else if(listadoClientes.getUsuarios().isEmpty()){
		
	%> 
	
	<p>
		Aun no hay clientes, registremos a nuestros clientes.
	</p>
	
	  <p>
		Crea una Cuenta de Usuario <a href="RegistrarUsuario">AQUÍ</a>
	</p>
	
	<% }else{ 
	ArrayList<UsuarioDTO> listaClientes = new ArrayList<UsuarioDTO>();
	listaClientes = listadoClientes.getUsuarios();
	
	%>
	
	<div class="CuentasUsuario">
		
		<%
		for(UsuarioDTO user : listaClientes){
		%>
		<div class="Cuenta">
			<form method="post" action="Perfil">
	
				<label for="DNI">DNI: <%=user.getDni()%></label> <br/>
				
				<label for="Nombre">Nombre: <%=user.getNombre()%></label> <br/>
				
				<label for="Apellidos">Apellidos: <%=user.getApellidos()%></label> <br/>
				
				<label for="Email">Email: <%=user.getEmail()%></label> <br/>
				
				<label for="Dirección">Dirección: <%=user.getDireccion()%></label> <br/>
				
				<label for="Teléfono">Teléfono: <%=user.getTelefono()%></label> <br/>		 

			</form>
			<form method=post action="CancelarCuentaUsuario">
				<input type="hidden" name="idCliente" value="<%=user.getDni()%>">
				<input type="submit" value="Cancelar Cuenta Usuario">
			</form>
		</div>
	<%	}
	}%>
	</div>
</main>


</body>
</html>