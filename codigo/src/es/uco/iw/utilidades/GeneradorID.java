package es.uco.iw.utilidades;

import java.util.Random;

public class GeneradorID {

	public static String GenerarIBAN() {
		Random random = new Random();
		String iban = "ES";
		iban += random.nextInt(10);
		iban += random.nextInt(10);
		
		for (int  i = 0; i < 20; i++) {
			if (i % 4 == 0) {
				iban += " ";
			}
			
			iban += random.nextInt(10);
		}
		
		return iban;
	}
	
	public static String GenerarNumTarjeta() {
		Random random = new Random();
		String numTarjeta = "" + random.nextInt(10);
		
		for (int  i = 1; i < 16; i++) {
			if (i % 4 == 0) {
				numTarjeta += " ";
			}
			
			numTarjeta += random.nextInt(10);
		}
		
		return numTarjeta;
	}
	
}
