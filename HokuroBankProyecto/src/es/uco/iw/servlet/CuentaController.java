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
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;

/**
 * Servlet implementation class CuentaController
 */
public class CuentaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CuentaController() {
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
		dbURL= dbURL + server + ":" + port + "/" + username_bd; 
		String sql = request.getServletContext().getInitParameter("sql");
		
		ServletContext application = getServletContext();
		InputStream myIO = application.getResourceAsStream(sql);
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);
		
		ClienteBean cliente = (ClienteBean) session.getAttribute("clienteBean");
		CuentaBancariaDAO cuentaUserDAO = new CuentaBancariaDAO (dbURL, username_bd, password_bd, prop);
		
		Boolean login = cliente != null && !cliente.getDNI().equals("");
		RequestDispatcher disparador;
		String nextPage ="/mvc/view/MisCuentasView"; 
		
		if (login) {
			//Deberemos de coger la información de las cuentas del cliente para enviarsela a la vista
			String userDNI = cliente.getDNI();
			
			String idCuenta = request.getParameter("idCuenta");

			if (idCuenta != null){
				//No se que opciones pondremos por lo que lo dejo vacio por ahora
			}
			else {

				ArrayList<String> idCuentasCliente = cuentaUserDAO.QueryByIdCliente(userDNI);


				ArrayList<CuentaBancariaDTO> cuentasCliente = new ArrayList <CuentaBancariaDTO> ();
				for (int i = 0; i< idCuentasCliente.size(); i++) {
					cuentasCliente.add(cuentaUserDAO.QueryByIdCuentaBancaria(idCuentasCliente.get(i)));
				}

				InfoCuentasBancariasBean cuentas = new InfoCuentasBancariasBean();

				cuentas.setCuentas(cuentasCliente);
				
				session.setAttribute("infoCuentas", cuentas);
				
				nextPage = "MisCuentasView";
				
				disparador = request.getRequestDispatcher(nextPage);
				}
			
		}
		else {
				nextPage = "/Home";
				disparador = request.getRequestDispatcher(nextPage);
				String mensajeNextPage = "No se encuentra logueado";
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
