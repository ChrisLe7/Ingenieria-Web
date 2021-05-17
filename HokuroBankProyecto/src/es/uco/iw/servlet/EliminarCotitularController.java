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

import es.uco.iw.datos.CuentaBancariaDAO;
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.display.ClienteBean;
import es.uco.iw.display.InfoCuentasBancariasBean;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.usuario.RolUsuario;


/**
 * Servlet implementation class EliminarCotitularController
 */
@WebServlet("/EliminarCotitularController")
public class EliminarCotitularController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarCotitularController() {
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
		CuentaBancariaDAO cuentaUserDAO = new CuentaBancariaDAO (dbURL, username_bd, password_bd, prop);
		String nextPage ="/mvc/view/loginView"; 
		String mensajeNextPage = "";
		RequestDispatcher disparador = null;
		Boolean login = cliente != null && !cliente.getDni().equals("");
		
		if (!login) {
			//No se encuentra logueado se debe de ir al login.
			nextPage = "Login";
			mensajeNextPage = "No se encuentra logueado, inicie sesion";
			request.setAttribute("mensaje", mensajeNextPage);
		}	
		
		else {
			
			if(cliente.getRol().equals(RolUsuario.Administrador)){ 
			
				String idCuenta = request.getParameter("idCuenta");
				String idCoTitular = request.getParameter("idCoTitular");
				InfoCuentasBancariasBean infoCuenta = null;
				CuentaBancariaDTO cuenta = null;
				infoCuenta = (InfoCuentasBancariasBean) session.getAttribute("infoCuentas");
				
				cuenta = infoCuenta.get(0);
				if (idCuenta != null && idCoTitular != null) {
					cuentaUserDAO.DeleteCotitular(cuenta);
				}
				mensajeNextPage = "Se ha eliminado con exito, al cotitular " + idCoTitular + " de la cuenta " + idCuenta;
				request.setAttribute("mensaje", mensajeNextPage);
				request.getSession().removeAttribute("listadoClientes"); 
				request.getSession().removeAttribute("infoCuentas");
				nextPage = "Home";
			}
			else {
				nextPage = "Home";
				mensajeNextPage = "No es admin, acceso no autorizado";
				request.setAttribute("mensaje", mensajeNextPage);
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
