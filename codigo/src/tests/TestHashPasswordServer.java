package tests;

import java.security.SecureRandom;

import es.uco.iw.utilidades.HashPassword;

//ACTUALMENTE NO ESTA EN USO
public class TestHashPasswordServer {

	public static void main(String[] args) {
		
		System.out.println("Test HashPasswordServer");
		
		String password = "myPassword";
		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		
		String passwordHash = HashPassword.getHash(password, salt);
		
		assert !HashPassword.getHash("password", salt).equals(passwordHash) : "Error contraseña incorrecta";
		
		assert HashPassword.getHash("myPassword", salt).equals(passwordHash) : "Error contraseña correcta";
		
		System.out.println("Exito");
		
	}
	
}
