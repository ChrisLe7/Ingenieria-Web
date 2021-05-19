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

import es.uco.iw.datos.TarjetaDAO;
import es.uco.iw.display.ClienteBean;
import es.uco.iw.display.InfoTarjetasBean;
import es.uco.iw.negocio.tarjeta.TarjetaDTO;

/**
 * Servlet implementation class ModificarTarjetaController
 */

public class ModificarTarjetaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificarTarjetaController() {
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
		Boolean login = cliente != null && !cliente.getDni().equals("");
		String nextPage  = "";
		String mensajeNextPage = "";
		TarjetaDAO tarjetaDAO = new TarjetaDAO (dbURL, username_bd, password_bd, prop);
		RequestDispatcher disparador = null;
		TarjetaDTO tarjeta = null;
		if (login) {
			
			String pin = request.getParameter("pin");
			String idTarjeta = request.getParameter("idTarjetaModificar");
			if (pin != null) {
				tarjeta = new TarjetaDTO (idTarjeta, Integer.valueOf(pin), null, "", "");
				tarjetaDAO.UpdatePin(tarjeta);
			}else {
				//Tengo que ir a la vista
				if (idTarjeta != null) {
					tarjeta = tarjetaDAO.QueryByNumTarjeta(idTarjeta);
					
					InfoTarjetasBean infoTarjetas = new InfoTarjetasBean();
					infoTarjetas.setTarjetas(new ArrayList<TarjetaDTO> ());
					infoTarjetas.set(0, tarjeta);
					session.setAttribute("infoTarjetas", infoTarjetas);
					
					nextPage = "/mvc/view/modificarTarjetaView.jsp";
				}
				else {
					//Error acceden desde otro lado mandamos al home notificando un acceso erroneo
					nextPage = "Home";
					mensajeNextPage = "Lo sentimos no puede acceder a modificar Tarjeta sin escoger la tarjeta";
					request.setAttribute("mensaje", mensajeNextPage);
					
				}
			}
			
			
		}
		else {
			//No se encuentra logeado mandamos al index.
			nextPage = "Home";
			
			mensajeNextPage = "No se encuentra logueado";
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
