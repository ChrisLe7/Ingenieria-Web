package es.uco.iw.negocio.transaccion;

import java.io.Serializable;
import java.util.Date;

public class TransaccionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String idTransaccion;
	private float cantidad;
	private TipoOperacion tipoOperacion;
	private Date fecha;
	private String comentario;
	private String idCuentaOrigen;
	private String idCuentaDestino;

	/**
	 * Constructor completo de una cuenta bancaria 
	 * 
	 * @param idTransaccion ID de la transaccion
	 * @param cantidad Cantidad de la transaccion
	 * @param tipoOperacion Tipo de la operacion de la transaccion
	 * @param fecha Fecha de la transaccion
	 * @param comentario Comentario de la transaccion
	 * @param idCuentaOrigen Id de la cuenta de origen de la transaccion
	 * @param idCuentaDestino Id de la cuenta de destino de la transaccion
	 */
	public TransaccionDTO(String idTransaccion, float cantidad, TipoOperacion tipoOperacion, Date fecha, String comentario, String idCuentaOrigen, String idCuentaDestino) {
		this.idTransaccion = idTransaccion;
		this.cantidad = cantidad;
		this.tipoOperacion = tipoOperacion;
		this.fecha = fecha;
		this.comentario = comentario;
		this.idCuentaOrigen = idCuentaOrigen;
		this.idCuentaDestino= idCuentaDestino;
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
	 * Devuelve el id de la cuenta origen de la transaccion
	 * 
	 * @return Id de la cuenta origen de la transaccion
	 */
	public String getIdCuentaOrigen() {
		return idCuentaOrigen;
	}

	/**
	 * Asigna un id de la cuenta origen a la transaccion
	 * 
	 * @param idCuentaOrigen Id de la cuenta origen a asignar a la la transaccion
	 */
	public void setIdCuentaOrigen(String idCuentaOrigen) {
		this.idCuentaOrigen = idCuentaOrigen;
	}

	/**
	 * Devuelve el id de la cuenta destino de la transaccion
	 * 
	 * @return Id de la cuenta destino de la transaccion
	 */
	public String getIdCuentaDestino() {
		return idCuentaDestino;
	}

	/**
	 * Asigna un id de la cuenta destino a la transaccion
	 * 
	 * @param idCuentaDestino Id de la cuenta destino a asignar a la la transaccion
	 */
	public void setIdCuentaDestino(String idCuentaDestino) {
		this.idCuentaDestino = idCuentaDestino;
	}

	@Override
	/**
	 * Devuelve el contenido del objeto como cadena
	 * 
	 * @return Cadena con el contenido
	 */
	public String toString() {
		return "TransaccionDTO [idTransaccion=" + idTransaccion + ", cantidad=" + cantidad + ", tipoOperacion="
				+ tipoOperacion + ", fecha=" + fecha + ", comentario=" + comentario + ", idCuentaOrigen="
				+ idCuentaOrigen + ", idCuentaDestino=" + idCuentaDestino + "]";
	}

}