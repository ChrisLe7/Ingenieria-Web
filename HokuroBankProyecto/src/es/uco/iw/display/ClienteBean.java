package es.uco.iw.display;

import java.io.Serializable;

import es.uco.iw.negocio.usuario.RolUsuario;

public class ClienteBean implements Serializable {
    private static final long serialVersionUID = 1L;
	
    private String dni;
	private RolUsuario rol;
	
	ClienteBean () {}
	
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public RolUsuario getRol() {
		return rol;
	}
	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}
	
	
	
}
