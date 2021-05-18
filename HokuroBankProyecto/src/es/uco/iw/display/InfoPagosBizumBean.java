package es.uco.iw.display;

import java.io.Serializable;
import java.util.ArrayList;

import es.uco.iw.negocio.bizum.BizumDTO;


public class InfoPagosBizumBean  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idCuenta = "";
	private ArrayList<BizumDTO> listaPagos = null;
	private String telefono = "";
	
	public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public ArrayList<BizumDTO> getPagos() {
		return listaPagos;
	}

	public void setPagos(ArrayList<BizumDTO> pagos) {
		this.listaPagos = pagos;
	}
	
	public BizumDTO get(int indice) {
		return this.listaPagos.get(indice);
	}
	
	public void set(int indice, BizumDTO pago) {
		this.listaPagos.set(indice, pago);
	}

}
