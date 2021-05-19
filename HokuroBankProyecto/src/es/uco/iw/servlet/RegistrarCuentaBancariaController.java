package es.uco.iw.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.uco.iw.datos.CuentaBancariaDAO;
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.display.ClienteBean;
import es.uco.iw.display.ListadoClientesBean;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.cuentaBancaria.TipoCuentaBancaria;
import es.uco.iw.negocio.usuario.RolUsuario;
import es.uco.iw.negocio.usuario.UsuarioDTO;
import es.uco.iw.utilidades.GeneradorID;

/**
 * Servlet implementation class RegistrarCuentaBancariaController
 */

public class RegistrarCuentaBancariaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrarCuentaBancariaController() {
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
		
		UsuarioDAO userDAO = new UsuarioDAO (dbURL, username_bd, password_bd, prop);
		CuentaBancariaDAO cuentaUserDAO = new CuentaBancariaDAO (dbURL, username_bd, password_bd, prop);
		ClienteBean cliente = (ClienteBean) session.getAttribute("clienteBean");
		
		
		
		RequestDispatcher disparador = null;
		String nextPage = null;
		
		String tipoCuenta = request.getParameter("tipoCuenta");
		Boolean login = cliente != null && !cliente.getDni().equals("");
		if (login && cliente != null && cliente.getRol().equals(RolUsuario.Administrador)) {
			
			if (tipoCuenta != null) {
				//Significa que venimos de la vista y deberemos de crear la nueva cuenta.
				String idTitular = request.getParameter("idTitular");
				String idCuenta = GeneradorID.GenerarIBAN();
				boolean condicion = false;
				while (!condicion) {
					if (cuentaUserDAO.QueryByIdCuentaBancaria(idCuenta) != null) {
						idCuenta = GeneradorID.GenerarIBAN();
					}
					else {
						condicion = true;
					}
				}
				float saldo = 0;
				boolean estadoBizum = false;
				
				CuentaBancariaDTO nuevaCuenta = new CuentaBancariaDTO(idCuenta, saldo, TipoCuentaBancaria.valueOf(tipoCuenta), estadoBizum, idTitular);
				UsuarioDTO usuario = new UsuarioDTO (idTitular);
				
				cuentaUserDAO.Insert(nuevaCuenta, usuario);
				request.getSession().removeAttribute("listadoClientes");
				nextPage = "Home";
				
			}
			else {
				
				ListadoClientesBean listadoClientes = new ListadoClientesBean ();
				
				// QUERY PARA OBTENER EL LISTADO DE USUARIOS COMO DTO's
				ArrayList <UsuarioDTO> listadoUsuarios = userDAO.QueryUsuarios();
				listadoClientes.setUsuarios(listadoUsuarios);
				
				request.getSession().setAttribute("listadoClientes", listadoClientes);
				nextPage = "/mvc/view/RegistrarCuentaBancaria.jsp";
			
			}
		
		}
		else {
			disparador = request.getRequestDispatcher("/Home");
			String mensajeNextPage = "No es administrador, falta de permisos";
			request.setAttribute("mensaje", mensajeNextPage);
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
