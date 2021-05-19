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
import es.uco.iw.datos.TarjetaDAO;
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.display.ClienteBean;
import es.uco.iw.display.InfoCuentasBancariasBean;
import es.uco.iw.display.InfoTarjetasBean;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.tarjeta.TarjetaDTO;
import es.uco.iw.negocio.usuario.PropiedadCuenta;
import es.uco.iw.negocio.usuario.UsuarioDTO;

/**
 * Servlet implementation class HomeController
 */
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
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
		String nextPage = "/index.jsp";
		RequestDispatcher disparador = null;
		UsuarioDAO userDAO = new UsuarioDAO (dbURL, username_bd, password_bd, prop);
		CuentaBancariaDAO cuentaUserDAO = new CuentaBancariaDAO (dbURL, username_bd, password_bd, prop);
		TarjetaDAO tarjetaDAO = new TarjetaDAO (dbURL, username_bd, password_bd, prop);
		
		Boolean login = cliente != null && !cliente.getDni().equals("");
		
		if (!login) {
			nextPage = "/Login";
			disparador = request.getRequestDispatcher(nextPage);
			String mensajeNextPage = "No se encuentra logueado";
			request.setAttribute("mensaje", mensajeNextPage);
		}else {
			UsuarioDTO clienteInfo = userDAO.QueryByDni(cliente.getDni());
			
			ArrayList<PropiedadCuenta> idCuentasCliente = clienteInfo.getCuentasBancarias();
			

			ArrayList<CuentaBancariaDTO> cuentasCliente = new ArrayList <CuentaBancariaDTO> ();
			for (int i = 0; i< idCuentasCliente.size(); i++) {
				cuentasCliente.add(cuentaUserDAO.QueryByIdCuentaBancaria(idCuentasCliente.get(i).getIdCuentaBancaria()));
			}

			InfoCuentasBancariasBean cuentas = new InfoCuentasBancariasBean();

			cuentas.setCuentas(cuentasCliente);
			
			session.setAttribute("infoCuentas", cuentas);
			
			nextPage = "/index.jsp";
			
			ArrayList<String> tarjetasUsuario = clienteInfo.getTarjetas();
			ArrayList<TarjetaDTO> infoTarjetasUsuario = new ArrayList<TarjetaDTO> ();
			
					
			for (int i = 0; i< tarjetasUsuario.size();i++) {
				infoTarjetasUsuario.add(tarjetaDAO.QueryByNumTarjeta(tarjetasUsuario.get(i)));
			}
			
			InfoTarjetasBean infotarjetas = new InfoTarjetasBean();

			infotarjetas.setTarjetas(infoTarjetasUsuario);
			
			session.setAttribute("infoTarjetas", infotarjetas);
			
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
