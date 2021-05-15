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
import es.uco.iw.negocio.usuario.UsuarioDTO;
import es.uco.iw.negocio.usuario.UsuarioLoginDTO;
import es.uco.iw.utilidades.HashPassword;

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
		RequestDispatcher disparador = null;
		String nextPage ="/mvc/view/loginView"; 
		if (!login) {
			
			String UserDNI = request.getParameter("DNI");
			String UserPassword = request.getParameter("Password");

			if (UserDNI != null) {
				
				
				UsuarioLoginDTO userDTO = userDAO.QueryByPassword(UserDNI);
				
				String saltContraseña = userDTO.getSalt();
				
				String passwordHash = HashPassword.createHash(UserPassword, saltContraseña);
				
				
				
				if (passwordHash.equals(userDTO.getPassword())) {
					cliente = new ClienteBean ();
					cliente.setDni(UserDNI);
					cliente.setRol(userDTO.getRol());
					session.setAttribute("clienteBean", cliente);
					nextPage = "/Home";
					disparador = request.getRequestDispatcher(nextPage);
				}
				else {
					nextPage = "/mvc/view/loginView.jsp";
					System.out.println("Contraseña Errorne");
					disparador = request.getRequestDispatcher(nextPage);
					String mensajeNextPage = "Error de Contraseña, Intentelo de Nuevo";
					request.setAttribute("mensaje", mensajeNextPage);
				}
			}
			else {
				nextPage = "/mvc/view/loginView.jsp";
				disparador = request.getRequestDispatcher(nextPage);
				String mensajeNextPage = "Accede a HokuroBank con tu DNI y Clave.";
				request.setAttribute("mensaje", mensajeNextPage);
			}
		}
		else {
			//Se encuentra logueado deberá de acceder al Home.
			//nextPage = "index.jsp";
			//disparador = request.getRequestDispatcher(nextPage);
			
			request.getSession().removeAttribute("clienteBean");
			request.getSession().removeAttribute("infoTarjetas");
			request.getSession().removeAttribute("infoCuentas");
					
			
			nextPage = "/index.jsp";
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
