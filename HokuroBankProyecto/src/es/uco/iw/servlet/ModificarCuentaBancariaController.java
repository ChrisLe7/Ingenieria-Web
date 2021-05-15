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
import es.uco.iw.display.InfoCuentasBancariasBean;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.usuario.UsuarioDTO;

/**
 * Servlet implementation class CuentaBancariaController
 */

public class ModificarCuentaBancariaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificarCuentaBancariaController() {
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
		String mensajeNextPage = "";
		ClienteBean cliente = (ClienteBean) session.getAttribute("clienteBean");
		CuentaBancariaDAO cuentaUserDAO = new CuentaBancariaDAO (dbURL, username_bd, password_bd, prop);
		UsuarioDAO userDAO = new UsuarioDAO (dbURL, username_bd, password_bd, prop);
		
		Boolean login = cliente != null && !cliente.getDni().equals("");
		RequestDispatcher disparador = null;
		String nextPage = null;
		InfoCuentasBancariasBean infoCuentas = null;
		if (!login) {
			//No se encuentra logueado se debe de ir al login.
			nextPage = "Login";
			mensajeNextPage = "No se encuentra logueado, inicie sesión";
			request.setAttribute("mensaje", mensajeNextPage);
		}
		else {
			//Se encuentra logueado
			String estadoBizum = request.getParameter("modificar_bizum");
			String modificarSaldo = request.getParameter("modificar_saldo");
			String saldo =  request.getParameter("saldoCuenta");
			CuentaBancariaDTO cuenta = null;
	
			if (estadoBizum != null) {
				
				String phone = request.getParameter("telefono");
				int telefono = 0;
				if (phone != null) {
					telefono = Integer.valueOf(phone);
					cuenta = cuentaUserDAO.QueryByTelefono(telefono);
					if (cuenta != null) {
						nextPage = "Home";
						mensajeNextPage = "Lo sentimos el telefono introducido ya se encuentra vinculado a otra cuenta";
					}
					else {
						UsuarioDTO usuarioDTO = new UsuarioDTO (cliente.getDni());
						cuenta = cuentaUserDAO.QueryByIdCuentaBancaria(estadoBizum);
						
						usuarioDTO.setTelefono(telefono);
						cuenta.setEstadoBizum(!cuenta.getEstadoBizum());
						cuentaUserDAO.UpdateBizum(cuenta, usuarioDTO);
						nextPage = "MisCuentas";
					}
					
				}
				else {
					//Significa que debemos de cancelar el bizum
					UsuarioDTO usuarioDTO = new UsuarioDTO (cliente.getDni());
					cuenta = cuentaUserDAO.QueryByIdCuentaBancaria(estadoBizum);
					cuenta.setEstadoBizum(!cuenta.getEstadoBizum());
					cuentaUserDAO.UpdateBizum(cuenta, usuarioDTO);
					nextPage = "MisCuentas";
				}
				
					
					
				
					request.getSession().removeAttribute("infoCuentas");
				
		
			}
			else if (modificarSaldo != null){
				//Significa que desean modificar el saldo por lo cual deberemos de enviar la información de la cuenta concreta, el resto se podra eliminar.
				nextPage = "/mvc/view/ModificarCuentaBancaria.jsp";
				
				request.getSession().removeAttribute("infoCuentas");
				//Limpiamos el conjunto de las cuentas para ahora solo tener uno
				cuenta = cuentaUserDAO.QueryByIdCuentaBancaria(modificarSaldo);
				infoCuentas = new InfoCuentasBancariasBean();
				ArrayList<CuentaBancariaDTO> arraycuentas = new ArrayList<CuentaBancariaDTO>();
				arraycuentas.add(cuenta);
				System.out.println("holahola");
				infoCuentas.setCuentas(arraycuentas);
				request.getSession().setAttribute("infoCuentas", infoCuentas);
				
				
			}else if (saldo != null ) {
				nextPage = "MisCuentas";
				String idCuenta = request.getParameter("idCuenta");
				cuenta = cuentaUserDAO.QueryByIdCuentaBancaria(idCuenta);
					cuenta = cuentaUserDAO.QueryByIdCuentaBancaria(idCuenta);
					cuenta.setSaldo(Float.valueOf(saldo));
					cuentaUserDAO.UpdateSaldo(cuenta);
					request.getSession().removeAttribute("infoCuentas");
					nextPage = "MisCuentas";
				
			}else {
				//Ha accedido sin permiso. Dirigimos a mis Cuentas si esta logueado.
				nextPage ="/mvc/view/MisCuentasView.jsp"; 
				
			}
			
			
		}
		System.out.println("prueba");
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
