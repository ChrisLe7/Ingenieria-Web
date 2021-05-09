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
import es.uco.iw.display.UsuarioInfoBean;
import es.uco.iw.negocio.usuario.UsuarioDTO;

/**
 * Servlet implementation class PerfilUserController
 */
public class PerfilUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilUserController() {
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
		Boolean login = cliente != null && !cliente.getDni().equals("");
		RequestDispatcher disparador;
		String nextPage ="/mvc/view/modificarUsuarioView"; 
		if (login) {
			String UserCorreo = request.getParameter("correo");
			UsuarioDTO userDTO = null;
			if (UserCorreo != null) {
				//Significa que tenemos cambios y venimos del formulario
				String UserTelefono = request.getParameter("telefono");
				String UserDireccion = request.getParameter("direccion");
				String UserPassword = request.getParameter("password");
				
				
				userDTO = userDAO.QueryByDni(cliente.getDni());
				
				userDTO.setDireccion(UserDireccion);
				userDTO.setPassword(UserPassword);
				userDTO.setTelefono(Integer.valueOf(UserTelefono));
				userDTO.setEmail(UserCorreo);

				userDAO.Update(userDTO);
				nextPage = "/Home";
				disparador = request.getRequestDispatcher(nextPage);
				
				
			}else {
				//Tenemos que direccionar al formulario pero antes deberemos de coger la información del usuario
				
				userDTO = userDAO.QueryByDni(cliente.getDni());
				UsuarioInfoBean clienteInfo = new UsuarioInfoBean ();
				clienteInfo.setUsuario(userDTO);

				session.setAttribute("infoClienteBean", clienteInfo);

				nextPage ="/mvc/view/modificarUsuarioView";
				disparador = request.getRequestDispatcher(nextPage);
				String mensajeNextPage = "";
				request.setAttribute("mensaje", mensajeNextPage);
			}
			
			
			
			
		}
		else {
			disparador = request.getRequestDispatcher("/Login");
			String mensajeNextPage = "No se encuentra logueado, inicie sesión";
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
