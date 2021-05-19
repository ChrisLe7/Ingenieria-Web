package es.uco.iw.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

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
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.display.ClienteBean;
import es.uco.iw.display.InfoCuentasBancariasBean;
import es.uco.iw.negocio.bizum.BizumDTO;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.transaccion.TipoOperacion;
import es.uco.iw.negocio.usuario.PropiedadCuenta;
import es.uco.iw.negocio.usuario.UsuarioDTO;

/**
 * Servlet implementation class RealizarPagoBizum
 */
@WebServlet("/RealizarPagoBizum")
public class RealizarPagoBizumController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RealizarPagoBizumController() {
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
		BizumDAO bizumDAO = new BizumDAO(dbURL, username_bd, password_bd, prop);
		CuentaBancariaDAO cuentaUserDAO = new CuentaBancariaDAO (dbURL, username_bd, password_bd, prop);
		String nextPage ="/mvc/view/loginView"; 
		String mensajeNextPage = "";
		if (!login) {
			//No se encuentra logueado se debe de ir al login.
			nextPage = "Login";
			mensajeNextPage = "No se encuentra logueado, inicie sesión";
			request.setAttribute("mensaje", mensajeNextPage);
		}	
		
		else {
			
			String idTelefonoDestino = request.getParameter("idTelefonoDestino");
			
			if (idTelefonoDestino != null) {
				//Significa que vengo de la vista
				String idTelefonoOrigen = request.getParameter("idCuentaOrigen");
				String descripcion = request.getParameter("descripcion");
				String cantidad = request.getParameter("cantidad");
				String tipoOperacion = request.getParameter("tipoOperacion");
				String idTransaccion = "";
				BizumDTO transaccionBizum = new BizumDTO (idTransaccion, Float.valueOf(cantidad), TipoOperacion.valueOf(tipoOperacion), new Date(), descripcion, Integer.valueOf(idTelefonoOrigen), Integer.valueOf(idTelefonoDestino));
	

				CuentaBancariaDTO cuentaDestino = cuentaUserDAO.QueryByTelefono(Integer.valueOf(idTelefonoDestino));	
				
				if (cuentaDestino == null || !cuentaDestino.getEstadoBizum ()) {
					mensajeNextPage = "Lo sentimos pero la cuenta destino no existe o no tiene Bizum habilitado";
					request.setAttribute("mensaje", mensajeNextPage);
				}
				else {
					CuentaBancariaDTO cuentaOrigen = cuentaUserDAO.QueryByTelefono(Integer.valueOf(idTelefonoOrigen));
					if (cuentaOrigen.getEstadoBizum()) {
						Float aux = cuentaOrigen.getSaldo() - Float.valueOf(cantidad);
						System.out.println(aux);
						System.out.println(cuentaOrigen);
						System.out.println(cuentaDestino);
						
						if (aux >= 0) {
							cuentaOrigen.setSaldo(aux);
							cuentaDestino.setSaldo(cuentaDestino.getSaldo() +  Float.valueOf(cantidad));
							cuentaUserDAO.UpdateSaldo(cuentaOrigen);
							cuentaUserDAO.UpdateSaldo(cuentaDestino);
							
							bizumDAO.Insert(transaccionBizum);
							mensajeNextPage = "El Pago se ha realizado con exito, se le han transferido " + cantidad + " al telefono " + idTelefonoDestino;
							request.setAttribute("mensaje", mensajeNextPage); 
						}
						else {
							mensajeNextPage = "Lo sentimos pero la cantidad a realizar es superior a la que posee la cuenta Origen";
							request.setAttribute("mensaje", mensajeNextPage);
						}
					}
					
					nextPage = "Home";
					request.getSession().removeAttribute("UsuarioInfoBean");
				}

			}
			else {
				// Tengo que ir a la vista
				//Debere de coger que cuentas pueden realizar el pago.
				UsuarioDTO clienteInfo = userDAO.QueryByDni(cliente.getDni());
				nextPage = "/mvc/view/realizarPagoBizumView.jsp";

				
				ArrayList<PropiedadCuenta> idCuentas = clienteInfo.getCuentasBancarias();
				ArrayList<CuentaBancariaDTO> listadoCuentas = new ArrayList<CuentaBancariaDTO> ();
				for (int i = 0; i< idCuentas.size(); i++) {
					listadoCuentas.add(cuentaUserDAO.QueryByIdCuentaBancaria(idCuentas.get(i).getIdCuentaBancaria()));
				}
				
				InfoCuentasBancariasBean infoCuentasUsuario = new InfoCuentasBancariasBean();
				infoCuentasUsuario.setCuentas(listadoCuentas);

				session.setAttribute("infoCuentasUsuario", infoCuentasUsuario);
				
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
