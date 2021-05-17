package es.uco.iw.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
import es.uco.iw.display.ListadoClientesBean;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.usuario.UsuarioDTO;

/**
 * Servlet implementation class GestionarTitularesCuentaBancariaController
 */

public class GestionarTitularesCuentaBancariaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionarTitularesCuentaBancariaController() {
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
			
			String idCuenta = request.getParameter("idCuenta");
			
			if (idCuenta != null) {
				String idCoTitular = request.getParameter("idCoTitular");
				
				InfoCuentasBancariasBean infoCuenta = null;
				CuentaBancariaDTO cuenta = null;
				if (idCoTitular == null) {
					//Dirijo a la vista
					
					nextPage = "/mvc/view/gestionarTitularesCuentaView.jsp";
					infoCuenta = new InfoCuentasBancariasBean(); 
					
					cuenta = cuentaUserDAO.QueryByIdCuentaBancaria(idCuenta);
					
					ListadoClientesBean listadoClientes = new ListadoClientesBean ();
					
					ArrayList<UsuarioDTO> clientes = userDAO.QueryByCuentasBancarias();
					
					for (int i = 0; i < clientes.size(); i++) {
						if (clientes.get(i).getDni().equals(cuenta.getIdTitular())) {
							clientes.remove(i);
							break;
						}
					}
					listadoClientes.setUsuarios(clientes);
					if (clientes.isEmpty()) {
						System.out.println("VAICI");
					}
					ArrayList<CuentaBancariaDTO> aux = new ArrayList<CuentaBancariaDTO> ();
					aux.add(cuenta);
					System.out.println(aux.size() + "Tamaño");
					infoCuenta.setCuentas(aux);
					
					session.setAttribute("listadoClientes", listadoClientes);
					session.setAttribute("infoCuentas", infoCuenta);
					System.out.println("IR A LA VISTA");
					if (infoCuenta.getCuentas()== null) {
						System.out.println("ESTA VACIO 1");
					}
					
				}
				else {
					//Vengo de la vista realizo la modificación y mando al gestionar cuentas
					infoCuenta = (InfoCuentasBancariasBean) session.getAttribute("infoCuentas");
					
					cuenta = infoCuenta.get(0);
					
					if (cuenta.getIdCotitular().equals("")) {
						cuenta.setIdCotitular(idCoTitular);
						cuentaUserDAO.InsertCotitular(cuenta);
						mensajeNextPage = "Se ha agregado el Cotitular " + idCoTitular + " a la cuenta " + idCuenta;

					}
					else {
						cuenta.setIdCotitular(idCoTitular);
						cuentaUserDAO.UpdateCotitular(cuenta);
						mensajeNextPage = "Se ha actualizado al cotitular " + idCoTitular + " para la cuenta " + idCuenta;
						
					}
					nextPage = "Home";
					request.setAttribute("mensaje", mensajeNextPage);
					session.removeAttribute("listadoClientes");
					session.removeAttribute("infoCuentas");
				}
			} 
			else { 
				nextPage = "index.jsp";
				mensajeNextPage = "Acceso no autorizado";
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
