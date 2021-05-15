<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<%@ page import ="es.uco.iw.negocio.usuario.RolUsuario" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
</head>


<body>
	<%@ include file="/include/header.jsp" %>
	<%@ include file="/include/Menu.jsp" %>
		<% 
		System.out.println(clienteBean);
		System.out.println(clienteBean.getDni());
		
		logged = clienteBean != null && !clienteBean.getDni().equals("");
		mensajeNextPage = (String)request.getAttribute("mensaje");
		nextPage = "/mvc/view/MainMenu.jsp";
		if (mensajeNextPage == null) mensajeNextPage = "";	
		
		
		if (logged)  {
			/* Significa que el usuario se encuentra logueado. */
			
			/*Opciones del menú*/
			
			%>

					
<main class="main">

<%= mensajeNextPage %>
</main>

	
				
	
		<% 	
		}
		else {
			//No se encuentra logueado deberá de irse al controlador de login.
			nextPage = "/mvc/view/MainMenu.jsp";
			mensajeNextPage = "Debera de Iniciar Sesion para acceder al Sistema";
		%>
		<jsp:forward page="<%=nextPage%>">
			<jsp:param value="<%=mensajeNextPage%>" name="message"/>
		</jsp:forward>

		<%} %>
		
	<main class="main">


	
	


</main>

</body>
</html>
