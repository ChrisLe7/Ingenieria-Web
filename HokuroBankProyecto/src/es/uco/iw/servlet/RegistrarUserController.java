package es.uco.iw.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.display.ClienteBean;
import es.uco.iw.negocio.usuario.RolUsuario;
import es.uco.iw.negocio.usuario.UsuarioDTO;
import es.uco.iw.negocio.usuario.UsuarioLoginDTO;
import es.uco.iw.utilidades.HashPassword;

/**
 * Servlet implementation class RegistrarUserController
 */

public class RegistrarUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrarUserController() {
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
		String nextPage ="/mvc/view/RegistrarUsuarioView.jsp"; 
		if (login && cliente != null && cliente.getRol().equals(RolUsuario.Administrador)) {
			String UserDNI = request.getParameter("DNI");
			
			if (UserDNI != null) {
				String UserNombre = request.getParameter("nombre");
				String UserApellidos = request.getParameter("apellidos");
				String UserEmail = request.getParameter("email");
				String UserDireccion = request.getParameter("direccion");
				String UserTelefono  = request.getParameter("telefono"); 
				String UserRol = request.getParameter("rol");
				String UserPassword = request.getParameter("password");
				
				UsuarioDTO userDTO = new UsuarioDTO (UserDNI);
				
				userDTO.setEmail(UserEmail);
				userDTO.setNombre(UserNombre);
				userDTO.setApellidos(UserApellidos);
				userDTO.setTelefono(Integer.valueOf(UserTelefono));
				userDTO.setDireccion(UserDireccion);
				
				
				
				String salt = HashPassword.createSalt();
				
				String passwordHash = HashPassword.createHash(UserPassword, salt);
				
				UsuarioLoginDTO userLoginDTO  = new UsuarioLoginDTO (UserDNI,passwordHash, UserRol);
				userLoginDTO.setSalt(salt);
				
				userDAO.Insert(userDTO, userLoginDTO);
				
				nextPage = "Home";
				disparador = request.getRequestDispatcher(nextPage);
				
			}
			else {
				// Se debe de ir a la vista
				if (cliente != null && cliente.getRol().equals(RolUsuario.Administrador)) {
					nextPage = "/mvc/view/RegistrarUsuarioView.jsp"; 
					disparador = request.getRequestDispatcher(nextPage);
				}
				
			}
			
		}
		else {
			disparador = request.getRequestDispatcher("/Home");
			String mensajeNextPage = "No es administrador, falta de permisos";
			request.setAttribute("mensaje", mensajeNextPage);
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
