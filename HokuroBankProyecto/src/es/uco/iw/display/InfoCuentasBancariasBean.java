package es.uco.iw.display;

import java.io.Serializable;
import java.util.ArrayList;

import es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;


public class InfoCuentasBancariasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<CuentaBancariaDTO> cuentas = null;

	public ArrayList<CuentaBancariaDTO> getCuentas() {
		return cuentas;
	}

	public void setCuentas(ArrayList<CuentaBancariaDTO> cuentas) {
		this.cuentas = cuentas;
	}


	public CuentaBancariaDTO get(int indice) {
		return this.cuentas.get(indice);
	}
	
	public void set(int indice, CuentaBancariaDTO tarjeta) {
		this.cuentas.set(indice, tarjeta);
	}
	
	
	
	
}
