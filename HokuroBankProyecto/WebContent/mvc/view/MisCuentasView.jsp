<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO" %>

<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>  

<jsp:useBean  id="infoCuentas" scope="session" class="es.uco.iw.display.InfoCuentasBancariasBean"></jsp:useBean>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<% //Comprobar que se encuentra logueado.
		String nextPage = "";
		String mensajeNextPage ="";
		Boolean login = clienteBean != null && !clienteBean.getDni().equals("");
		if (login){
			%>
			<table class="myadverts">
			<tr>
				<th>ID Cuenta</th>
				<th>Propietario</th>
				<th>Copropietario</th>
				<th>Saldo</th>
			</tr>
			<% 
			if (infoCuentas != null) {
				ArrayList<CuentaBancariaDTO> misCuentas = infoCuentas.getCuentas();
				
				if (misCuentas != null && misCuentas.isEmpty()) {
					//Poseemos Tarjetas
					for (int  i = 0; i < misCuentas.size(); i++) {
						%>
						<tr class="tarjeta">
								<td><%= misCuentas.get(i).getIdCuentaBancaria()%></td>
								<td><%= misCuentas.get(i).getIdTitular()%></td>
								
								<td><%= misCuentas.get(i).getIdCotitular()%></td>
								<td><%= misCuentas.get(i).getSaldo()%></td>
								<td class="BotonCancelar">
									<form method=post action="CancelarTarjeta">
										<input type=text name="id-cuenta" class="hidden" value=<%=misCuentas.get(i).getIdCuentaBancaria()%>>
										<input type=submit value=Cancelar> 
									</form>
								</td>
								<% 
								if (misCuentas.get(i).getEstadoBizum() == true) {
								%>	<td class="BotonCancelarBizum">
									<form method=post action="GestionarBizum">
										<input type=text name="id-cuenta" class="hidden" value=<%=misCuentas.get(i).getIdCuentaBancaria()%>>
										<input type=submit value=Cancelar> 
									</form>
								</td>
								<% } else{
									
									
								%>
								<td class="BotonHabilitarBizum">
									<form method=post action="GestionarBizum">
										<input type=text name="id-cuenta" class="hidden" value=<%=misCuentas.get(i).getIdCuentaBancaria()%>>
										<input type=submit value=Habilitar> 
									</form>
								</td>
								
								
								 <%	
								}
								
								%>
						</tr>
						<% 
							
					}
				}
				else {
					//No posee Tarjetas -> No se que mostrar
					%> 
					<tr>
						<td class="ContratarCuenta">
									<form method=post action="ContratarCuenta">
										<input type=text name="cuenta" class="hidden" value="">
										<input type=submit value="Contratar Cuenta Nueva"> 
									</form>
								</td>
					</tr>
					<% 
				}
			}
			%>
			</table>
	
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
