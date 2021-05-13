package es.uco.iw.display;

import java.io.Serializable;
import java.util.ArrayList;

import es.uco.iw.negocio.transaccion.TransaccionDTO;

public class InfoTransaccionesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<TransaccionDTO> listaTransacciones = null;

	public ArrayList<TransaccionDTO> getTarjetas() {
		return listaTransacciones;
	}

	public void setTarjetas(ArrayList<TransaccionDTO> tarjetas) {
		this.listaTransacciones = tarjetas;
	}
	
	public TransaccionDTO get(int indice) {
		return this.listaTransacciones.get(indice);
	}
	
	public void set(int indice, TransaccionDTO transacccion) {
		this.listaTransacciones.set(indice, transacccion);
	}
}
