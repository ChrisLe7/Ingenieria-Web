package tests;

import es.uco.iw.utilidades.EnvioCorreo;

public class TestEnvioCorreo {

	public static void main(String[] args) {
		
		System.out.println("Test EnvioCorreo");
		
		// Poner correo de prueba para recibir el mensaje
		String destinatario = "";
		String asunto = "Asunto";
		String mensaje = "Mensaje";
		
		EnvioCorreo.EnviarCorreo(destinatario, asunto, mensaje);
		
		System.out.println("Exito");
		
	}
	
}
