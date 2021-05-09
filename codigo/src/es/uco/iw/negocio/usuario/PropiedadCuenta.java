package es.uco.iw.negocio.usuario;

public class PropiedadCuenta {

	private String idCuentaBancaria;
	private RolPropietario rol;
	
	/**
	 * Constructor con id de cuenta bancaria y el rol de propiedad del usuario
	 * 
	 * @param idCuentaBancaria Id de la cuenta bancaria
	 * @param rol Rol de propiedad del usuario
	 */
	public PropiedadCuenta(String idCuentaBancaria, RolPropietario rol) {
		this.idCuentaBancaria = idCuentaBancaria;
		this.rol = rol;
	}
	
	/**
	 * Devuelve la id de la cuenta bancaria
	 * 
	 * @return Id de la cuenta bancaria
	 */
	public String getIdCuentaBancaria() {
		return idCuentaBancaria;
	}

	/**
	 * Asigna una id a la cuenta bancaria
	 * 
	 * @param idCuentaBancaria Id de la cuenta bancaria a asignar
	 */
	public void setIdCuentaBancaria(String idCuentaBancaria) {
		this.idCuentaBancaria = idCuentaBancaria;
	}

	/**
	 * Devuelve el ro de propiedad del usuario
	 * 
	 * @return Rol de propiedad del usuario
	 */
	public RolPropietario getRol() {
		return rol;
	}

	/**
	 * Asigna un rol de propiedad al usuario
	 * 
	 * @param rol Rol de propiedad a asignar
	 */
	public void setRol(RolPropietario rol) {
		this.rol = rol;
	}	
	
}
