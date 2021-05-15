package es.uco.iw.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.display.ClienteBean;
import es.uco.iw.negocio.usuario.UsuarioLoginDTO;
import es.uco.iw.utilidades.EnvioCorreo;
import es.uco.iw.utilidades.HashPassword;

/**
 * Servlet implementation class ResetPasswordController
 */

public class ResetPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPasswordController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String port = request.getServletContext().getInitParameter("port");
		String username_bd = request.getServletContext().getInitParameter("username");
		String password_bd = request.getServletContext().getInitParameter("password");
		String server = request.getServletContext().getInitParameter("server");
		String dbURL = request.getServletContext().getInitParameter("dbURL");
		String bdName = request.getServletContext().getInitParameter("bdName");
		
		dbURL= dbURL + server + ":" + port + "/" + bdName; 
		String sql = request.getServletContext().getInitParameter("sql");
		
		ServletContext application = getServletContext();
		InputStream myIO = application.getResourceAsStream(sql);
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);
		
		ClienteBean cliente = (ClienteBean) session.getAttribute("clienteBean");
		UsuarioDAO userDAO = new UsuarioDAO (dbURL, username_bd, password_bd, prop);
		Boolean login = cliente != null && !cliente.getDni().equals("");
		RequestDispatcher disparador;
		String nextPage ="/mvc/view/resetPasswordView.jsp"; 
		String mensajeNextPage = "";
		if (!login) {
			//No se encuentra logueado se debe de ir al login.
			nextPage = "Login";
			mensajeNextPage = "No se encuentra logueado, inicie sesión";
			request.setAttribute("mensaje", mensajeNextPage);
		}
		else {
			String UserEmail = request.getParameter("email");
			String idUserCliente = "";
			if (UserEmail != null) {
				//Significa estamos logueado y queremos resetear la contraseña de alguien
				
				idUserCliente = request.getParameter("idCliente"); //ARGUMENTO REQUERIDO EN LA VISTA
				if (idUserCliente == null) {
					idUserCliente = cliente.getDni();
				}

				String newPassword = request.getParameter("prehashPassword");
				String newPasswordHasheada = request.getParameter("password");
				
				UsuarioLoginDTO userLoginDTO = userDAO.QueryByPassword(idUserCliente);
				
				if (userLoginDTO == null) {
					mensajeNextPage ="Lo sentimos pero el Cliente con ID: " + idUserCliente + " no existe";
					nextPage = "Home";
				}
				else {
					String passwordHash = HashPassword.createHash(newPasswordHasheada, userLoginDTO.getSalt());
					userLoginDTO.setPassword(passwordHash);
					
					userDAO.UpdatePasswordPorReseteo(userLoginDTO);
					
					//AHORA DEBERÍA DE ENVIAR EL CORREO 
					String asunto = "Reseteo de Contraseña para el usuario" + idUserCliente ;
					String mensaje = "Su contraseña se ha reseteado de forma correcta, su contraseña nueva será: " + newPassword;
					EnvioCorreo.EnviarCorreo(UserEmail, asunto, mensaje);
					nextPage = "Home";
					mensajeNextPage = "La contraseña del usuario "+ idUserCliente +" se ha reseteado de forma correcta. Es: "+ newPassword;
				}
			}
			else {
				//Dirigimos a la vista
				nextPage = "/mvc/view/resetPasswordView.jsp";
			}
	
		}
		
		disparador = request.getRequestDispatcher(nextPage);
		disparador.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
