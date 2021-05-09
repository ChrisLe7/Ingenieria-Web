package es.uco.iw.negocio.usuario;

public class PropiedadCuenta {

	private String idCuentaBancaria;
	private RolPropietario rol;
	
	public PropiedadCuenta(String idCuentaBancaria, RolPropietario rol) {
		super();
		this.idCuentaBancaria = idCuentaBancaria;
		this.rol = rol;
	}
	
	public String getIdCuentaBancaria() {
		return idCuentaBancaria;
	}

	public void setIdCuentaBancaria(String idCuentaBancaria) {
		this.idCuentaBancaria = idCuentaBancaria;
	}

	public RolPropietario getRol() {
		return rol;
	}

	public void setRol(RolPropietario rol) {
		this.rol = rol;
	}	
	
}
