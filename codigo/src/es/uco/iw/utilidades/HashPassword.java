package es.uco.iw.utilidades;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashPassword {
	
	/**
	 * Devulve la cadena hexadecimal de la contraseña
	 * 
	 * @param array Array de caracteres de la contraseña
	 * @return Cadena hexadecimal de la contraseña
	 */
	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        
        if(paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        else{
            return hex;
        }
	}
	
	/**
	 * Crea un salt para la contraseña
	 * 
	 * @return Salt para la contraseña
	 */
	public static String createSalt() {
		SecureRandom random = new SecureRandom();
		return "" + random.nextInt();
	}
	
	/**
	 * Devuelve el hash de una contraseña
	 * 
	 * @param password Contraseña a hashear
	 * @param salt Salt utilizado para hashear la contraseña
	 * @return Hash de la contraseña
	 */
	public static String createHash(String password, String salt) {
		byte[] hash = new byte[0];			
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1, 512);
				
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} 
		catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.out.println(e);
		}
		
		return toHex(hash);
	}
	
}
