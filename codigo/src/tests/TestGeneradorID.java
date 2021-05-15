package tests;

import es.uco.iw.utilidades.GeneradorID;

public class TestGeneradorID {

	public static void main(String[] args) {
		
		System.out.println("Test GeneradorID");
		
		System.out.println("Generando IBAN aleatorio");
		
		for (int i = 0; i < 5; i++) {
			System.out.println(GeneradorID.GenerarIBAN());
		}
		
		System.out.println("Generando numero de tarjeta aleatorio");
		
		for (int i = 0; i < 5; i++) {
			System.out.println(GeneradorID.GenerarNumTarjeta());
		}
		
		System.out.println("Generando id de transaccion bancarias aleatoria");
		
		for (int i = 0; i < 5; i++) {
			System.out.println(GeneradorID.GenerarIdTransaccion());
		}
		
		System.out.println("Exito");
		
	}
	
}
