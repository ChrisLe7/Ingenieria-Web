package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import es.uco.iw.datos.CuentaBancariaDAO;
import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import es.uco.iw.negocio.cuentaBancaria.TipoCuentaBancaria;
import es.uco.iw.negocio.usuario.UsuarioDTO;

public class TestCuentaBancariaDAO {

	public static void main(String[] args) throws IOException {
		
		Properties properties = new Properties();
		FileReader fileReader = new FileReader("sql.properties");
		properties.load(fileReader);
			
		System.out.println("Test CuentaBancariaDAO");
		
		CuentaBancariaDAO cuentaBancariaDAO = new CuentaBancariaDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		
		CuentaBancariaDTO cuentaBancariaTest = new CuentaBancariaDTO("CuentaBancariaTest", (float) 30.5, TipoCuentaBancaria.Corriente, false, "UsuarioTest");
		
		// Si hay algun fallo, borra la cuenta bancaria de prueba en caso de haberla
		if (cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaTest.getIdCuentaBancaria()) != null) {
			cuentaBancariaDAO.Delete(cuentaBancariaTest.getIdCuentaBancaria());
		}
		
		assert cuentaBancariaDAO.Insert(cuentaBancariaTest, new UsuarioDTO("UsuarioTest", "password")) > 0 : "No se ha introducido la cuenta bancaria";
		
		assert cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaTest.getIdCuentaBancaria()) != null : "No se ha encontrado la cuenta bancaria";
		
		// FALTA PROBAR QueryByCliente YA QUE ACTUALMENTE NO SE TIENE TODAVIA LA RELACION
		
		assert cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaTest.getIdCuentaBancaria()).getIdTitular().equals("UsuarioTest") : "Error titular de cuenta bancaria";
		
		assert cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaTest.getIdCuentaBancaria()).getIdCotitular().equals("") : "Error cotitular de cuenta bancaria";
		
		assert !cuentaBancariaDAO.QueryByBizum(cuentaBancariaTest.getIdCuentaBancaria()) : "Error bizum";
		
		cuentaBancariaTest.setEstadoBizum(true);
		
		assert cuentaBancariaDAO.UpdateBizum(cuentaBancariaTest) > 0 : "Error actualizando bizum";
		
		assert cuentaBancariaDAO.QueryByBizum(cuentaBancariaTest.getIdCuentaBancaria()) : "Error bizum actualizado";
		
		//assert cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaTest.getIdCuentaBancaria()).getEstadoBizum() : "Error bizum actualizado";
		
		assert cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaTest.getIdCuentaBancaria()).getSaldo() == (float) 30.5 : "Error saldo";
		
		cuentaBancariaTest.setSaldo(cuentaBancariaTest.getSaldo() + (float) 20.1);
		
		assert cuentaBancariaDAO.UpdateSaldo(cuentaBancariaTest) > 0 : "Error actualizando saldo";
		
		assert cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaTest.getIdCuentaBancaria()).getSaldo() == (float) 50.6 : "Error saldo actualizado";

		assert cuentaBancariaDAO.Delete(cuentaBancariaTest.getIdCuentaBancaria()) > 0 : "Error en el borrado";
		
		assert cuentaBancariaDAO.QueryByIdCuentaBancaria(cuentaBancariaTest.getIdCuentaBancaria()) == null : "Se ha encontrado una cuenta bancaria borrada";
		
		System.out.println("Exito");
		
	}
	
}

