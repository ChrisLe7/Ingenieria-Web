package tests;

import es.uco.iw.utilidades.HashPassword;

public class TestHashPasswordServer {

	public static void main(String[] args) {
		
		System.out.println("Test HashPasswordServer");
		
		String password = "myPassword";
		
		String salt = HashPassword.createSalt();
		
		String passwordHash = HashPassword.createHash(password, salt);
		
		assert !HashPassword.createHash("password", salt).equals(passwordHash) : "Error contraseña incorrecta";
		
		assert HashPassword.createHash("myPassword", salt).equals(passwordHash) : "Error contraseña correcta";
		
		System.out.println("Exito");
		
	}
	
}
