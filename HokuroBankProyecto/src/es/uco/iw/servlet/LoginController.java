package es.uco.iw.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.negocio.usuario.UsuarioDTO;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		String port = request.getServletContext().getInitParameter("port");
		String username_bd = request.getServletContext().getInitParameter("username");
		String password_bd = request.getServletContext().getInitParameter("password");
		String server = request.getServletContext().getInitParameter("server");
		String dbURL = request.getServletContext().getInitParameter("dbURL");
		dbURL= dbURL + server + ":" + port + "/" + username_bd; 
		String sql = request.getServletContext().getInitParameter("sql");
		
		ServletContext application = getServletContext();
		InputStream myIO = application.getResourceAsStream(sql);
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);
		
		ClienteBean cliente = (ClienteBean) session.getAttribute("clienteBean");
		UsuarioDAO userDAO = new UsuarioDAO (dbURL, username_bd, password_bd, prop);
		Boolean login = cliente != null && !cliente.getDNI().equals("");
		RequestDispatcher disparador;
		String nextPage ="/mvc/view/loginView"; 
		if (!login) {
			
			String UserDNI = request.getParameter("DNI");
			String UserPassword = request.getParameter("Password");

			if (UserDNI != null) {
				UsuarioDTO userDTO = userDAO.QueryByPassword(UserDNI);
				
				if (UserPassword.equals(userDTO.getPassword())) {
					cliente.setDNI(UserDNI);
					session.setAttribute("clienteBean", cliente);
					nextPage = "/Home";
					disparador = request.getRequestDispatcher(nextPage);
				}
				else {
					disparador = request.getRequestDispatcher(nextPage);
					String mensajeNextPage = "Error de Contraseña, Intentelo de Nuevo";
					request.setAttribute("mensaje", mensajeNextPage);
				}
			}
			else {
				disparador = request.getRequestDispatcher(nextPage);
				String mensajeNextPage = "No se encuentra logueado, debe de iniciar sesión";
				request.setAttribute("mensaje", mensajeNextPage);
			}
		}
		else {
			//Se encuentra logueado deberá de acceder al Home.
			nextPage = "/Home";
			disparador = request.getRequestDispatcher(nextPage);
			
			
		}
		
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
