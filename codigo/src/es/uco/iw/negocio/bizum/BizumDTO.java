package es.uco.iw.negocio.bizum;

import java.io.Serializable;
import java.util.Date;

import es.uco.iw.negocio.transaccion.TipoOperacion;

public class BizumDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String idTransaccion;
	private float cantidad;
	private TipoOperacion tipoOperacion;
	private Date fecha;
	private String comentario;
	private int telefonoOrigen;
	private int telefonoDestino;

	/**
	 * Constructor completo de una cuenta bancaria 
	 * 
	 * @param idTransaccion ID del bizum
	 * @param cantidad Cantidad del bizum
	 * @param tipoOperacion Tipo de la operacion del bizum
	 * @param fecha Fecha del bizum
	 * @param comentario Comentario del bizum
	 * @param telefonoOrigen Id del telefono de origen del bizum
	 * @param telefonoDestino Id del telefono de destino del bizum
	 */
	public BizumDTO(String idTransaccion, float cantidad, TipoOperacion tipoOperacion, Date fecha, String comentario, int telefonoOrigen, int telefonoDestino) {
		this.idTransaccion = idTransaccion;
		this.cantidad = cantidad;
		this.tipoOperacion = tipoOperacion;
		this.fecha = fecha;
		this.comentario = comentario;
		this.telefonoOrigen = telefonoOrigen;
		this.telefonoDestino= telefonoDestino;
	}

	/**
	 * Devuelve el id de la transaccion
	 * 
	 * @return Id de la transaccion
	 */
	public String getIdTransaccion() {
		return idTransaccion;
	}

	/**
	 * Asigna un id a la transaccion
	 * 
	 * @param idTransaccion Id a asignar a la la transaccion
	 */
	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	/**
	 * Devuelve la cantidad de la transaccion
	 * 
	 * @return Cantidad de la transaccion
	 */
	public float getCantidad() {
		return cantidad;
	}

	/**
	 * Asigna una cantidad a la transaccion
	 * 
	 * @param cantidad Cantidad a asignar a la la transaccion
	 */
	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Devuelve el tipo de la transaccion
	 * 
	 * @return Tipo de la transaccion
	 */
	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Asigna un tipo a la transaccion
	 * 
	 * @param tipoOperacion Tipo a asignar a la la transaccion
	 */
	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Devuelve la fecha de la transaccion
	 * 
	 * @return Fecha de la transaccion
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Asigna una fecha a la transaccion
	 * 
	 * @param fecha Fecha a asignar a la la transaccion
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el comentario de la transaccion
	 * 
	 * @return Comentario de la transaccion
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * Asigna un comentario a la transaccion
	 * 
	 * @param comentario Comentario a asignar a la la transaccion
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * Devuelve el telefono de origen origen de la transaccion
	 * 
	 * @return Telefono de origen origen de la transaccion
	 */
	public int getTelefonoOrigen() {
		return telefonoOrigen;
	}

	/**
	 * Asigna un telefono de origen a la transaccion
	 * 
	 * @param telefonoOrigen Telefono de origen a asignar a la la transaccion
	 */
	public void setTelefonoOrigen(int telefonoOrigen) {
		this.telefonoOrigen = telefonoOrigen;
	}

	/**
	 * Devuelve el telefono de destino de la transaccion
	 * 
	 * @return Telefono de destino de la transaccion
	 */
	public int getTelefonoDestino() {
		return telefonoDestino;
	}

	/**
	 * Asigna un telefono de destino a la transaccion
	 * 
	 * @param telefonoDestino Telefono de destino a asignar a la la transaccion
	 */
	public void setTelefonoDestino(int telefonoDestino) {
		this.telefonoDestino = telefonoDestino;
	}


}
