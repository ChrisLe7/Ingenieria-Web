package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.negocio.usuario.RolPropietario;
import es.uco.iw.negocio.usuario.UsuarioDTO;
import es.uco.iw.negocio.usuario.UsuarioLoginDTO;
import es.uco.iw.negocio.usuario.PropiedadCuenta;
import es.uco.iw.datos.CuentaBancariaDAO;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.cuentaBancaria.TipoCuentaBancaria;
import es.uco.iw.datos.TarjetaDAO;
import es.uco.iw.negocio.tarjeta.TarjetaDTO;
import es.uco.iw.negocio.tarjeta.TipoTarjeta;

public class TestUsuarioDAO_Parte2 {

	public static void main(String[] args) throws IOException {
		
		Properties properties = new Properties();
		FileReader fileReader = new FileReader("WebContent/WEB-INF/sql.properties");
		properties.load(fileReader);
			
		System.out.println("Test UsuarioDAO_Parte2");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		CuentaBancariaDAO cuentaBancariaDAO = new CuentaBancariaDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		TarjetaDAO tarjetaDAO = new TarjetaDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		
		UsuarioDTO usuarioTest = new UsuarioDTO("UsuarioTest", "Nombre", "Apellidos", "Correo", "Direccion", 1234, new ArrayList<PropiedadCuenta>(), new ArrayList<String>());
		CuentaBancariaDTO cuentaBancariaTest = new CuentaBancariaDTO("CuentaBancariaTest", (float) 30.5, TipoCuentaBancaria.Corriente, false, usuarioTest.getDni());
		TarjetaDTO tarjetaTest = new TarjetaDTO("TarjetaTest", 123, TipoTarjeta.Credito, "UsuarioTest", "CuentaBancariaTest");
		
		// Si hay algun fallo, borra el usuario de prueba en caso de haberlo
		if (usuarioDAO.QueryByDni(usuarioTest.getDni()) != null ) {
			usuarioDAO.Delete(usuarioTest.getDni());
		}
		
		// Si hay algun fallo, borra la cuenta bancaria de prueba en caso de haberla
		if (cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaTest.getIdCuentaBancaria()) != null) {
			cuentaBancariaDAO.Delete(cuentaBancariaTest.getIdCuentaBancaria());
		}
		
		// Si hay algun fallo, borra la tarjeta de prueba en caso de haberla
		if (tarjetaDAO.QueryByNumTarjeta(tarjetaTest.getNumTarjeta()) != null) {
			tarjetaDAO.Delete(tarjetaTest.getNumTarjeta());
		}
		
		UsuarioLoginDTO usuarioLoginTest = new UsuarioLoginDTO(usuarioTest.getDni(), "", "");
		
		assert usuarioDAO.Insert(usuarioTest, usuarioLoginTest) > 0 : "No se ha introducido el usuario";
		
		UsuarioDTO queryRes = usuarioDAO.QueryByDni(usuarioTest.getDni());
		
		assert queryRes.getDni().equals(usuarioTest.getDni()) : "Error Dni";
		
		assert queryRes.getNombre().equals(usuarioTest.getNombre()) : "Error nombre";
		
		assert queryRes.getApellidos().equals(usuarioTest.getApellidos()) : "Error apellidos";
		
		assert queryRes.getEmail().equals(usuarioTest.getEmail()) : "Error email";
		
		assert queryRes.getDireccion().equals(usuarioTest.getDireccion()) : "Error direccion";
		
		assert queryRes.getTelefono() == usuarioTest.getTelefono() : "Error telefono";
				
		assert queryRes.getCuentasBancarias().size() == 0 : "Error en las cuentas bancarias del usuario";
		
		assert queryRes.getTarjetas().size() == 0 : "Error en las tarjetas del usuario";
		
		assert cuentaBancariaDAO.Insert(cuentaBancariaTest, usuarioTest) > 0 : "No se ha introducido la cuenta bancaria";
		
		assert tarjetaDAO.Insert(tarjetaTest) > 0 : "No se ha introducido la tarjeta";
		
		queryRes = usuarioDAO.QueryByDni(usuarioTest.getDni());
		
		assert queryRes.getCuentasBancarias().size() == 1 : "Error en las cuentas bancarias del usuario";
		
		assert queryRes.getCuentasBancarias().get(0).getIdCuentaBancaria().equals(cuentaBancariaTest.getIdCuentaBancaria()) : "Error en la id de la cuenta bancaria";
		
		assert queryRes.getCuentasBancarias().get(0).getRol().equals(RolPropietario.Titular) : "Error rol de propiedad de cuenta bancaria";
		
		assert queryRes.getTarjetas().size() == 1 : "Error en las tarjetas del usuario";
		
		UsuarioLoginDTO res = usuarioDAO.QueryByPassword(usuarioTest.getDni());
		
		assert res.getRol().equals(usuarioLoginTest.getRol()) : "Error rol de usuario";
		
		assert tarjetaDAO.Delete(tarjetaTest.getNumTarjeta()) > 0 : "Error en el borrado";
				
		assert cuentaBancariaDAO.Delete(cuentaBancariaTest.getIdCuentaBancaria()) > 0 : "Error en el borrado";
		
		assert usuarioDAO.Delete(usuarioTest.getDni()) > 0 : "Error en el borrado";
				
		System.out.println("Exito");
		
	}
	
}
