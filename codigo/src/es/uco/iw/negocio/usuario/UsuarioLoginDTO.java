package es.uco.iw.negocio.usuario;

public class UsuarioLoginDTO {

	private String dni;
	private String password;
	private String salt;
	private RolUsuario rol;

	/**
	 * Constructor con dni, password y salt de un usuario para el login
	 * 
	 * @param dni Dni del usuario
	 * @param password Password del usuario
	 * @param salt Salt del login del usuario
	 */
	public UsuarioLoginDTO(String dni, String password, String salt) {
		this(dni, password, salt, RolUsuario.Cliente);
	}
	
	/**
	 * Constructor completo de un usuario para el login
	 * 
	 * @param dni Dni del usuario
	 * @param password Password del usuario
	 * @param salt Salt del login del usuario
	 * @param rol Rol del login usuario
	 */
	public UsuarioLoginDTO(String dni, String password, String salt, RolUsuario rol) {
		this.dni = dni;
		this.password = password;
		this.salt = salt;
		this.rol = rol;
	}

	/**
	 * Devuelve el dni de un usuario
	 * 
	 * @return Dni del usuario
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Asigna un dni a un usuario
	 * 
	 * @param dni Dni a asignar en el usuario
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	/**
	 * Devuelve la contraseña de un contato
	 * 
	 * @return Contraseña del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Asigna una contraseña a un usuario
	 * 
	 * @param password Contraseña a asignar en el usuario
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Devuelve el salt del login de un usuario
	 * 
	 * @return Salt del login de un usuario
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * Asigna un salt al login de un usuario
	 * 
	 * @param salt Salt a asignar al login del usuario
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Devuelve el rol de un usuario
	 * 
	 * @return Rol del usuario
	 */
	public RolUsuario getRol() {
		return rol;
	}

	/**
	 * Asigna rol a un usuario
	 * @param rol Rol a asignar al usuario
	 */
	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}

	@Override
	/**
	 * Devuelve el contenido del objeto como cadena
	 * 
	 * @return Cadena con el contenido
	 */
	public String toString() {
		return "UsuarioLoginDTO [dni=" + dni + ", password=" + password + ", salt=" + salt + ", rol=" + rol + "]";
	}
	
}
