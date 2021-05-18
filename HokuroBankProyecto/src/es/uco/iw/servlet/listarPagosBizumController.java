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

import es.uco.iw.datos.BizumDAO;
import es.uco.iw.datos.CuentaBancariaDAO;
import es.uco.iw.datos.TransaccionDAO;
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.display.ClienteBean;
import es.uco.iw.display.InfoPagosBizumBean;
import es.uco.iw.display.InfoTransaccionesBean;
import es.uco.iw.negocio.bizum.BizumDTO;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.transaccion.TransaccionDTO;

/**
 * Servlet implementation class listarPagosBizumController
 */

public class listarPagosBizumController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listarPagosBizumController() {
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
		BizumDAO transaccionDAO = new BizumDAO(dbURL, username_bd, password_bd, prop);
		CuentaBancariaDAO cuentaUserDAO = new CuentaBancariaDAO (dbURL, username_bd, password_bd, prop);
		String nextPage ="/mvc/view/loginView"; 
		String mensajeNextPage = "";
				
		if (login) {
			String idCuenta = request.getParameter("idCuenta");
			System.out.println(idCuenta);
			
			if (idCuenta == null ) {
				mensajeNextPage = "Lo sentimos pero ha accedido sin permisos, no habia seleccionado la cuenta anteriormente";
				request.setAttribute("mensaje", mensajeNextPage);
				nextPage = "/MisCuentas";
				
			}else {
				
				CuentaBancariaDTO cuentaAux = cuentaUserDAO.QueryByIdCuentaBancaria(idCuenta);
				
				InfoPagosBizumBean infoTransacciones = null;
				if (idCuenta.equals("")) {
					infoTransacciones = (InfoPagosBizumBean) session.getAttribute("InfoTransacciones");
					idCuenta = infoTransacciones.getIdCuenta();
				}
				else {
					infoTransacciones = new InfoPagosBizumBean();
				}
				
				System.out.println("Transacciones de la cuenta: " + idCuenta);
				
				ArrayList<BizumDTO> pagosBizum =  transaccionDAO.QueryByTelefonoOrigen(cuentaAux.getTelefonoBizum());
				
				System.out.println("Numero de transacciones: " + pagosBizum.size());
				

				
				infoTransacciones.setIdCuenta(idCuenta);
				infoTransacciones.setTelefono(idCuenta);
				infoTransacciones.setPagos(pagosBizum);
				session.setAttribute("infoTransaccionesBizum", infoTransacciones);
				//cambio añadido 
				nextPage = "/mvc/view/listarTransaccionesBizumView.jsp";
			}
		}
		else {
			nextPage = "Login";
			mensajeNextPage = "No se encuentra logueado, inicie sesion";
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
