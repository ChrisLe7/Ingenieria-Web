package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import es.uco.iw.datos.TransaccionDAO;
import es.uco.iw.negocio.transaccion.TransaccionDTO;
import es.uco.iw.negocio.usuario.UsuarioDTO;
import es.uco.iw.negocio.transaccion.TipoOperacion;
import es.uco.iw.datos.CuentaBancariaDAO;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.cuentaBancaria.TipoCuentaBancaria;

public class TestTransaccionDAO {

	public static void main(String[] args) throws IOException {
		
		Properties properties = new Properties();
		FileReader fileReader = new FileReader("sql.properties");
		properties.load(fileReader);
	
		System.out.println("Test TransaccionDAO");
		
		CuentaBancariaDAO cuentaBancariaDAO = new CuentaBancariaDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		TransaccionDAO transaccionDAO = new TransaccionDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		
		CuentaBancariaDTO cuentaBancariaOrigenTest = new CuentaBancariaDTO("CuentaOrigenTest", (float) 30.5, TipoCuentaBancaria.Corriente, false, "UsuarioTest");
		CuentaBancariaDTO cuentaBancariaDestinoTest = new CuentaBancariaDTO("CuentaDestinoTest", (float) 5, TipoCuentaBancaria.Corriente, false, "UsuarioTest");
		TransaccionDTO transaccionTest = new TransaccionDTO("TransaccionTest", (float) 20, TipoOperacion.Pagar, new Date(), "Test", "CuentaOrigenTest", "CuentaDestinoTest");
		
		// Si hay algun fallo, borra la cuenta bancaria de origen de prueba en caso de haberla
		if (cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaOrigenTest.getIdCuentaBancaria()) != null) {
			cuentaBancariaDAO.Delete(cuentaBancariaOrigenTest.getIdCuentaBancaria());
		}
		
		// Si hay algun fallo, borra la cuenta bancaria de destino de prueba en caso de haberla
		if (cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaDestinoTest.getIdCuentaBancaria()) != null) {
			cuentaBancariaDAO.Delete(cuentaBancariaDestinoTest.getIdCuentaBancaria());
		}
		
		// Si hay algun fallo, borra la transaccion de prueba en caso de haberla
		if (transaccionDAO.QueryByIdTransaccion(transaccionTest.getIdTransaccion()) != null) {
			transaccionDAO.Delete(transaccionTest.getIdTransaccion());
		}
		
		cuentaBancariaDAO.Insert(cuentaBancariaOrigenTest, new UsuarioDTO("UsuarioTest"));
		
		cuentaBancariaDAO.Insert(cuentaBancariaDestinoTest, new UsuarioDTO("Usuario2Test"));
		
		assert transaccionDAO.Insert(transaccionTest) > 0 : "No se ha introducido la transaccion";
		
		assert transaccionDAO.QueryByIdTransaccion(transaccionTest.getIdTransaccion()) != null : "No se ha encontrado la transaccion";
				
		assert transaccionDAO.QueryByIdCuentaOrigen(transaccionTest.getIdCuentaOrigen()).size() == 1 : "Error numero de transacciones de cuenta";
		
		assert transaccionDAO.QueryByOperacion((TipoOperacion.Pagar).toString()).size() == 1 : "Error filtro operacion pagar";
		
		assert transaccionDAO.QueryByOperacion((TipoOperacion.Recibir).toString()).size() == 0 : "Error filtro operacion recibir";
		
		assert transaccionDAO.Delete(transaccionTest.getIdTransaccion()) > 0 : "Error en el borrado";
		
		assert transaccionDAO.QueryByIdTransaccion(transaccionTest.getIdTransaccion()) == null : "Se ha encontrado una transaccion borrada";

		cuentaBancariaDAO.Delete(cuentaBancariaOrigenTest.getIdCuentaBancaria());
		
		cuentaBancariaDAO.Delete(cuentaBancariaDestinoTest.getIdCuentaBancaria());
		
		System.out.println("Exito");
		
	}
	
}
