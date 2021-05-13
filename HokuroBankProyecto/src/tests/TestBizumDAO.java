package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import es.uco.iw.datos.BizumDAO;
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.negocio.bizum.BizumDTO;
import es.uco.iw.negocio.transaccion.TipoOperacion;
import es.uco.iw.negocio.usuario.PropiedadCuenta;
import es.uco.iw.negocio.usuario.UsuarioDTO;
import es.uco.iw.negocio.usuario.UsuarioLoginDTO;

public class TestBizumDAO {

public static void main(String[] args) throws IOException {
		
		Properties properties = new Properties();
		FileReader fileReader = new FileReader("sql.properties");
		properties.load(fileReader);
	
		System.out.println("Test BizumDAO");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		BizumDAO bizumDAO = new BizumDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		
		UsuarioDTO usuarioTest = new UsuarioDTO("UsuarioTest", "Nombre", "Apellidos", "Correo", "Direccion", 1234, new ArrayList<PropiedadCuenta>(), new ArrayList<String>());
		BizumDTO bizumTest = new BizumDTO("TransaccionTest", (float) 123.2, TipoOperacion.Pagar, new Date(), "Test", 1234, 5678);
		
		// Si hay algun fallo, borra el usuario prueba en caso de haberla
		if (usuarioDAO.QueryByDni(usuarioTest.getDni()) != null ) {
			usuarioDAO.Delete(usuarioTest.getDni());
		}
		
		// Si hay algun fallo, borra la transaccion de prueba en caso de haberla
		if (bizumDAO.QueryByIdTransaccion(bizumTest.getIdTransaccion()) != null) {
			bizumDAO.Delete(bizumTest.getIdTransaccion());
		}
		
		assert usuarioDAO.Insert(usuarioTest, new UsuarioLoginDTO(usuarioTest.getDni(), "", "")) > 0 : "No se ha introducido el usuario";
				
		assert bizumDAO.Insert(bizumTest) > 0 : "No se ha introducido el bizum";
		
		assert bizumDAO.QueryByIdTransaccion(bizumTest.getIdTransaccion()) != null : "No se ha encontrado el bizum";
				
		assert bizumDAO.QueryByTelefonoOrigen(bizumTest.getTelefonoOrigen()).size() == 1 : "Error numero de bizums del usuario";
		
		assert bizumDAO.QueryByOperacion((TipoOperacion.Pagar).toString()).size() == 1 : "Error filtro operacion pagar";
		
		assert bizumDAO.QueryByOperacion((TipoOperacion.Recibir).toString()).size() == 0 : "Error filtro operacion recibir";
		
		assert bizumDAO.Delete(bizumTest.getIdTransaccion()) > 0 : "Error en el borrado";
		
		assert bizumDAO.QueryByIdTransaccion(bizumTest.getIdTransaccion()) == null : "Se ha encontrado un bizum borrado";
		
		assert usuarioDAO.Delete(usuarioTest.getDni()) > 0 : "Error en el borrado";
		
		System.out.println("Exito");
		
	}

	
}
