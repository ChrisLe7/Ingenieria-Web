package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import es.uco.iw.datos.TarjetaDAO;
import es.uco.iw.negocio.tarjeta.TarjetaDTO;
import es.uco.iw.negocio.tarjeta.TipoTarjeta;
import es.uco.iw.datos.CuentaBancariaDAO;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.cuentaBancaria.TipoCuentaBancaria;
import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.negocio.usuario.UsuarioDTO;

public class TestTarjetaDAO {

	public static void main(String[] args) throws IOException {
	
		Properties properties = new Properties();
		FileReader fileReader = new FileReader("sql.properties");
		properties.load(fileReader);
	
		System.out.println("Test tarjetaDAO");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		CuentaBancariaDAO cuentaBancariaDAO = new CuentaBancariaDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		TarjetaDAO tarjetaDAO = new TarjetaDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		
		UsuarioDTO usuarioTest = new UsuarioDTO("UsuarioTest", "123");
		CuentaBancariaDTO cuentaBancariaTest = new CuentaBancariaDTO("CuentaBancariaTest", (float) 30.5, TipoCuentaBancaria.Corriente, false);
		TarjetaDTO tarjetaTest = new TarjetaDTO("TarjetaTest", 123, TipoTarjeta.Credito, "UsuarioTest", "CuentaBancariaTest");
		
		// Si hay algun fallo, borra el usuario prueba en caso de haberla
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
		
		assert (usuarioDAO.Insert(usuarioTest) > 0) : "No se ha introducido el usuario";
		
		assert cuentaBancariaDAO.Insert(cuentaBancariaTest, usuarioTest) > 0 : "No se ha introducido la cuenta bancaria";
		
		assert tarjetaDAO.Insert(tarjetaTest) > 0 : "No se ha introducido la tarjeta";
		
		assert tarjetaDAO.QueryByNumTarjeta(tarjetaTest.getNumTarjeta()) != null : "No se ha encontrado la tarjeta";
				
		assert tarjetaDAO.QueryByIdCliente(tarjetaTest.getIdCliente()).size() == 1 : "Error numero de tarjetas de cliente";
		
		assert tarjetaDAO.QueryByNumTarjeta(tarjetaTest.getNumTarjeta()).getPin() == 123 : "Error pin";
		
		tarjetaTest.setPin(456);
				
		assert tarjetaDAO.UpdatePin(tarjetaTest) > 0 : "Error actualizando pin";
		
		assert tarjetaDAO.QueryByNumTarjeta(tarjetaTest.getNumTarjeta()).getPin() == 456 : "Error pin actualizado";
			
		assert tarjetaDAO.Delete(tarjetaTest.getNumTarjeta()) > 0 : "Error en el borrado";
		
		assert tarjetaDAO.QueryByNumTarjeta(tarjetaTest.getNumTarjeta()) == null : "Se ha encontrado tarjeta borrada";
		
		assert cuentaBancariaDAO.Delete(cuentaBancariaTest.getIdCuentaBancaria()) > 0 : "Error en el borrado";
		
		assert usuarioDAO.Delete(usuarioTest.getDni()) > 0 : "Error en el borrado";
		
		System.out.println("Exito");

	}
	
}
