package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import es.uco.iw.datos.TransaccionDAO;
import es.uco.iw.negocio.transaccion.TransaccionDTO;
import es.uco.iw.negocio.transaccion.TipoOperacion;

public class TestTransaccionDAO {

	public static void main(String[] args) throws IOException {
		
		Properties properties = new Properties();
		FileReader fileReader = new FileReader("sql.properties");
		properties.load(fileReader);
	
		System.out.println("Test transaccionDAO");
		
		TransaccionDAO transaccionDAO = new TransaccionDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		
		TransaccionDTO transaccionTest = new TransaccionDTO("3", (float) 123.2, TipoOperacion.Pagar, new Date(), "Test", "4", "5");
		
		assert transaccionDAO.Insert(transaccionTest) > 0 : "No se ha introducido la transaccion";
		
		assert transaccionDAO.QueryByIdTransaccion(transaccionTest.getIdTransaccion()) != null : "No se ha encontrado la transaccion";
				
		assert transaccionDAO.QueryByIdCuentaOrigen(transaccionTest.getIdCuentaOrigen()).size() == 1 : "Error numero de transacciones de cliente";
		
		assert transaccionDAO.QueryByOperacion((TipoOperacion.Pagar).toString()).size() == 1 : "Error filtro operacion pagar";
		
		assert transaccionDAO.QueryByOperacion((TipoOperacion.Recibir).toString()).size() == 0 : "Error filtro operacion recibir";
		
		assert transaccionDAO.Delete(transaccionTest.getIdTransaccion()) > 0 : "Error en el borrado";
		
		assert transaccionDAO.QueryByIdTransaccion(transaccionTest.getIdTransaccion()) == null : "Se ha encontrado una transaccion borrada";

	}
	
}
