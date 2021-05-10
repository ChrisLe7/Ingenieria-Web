package es.uco.iw.display;

import java.io.Serializable;

import es.uco.iw.negocio.usuario.UsuarioDTO;

public class UsuarioInfoBean implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private UsuarioDTO usuario = null;

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	
    
}
