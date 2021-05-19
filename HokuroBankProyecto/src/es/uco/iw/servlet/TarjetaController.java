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
 * Servlet implementation class TarjetaController
 */

public class TarjetaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TarjetaController() {
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
		TarjetaDAO tarjetaDAO = new TarjetaDAO (dbURL, username_bd, password_bd, prop);
		Boolean login = cliente != null && !cliente.getDni().equals("");
		RequestDispatcher disparador;
		String nextPage ="/mvc/view/loginView"; 
		
		if (login) {
			//Se encuentra logueado el usuario deberemos de coger la información de sus tarjetas para mostrarlas.
			
			System.out.println("Compruebo que el usuario se encuentra logueado [TARJETACONTROLLER]");
			String TarjetaCancelar = request.getParameter("idtarjeta");
			System.out.println(TarjetaCancelar);

			if (TarjetaCancelar != null) {
				//Deberemos de cancelar la tarjeta y regresar al home.
				if (tarjetaDAO.Delete(TarjetaCancelar) == 0) {
					System.out.println("Ha fallado la cancelaci�n");

				}
				disparador = request.getRequestDispatcher("Home");
				String mensajeNextPage = "Se ha cancelado la tarjeta con exito";
				request.setAttribute("mensaje", mensajeNextPage);
				session.removeAttribute("infoTarjetas");
			}
			else {
				
				ArrayList<TarjetaDTO> infoTarjetasUsuario = tarjetaDAO.QueryByIdCliente(cliente.getDni());
				
				System.out.println("Dirijo al usuario a la vista tras coger la información");
				


				InfoTarjetasBean infotarjetas = new InfoTarjetasBean();

				infotarjetas.setTarjetas(infoTarjetasUsuario);
				
				session.setAttribute("infoTarjetas", infotarjetas);
				
				nextPage = "/mvc/view/misTarjetasView.jsp";
				
				disparador = request.getRequestDispatcher(nextPage);

			}
		}
		else {
			//No se encuentra logueado se debe de ir al login.
			disparador = request.getRequestDispatcher("/Login");
			String mensajeNextPage = "No se encuentra logueado, inicie sesión";
			request.setAttribute("mensaje", mensajeNextPage);
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
