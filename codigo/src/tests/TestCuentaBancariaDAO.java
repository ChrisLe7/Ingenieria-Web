package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import es.uco.iw.datos.CuentaBancariaDAO;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.cuentaBancaria.TipoCuentaBancaria;

public class TestCuentaBancariaDAO {

	public static void main(String[] args) throws IOException {
		
		Properties properties = new Properties();
		FileReader fileReader = new FileReader("sql.properties");
		properties.load(fileReader);
			
		System.out.println("Test CuentaBancariaDAO");
		
		CuentaBancariaDAO cuentaBancariaDAO = new CuentaBancariaDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		
		CuentaBancariaDTO cuentaBancariaTest = new CuentaBancariaDTO("3", (float) 30.5, TipoCuentaBancaria.Corriente, false);
		
		assert cuentaBancariaDAO.Insert(cuentaBancariaTest) > 0 : "No se ha introducido la cuenta bancaria";
		
		assert cuentaBancariaDAO.QueryById(cuentaBancariaTest.getIdCuentaBancaria()) != null : "No se ha encontrado la cuenta bancaria";
		
		// FALTA PROBAR QueryByCliente YA QUE ACTUALMENTE NO SE TIENE TODAVIA LA RELACION
		
		assert !cuentaBancariaDAO.QueryByBizum(cuentaBancariaTest.getIdCuentaBancaria()) : "Error bizum";
		
		cuentaBancariaTest.setEstadoBizum(true);
		
		assert cuentaBancariaDAO.UpdateBizum(cuentaBancariaTest) > 0 : "Error actualizando bizum";
		
		assert cuentaBancariaDAO.QueryByBizum(cuentaBancariaTest.getIdCuentaBancaria()) : "Error bizum actualizado";
		
		assert cuentaBancariaDAO.QueryById(cuentaBancariaTest.getIdCuentaBancaria()).getSaldo() == (float) 30.5 : "Error saldo";
		
		cuentaBancariaTest.setSaldo(cuentaBancariaTest.getSaldo() + (float) 20.1);
		
		assert cuentaBancariaDAO.UpdateSaldo(cuentaBancariaTest) > 0 : "Error actualizando saldo";
		
		assert cuentaBancariaDAO.QueryById(cuentaBancariaTest.getIdCuentaBancaria()).getSaldo() == (float) 50.6 : "Error saldo actualizado";

		assert cuentaBancariaDAO.Delete(cuentaBancariaTest.getIdCuentaBancaria()) > 0 : "Error en el borrado";
		
		assert cuentaBancariaDAO.QueryById(cuentaBancariaTest.getIdCuentaBancaria()) == null : "Se ha encontrado una cuenta bancaria borrada";
		
	}
	
}

