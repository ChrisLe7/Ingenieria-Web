package es.uco.iw.display;

import java.io.Serializable;
import java.util.ArrayList;

import es.uco.iw.negocio.tarjeta.TarjetaDTO;

public class InfoTarjetasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<TarjetaDTO> tarjetas;

	public ArrayList<TarjetaDTO> getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(ArrayList<TarjetaDTO> tarjetas) {
		this.tarjetas = tarjetas;
	}
	
	public TarjetaDTO get(int indice) {
		return this.tarjetas.get(indice);
	}
	
	public void set(int indice, TarjetaDTO tarjeta) {
		this.tarjetas.set(indice, tarjeta);
	}

}
