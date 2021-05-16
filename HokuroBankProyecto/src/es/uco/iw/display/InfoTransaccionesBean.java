package es.uco.iw.display;

import java.io.Serializable;
import java.util.ArrayList;

import es.uco.iw.negocio.transaccion.TransaccionDTO;

public class InfoTransaccionesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idCuenta = "";
	private ArrayList<TransaccionDTO> listaTransacciones = null;

	public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}

	public ArrayList<TransaccionDTO> getTransacciones() {
		return listaTransacciones;
	}

	public void setTransacciones(ArrayList<TransaccionDTO> transacciones) {
		this.listaTransacciones = transacciones;
	}
	
	public TransaccionDTO get(int indice) {
		return this.listaTransacciones.get(indice);
	}
	
	public void set(int indice, TransaccionDTO transacccion) {
		this.listaTransacciones.set(indice, transacccion);
	}
}
