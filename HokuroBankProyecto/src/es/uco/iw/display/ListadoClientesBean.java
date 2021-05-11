package es.uco.iw.display;

import java.io.Serializable;
import java.util.ArrayList;

import es.uco.iw.negocio.usuario.UsuarioDTO;

public class ListadoClientesBean implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private ArrayList<UsuarioDTO> listadoUsuarios = null;
    
	public ArrayList<UsuarioDTO> getUsuarios() {
		return listadoUsuarios;
	}

	public void setUsuarios(ArrayList<UsuarioDTO> tarjetas) {
		this.listadoUsuarios = tarjetas;
	}
	
	public UsuarioDTO get(int indice) {
		return this.listadoUsuarios.get(indice);
	}
	
	public void set(int indice, UsuarioDTO tarjeta) {
		this.listadoUsuarios.set(indice, tarjeta);
	}
}
