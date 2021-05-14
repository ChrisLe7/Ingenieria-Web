<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO" %>
 
<jsp:useBean  id="infoTarjetas" scope="session" class="es.uco.iw.display.InfoTarjetasBean"></jsp:useBean>  


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestionar Tarjetas</title>
</head>
<body>
<%@ include file="/include/Menu.jsp" %>
<%@ include file="/include/header.jsp" %>
	<% //Comprobar que se encuentra logueado.
		System.out.println("Estoy en la vista de misTarjeta");
		Boolean login = clienteBean != null && !clienteBean.getDni().equals("");
		if (login){
			%>
			<main class="main">
			<table class="myadverts">
			<tr>
				<th>Número de Tarjeta</th>
				<th>Tipo de Tarjeta</th>
				<th>Propietario</th>
				<th>Cuenta Bancaria Vinculada</th>
			</tr>
			<% 
			if (infoTarjetas != null) {
				System.out.println(infoTarjetas);
				ArrayList<TarjetaDTO> misTarjetas = infoTarjetas.getTarjetas();
				
				if (misTarjetas != null && misTarjetas.isEmpty()) {
					System.out.println("Tenemos tarjetas para el usuario");
					//Poseemos Tarjetas
					for (int  i = 0; i < misTarjetas.size(); i++) {
						%>
						<tr class="tarjeta">
								<td><%= misTarjetas.get(i).getNumTarjeta()%></td>
								<td><%= misTarjetas.get(i).getTipotarjeta()%></td>
								
								<td><%= misTarjetas.get(i).getIdCliente()%></td>
								<td><%= misTarjetas.get(i).getIdCuenta()%></td>
								<td class="BotonCancelar">
									<form method=post action="CancelarTarjeta">
										<input type=text name="id-tarjeta" class="hidden" value=<%=misTarjetas.get(i).getNumTarjeta()%>>
										<input type=submit value=Cancelar> 
									</form>
								</td>
						</tr>
						<% 
							
					}
				}
				else {
					//No posee Tarjetas -> No se que mostrar
					%> 
					<tr>
						<td class="ContratarTarjeta">
									<form method=post action="ContratarTarjeta">
										<input type=text name="cuenta" class="hidden" value="">
										<input type=submit value="Contratar Tarjeta Nueva"> 
									</form>
								</td>
					</tr>
					<% 
				}
			}
			%>
			</table>
		</main>
			<%
		}else {
			//No esta logueado Direccionador al Login
			nextPage = "Login";
			mensajeNextPage = "No esta logueado, falta de permisos";
			%>
			<jsp:forward page="<%=nextPage%>">
				<jsp:param value="<%=mensajeNextPage%>" name="message"/>
			</jsp:forward>
			<%
		}
	
	
	%>
	
</body>
</html>
