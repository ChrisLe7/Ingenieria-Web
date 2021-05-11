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
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.display.ClienteBean;
import es.uco.iw.display.InfoTarjetasBean;
import es.uco.iw.negocio.tarjeta.TarjetaDTO;
import es.uco.iw.negocio.usuario.UsuarioDTO;

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
		UsuarioDAO userDAO = new UsuarioDAO (dbURL, username_bd, password_bd, prop);
		TarjetaDAO tarjetaDAO = new TarjetaDAO (dbURL, username_bd, password_bd, prop);
		Boolean login = cliente != null && !cliente.getDni().equals("");
		RequestDispatcher disparador;
		String nextPage ="/mvc/view/loginView"; 
		
		if (login) {
			//Se encuentra logueado el usuario deberemos de coger la información de sus tarjetas para mostrarlas.
			
			UsuarioDTO userDTO = userDAO.QueryByDni(cliente.getDni());
			System.out.println("Compruebo que el usuario se encuentra logueado [TARJETACONTROLLER]");
			String TarjetaCancelar = request.getParameter("tarjeta");


			if (TarjetaCancelar != null) {
				//Deberemos de cancelar la tarjeta y regresar al home.
					
				
				disparador = request.getRequestDispatcher("/Home");
				String mensajeNextPage = "Se ha cancelado la tarjeta con exito";
				request.setAttribute("mensaje", mensajeNextPage);
				
			}
			else {
				ArrayList<String> tarjetasUsuario = userDTO.getTarjetas();
				ArrayList<TarjetaDTO> infoTarjetasUsuario = new ArrayList<TarjetaDTO> ();
				
				System.out.println("Dirijo al usuario a la vista tras coger la información");
				for (int i = 0; i< tarjetasUsuario.size();i++) {
					infoTarjetasUsuario.add(tarjetaDAO.QueryByNumTarjeta(tarjetasUsuario.get(i)));
				}
				
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
