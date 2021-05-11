package tests;

import java.security.SecureRandom;

import es.uco.iw.utilidades.HashPassword;

public class TestHashPasswordServer {

	public static void main(String[] args) {
		
		System.out.println("Test HashPasswordServer");
		
		String password = "myPassword";
		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		
		String passwordHash = HashPassword.createHash(password, salt.toString());
		
		assert !HashPassword.createHash("password", salt.toString()).equals(passwordHash) : "Error contraseña incorrecta";
		
		assert HashPassword.createHash("myPassword", salt.toString()).equals(passwordHash) : "Error contraseña correcta";
		
		System.out.println("Exito");
		
	}
	
}
