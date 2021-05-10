package es.uco.iw.negocio.cuentaBancaria;

import java.io.Serializable;

public class CuentaBancariaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String idCuentaBancaria;
	private float saldo;
	private TipoCuentaBancaria tipoCuenta;
	private boolean estadoBizum;
	private String idTitular;
	private String idCotitular;

	/**
	 * Constructor con id, saldo, tipo de cuenta, estado de bizum e id de Ttitular una cuenta bancaria 
	 * 
	 * @param idCuentaBancaria ID de la cuenta bancaria
	 * @param saldo Saldo de la cuenta bancaria
	 * @param tipoCuenta Tipo de la cuenta bancaria
	 * @param estadoBizum Estado del bizum de la cuenta bancaria
	 * @param idTitular Id del titular de la cuenta bancaria
	 */
	public CuentaBancariaDTO(String idCuentaBancaria, float saldo, TipoCuentaBancaria tipoCuenta, boolean estadoBizum, String idTitular) {
		this(idCuentaBancaria, saldo, tipoCuenta, estadoBizum, idTitular, "");
	}
	
	/**
	 * Constructor completo de una cuenta bancaria 
	 * 
	 * @param idCuentaBancaria ID de la cuenta bancaria
	 * @param saldo Saldo de la cuenta bancaria
	 * @param tipoCuenta Tipo de la cuenta bancaria
	 * @param estadoBizum Estado del bizum de la cuenta bancaria
	 * @param idTitular Id del titular de la cuenta bancaria
	 * @param idCotitular Id del cotitular de la cuenta bancaria
	 */
	public CuentaBancariaDTO(String idCuentaBancaria, float saldo, TipoCuentaBancaria tipoCuenta, boolean estadoBizum, String idTitular, String idCotitular) {
		this.idCuentaBancaria = idCuentaBancaria;
		this.saldo = saldo;
		this.tipoCuenta = tipoCuenta;
		this.estadoBizum = estadoBizum;
		this.idTitular = idTitular;
		this.idCotitular = idCotitular;
	}

	/**
	 * Devuelve el id de una cuenta bancaria
	 * 
	 * @return Id de la cuenta bancaria
	 */
	public String getIdCuentaBancaria() {
		return idCuentaBancaria;
	}

	/**
	 * Asigna un id a una cuenta bancaria
	 * 
	 * @param idCuentaBancaria Id a asignar a la cuenta bancaria
	 */
	public void setIdCuentaBancaria(String idCuentaBancaria) {
		this.idCuentaBancaria = idCuentaBancaria;
	}

	/**
	 * Devuelve el saldo de una cuenta bancaria
	 * 
	 * @return Saldo de la cuenta bancaria
	 */
	public float getSaldo() {
		return saldo;
	}

	/**
	 * Asigna un saldo a una cuenta bancaria
	 * 
	 * @param saldo Saldo a asignar a la cuenta bancaria
	 */
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	/**
	 * Devuelve el tipo de cuenta de una cuenta bancaria
	 * 
	 * @return Tipo de cuenta de la cuenta bancaria
	 */
	public TipoCuentaBancaria getTipoCuentaBancaria() {
		return tipoCuenta;
	}

	/**
	 * Asigna un tipo de cuenta a una cuenta bancaria
	 * 
	 * @param tipoCuenta Tipo de cuenta a asignar a la cuenta bancaria
	 */
	public void setTipoCuentaBancaria(TipoCuentaBancaria tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	/**
	 * Devuelve el estado de bizum de una cuenta bancaria
	 * 
	 * @return Estado de bizum de la cuenta bancaria
	 */
	public boolean getEstadoBizum() {
		return estadoBizum;
	}

	/**
	 * Asigna un estado de bizum a una cuenta bancaria
	 * 
	 * @param estadoBizum Estado de bizum a asignar a la cuenta bancaria
	 */
	public void setEstadoBizum(boolean estadoBizum) {
		this.estadoBizum = estadoBizum;
	}

	/**
	 * Devuelve el id del titular de la cuenta bancaria
	 * 
	 * @return Id del titular de la cuenta bancaria
	 */
	public String getIdTitular() {
		return idTitular;
	}

	/**
	 * Asigna un id de titular a la cuenta bancaria
	 * 
	 * @param idTitular Id del titular de la cuenta bancaria a asginar
	 */
	public void setIdTitular(String idTitular) {
		this.idTitular = idTitular;
	}

	/**
	 * Devuelve el id del cotitular de la cuenta bancaria
	 * 
	 * @return Id del Cotitular de la cuenta bancaria
	 */
	public String getIdCotitular() {
		return idCotitular;
	}

	/**
	 * Asigna un id del cotitular de la cuenta bancaria
	 * 
	 * @param idCotitular Id del cotitular de la cuenta bancaria a asignar
	 */
	public void setIdCotitular(String idCotitular) {
		this.idCotitular = idCotitular;
	}

	@Override
	/**
	 * Devuelve el contenido del objeto como cadena
	 * 
	 * @return Cadena con el contenido
	 */
	public String toString() {
		return "CuentaBancariaDTO [idCuentaBancaria=" + idCuentaBancaria + ", saldo=" + saldo + ", tipoCuenta="
				+ tipoCuenta + ", estadoBizum=" + estadoBizum + ", idTitular=" + idTitular + ", idCotitular="
				+ idCotitular + "]";
	}

}