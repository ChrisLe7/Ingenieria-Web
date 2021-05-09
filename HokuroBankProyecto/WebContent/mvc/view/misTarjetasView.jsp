<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import ="java.util.ArrayList,es.uco.iw.negocio.tarjeta.TarjetaDTO" %>

<jsp:useBean  id="clienteBean" scope="session" class="es.uco.iw.display.ClienteBean"></jsp:useBean>  

<jsp:useBean  id="infoTarjetas" scope="session" class="es.uco.iw.display.InfoTarjetasBean"></jsp:useBean>  


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestionar Tarjetas</title>
</head>
<body>

	<table class="myadverts">
			<tr>
				<th>ID Anuncio</th>
				<th>Titulo Anuncio</th>
				<th>Tipo Anuncio</th>
				<th>Estado</th>
			</tr>
	<% //Comprobar que se encuentra logueado.
		
		Boolean login = clienteBean != null && !clienteBean.getDni().equals("");
		if (login){
			if (infoTarjetas != null) {
				//El usuario no tiene tarjetas mostrar algun error o alguna forma de contratarlas. Redirreccionar a crearTarjeta
				
			}
			else {
				ArrayList<TarjetaDTO> misTarjetas = infoTarjetas.getTarjetas();
				if (misTarjetas.isEmpty()){
					for (int  i = 0; i < misTarjetas.size(); i++) {
						%>
						<tr class="tarjeta">
								<td><%= misTarjetas.get(i).getNumTarjeta()%></td>
								<td><%= misTarjetas.get(i).getTipotarjeta()%></td>
								
								<td><%= misTarjetas.get(i).getIdCliente()%></td>
								<td><%= misTarjetas.get(i).getIdCuenta()%></td>
								<td class="BotonCancelar">
									<form method=post action="CancelarTarjeta">
										<input type=text name="id-advert" class="hidden" value=<%=misTarjetas.get(i).getNumTarjeta()%>>
										<input type=submit value=Cancelar> 
									</form>
								</td>
						</tr>
						<% 
							
					}
				}
			}
			
		}
	
	
	%>
	</table>
</body>
</html>