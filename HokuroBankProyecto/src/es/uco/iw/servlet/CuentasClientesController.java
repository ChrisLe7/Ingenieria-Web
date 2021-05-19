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

import es.uco.iw.datos.CuentaBancariaDAO;
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.display.ListadoClientesBean;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.usuario.PropiedadCuenta;
import es.uco.iw.negocio.usuario.RolPropietario;
import es.uco.iw.negocio.usuario.UsuarioDTO;

/**
 * Servlet implementation class CuentasClientesController
 */
public class CuentasClientesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CuentasClientesController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		RequestDispatcher disparador = null;
		String nextPage ="/mvc/view/listadoClientesView.jsp"; 
		String idCliente = request.getParameter("idCliente");
		String mensaje = "";
		if (idCliente != null) {
			//Deberemos de cancelar las cuentas de los usuarios
			
			UsuarioDTO clienteDTO = userDAO.QueryByDni(idCliente);
			ArrayList<PropiedadCuenta> cuentas = clienteDTO.getCuentasBancarias();
			float cantidadDevolver = 0;
			CuentaBancariaDTO cuentaAux = null;
			for (int i = 0 ; i < cuentas.size(); i++) {
				if (cuentas.get(i).getRol().equals(RolPropietario.Titular)) {
					cuentaAux = cuentaUserDAO.QueryByIdCuentaBancaria(cuentas.get(i).getIdCuentaBancaria());
					cantidadDevolver =cantidadDevolver + cuentaAux.getSaldo();
					cuentaUserDAO.Delete(cuentas.get(i).getIdCuentaBancaria());
				}
			}
			
			
			userDAO.Delete(idCliente);
			mensaje = "Se cancelo la cuenta de usuario y deberá de ir al banco a recoger el dinero de sus cuentas -> " + cantidadDevolver ;
			request.setAttribute("mensaje", mensaje);
			request.getSession().removeAttribute("listadoClientes");
			nextPage = "Home";
		}else {
			nextPage = "/mvc/view/listadoClientesView.jsp"; 
			ListadoClientesBean listadoClientes = new ListadoClientesBean ();
			ArrayList<UsuarioDTO> usuarios = userDAO.QueryUsuarios();
			listadoClientes.setUsuarios(usuarios);
			System.out.println("Iremos a la vista");
			request.getSession().setAttribute("listadoClientes", listadoClientes);
		}
		
		System.out.println("Disparador");
		
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
