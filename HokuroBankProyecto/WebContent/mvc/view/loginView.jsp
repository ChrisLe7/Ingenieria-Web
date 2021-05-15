<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/Login.css"> 
    <header class="header">
	<div class="container logo-nav-container">
		<a href="index.jsp" class="logo">HokuroBank</a>
	</div>
</header>
    <script src="js/sha512.js" defer></script>
    <script src="js/hashPassword.js" defer></script>
    <script src="js/login.js" defer></script>
    <title>Login</title>




<body>

<%


String nextPage = "Login";

String mensajeNextPage = (String)request.getAttribute("mensaje");

if (mensajeNextPage == null) mensajeNextPage = "";	

%> 

<div id="warp">
Monotone Admin Login CSS
  <form  method = "post" action="Login" id="formularioUsuario">
    	<div class="admin">
			      <div class="rota">
				        <h1>HOKURO</h1>
        				<input id="dni" type="text" name="DNI" placeholder="DNI" required/><br/>
				        <input id="password" type="password" name="Password" placeholder="Contraseña" required/>
      			</div>
    		</div>
    		<div class="cms">
      			<div class="roti">
			        	<h1>BANK</h1>
				        <input class="elsubmit" type="submit" id="submitBtn" value="Iniciar Sesión"><br/>
				        <p><a href="index.jsp">Ayuda</a> </p>
      </div>
    		</div>

  	</form>
  	    	
</div>

<div>

    		<h2>⠀⠀</h2><br/><br/>
    		</div><%= mensajeNextPage %>
</body>
</html>
