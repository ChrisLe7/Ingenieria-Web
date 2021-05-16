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
import es.uco.iw.datos.TarjetaDAO;
import es.uco.iw.datos.TransaccionDAO;
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.display.ClienteBean;
import es.uco.iw.display.ListadoClientesBean;
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
		TarjetaDAO tarjetaDAO = new TarjetaDAO (dbURL, username_bd, password_bd, prop);
		TransaccionDAO transaccionDAO = new TransaccionDAO(dbURL, username_bd, password_bd, prop);
		Boolean login = cliente != null && !cliente.getDni().equals("");
		RequestDispatcher disparador = null;
		String nextPage ="/mvc/view/listadoClientesView.jsp"; 
		UsuarioDTO clienteInfo = null;
		String idCliente = request.getParameter("idCliente");
		if (idCliente != null) {
			//Deberemos de cancelar las cuentas de los usuarios
			
			UsuarioDTO clienteDTO = userDAO.QueryByDni(idCliente);
			ArrayList<PropiedadCuenta> cuentas = clienteDTO.getCuentasBancarias();
			for (int i = 0 ; i < cuentas.size(); i++) {
				if (cuentas.get(i).getRol().equals(RolPropietario.Titular)) {
					cuentaUserDAO.Delete(cuentas.get(i).getIdCuentaBancaria());
				}
			}
			System.out.println("Hemos querido cancelar la cuenta");
			
			userDAO.Delete(idCliente);
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
