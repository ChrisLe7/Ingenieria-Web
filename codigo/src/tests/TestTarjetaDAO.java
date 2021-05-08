package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import es.uco.iw.datos.TarjetaDAO;
import es.uco.iw.negocio.tarjeta.TarjetaDTO;
import es.uco.iw.negocio.tarjeta.TipoTarjeta;

public class TestTarjetaDAO {

	public static void main(String[] args) throws IOException {
	
		Properties properties = new Properties();
		FileReader fileReader = new FileReader("sql.properties");
		properties.load(fileReader);
	
		System.out.println("Test tarjetaDAO");
		
		TarjetaDAO tarjetaDAO = new TarjetaDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		
		TarjetaDTO tarjetaTest = new TarjetaDTO("3", 123, TipoTarjeta.Credito, "3", "4");
		
		assert tarjetaDAO.Insert(tarjetaTest) > 0 : "No se ha introducido la tarjeta";
		
		assert tarjetaDAO.QueryByNumTarjeta(tarjetaTest.getNumTarjeta()) != null : "No se ha encontrado la tarjeta";
				
		assert tarjetaDAO.QueryByIdCliente(tarjetaTest.getIdCliente()).size() == 1 : "Error numero de tarjetas de cliente";
		
		assert tarjetaDAO.QueryByNumTarjeta(tarjetaTest.getNumTarjeta()).getPin() == 123 : "Error pin";
		
		tarjetaTest.setPin(456);
				
		assert tarjetaDAO.UpdatePin(tarjetaTest) > 0 : "Error actualizando pin";
		
		assert tarjetaDAO.QueryByNumTarjeta(tarjetaTest.getNumTarjeta()).getPin() == 456 : "Error pin actualizado";
			
		assert tarjetaDAO.Delete(tarjetaTest.getNumTarjeta()) > 0 : "Error en el borrado";
		
		assert tarjetaDAO.QueryByNumTarjeta(tarjetaTest.getNumTarjeta()) == null : "Se ha encontrado tarjeta borrada";

	}
	
}
