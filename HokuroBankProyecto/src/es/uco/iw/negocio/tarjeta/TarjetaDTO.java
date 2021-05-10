package es.uco.iw.negocio.tarjeta;

import java.io.Serializable;

public class TarjetaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String numTarjeta;
    private int pin;
    private TipoTarjeta tipoTarjeta;
    private String idCliente;
    private String idCuenta;

    /**
	 * Constructor completo de una tarjeta 
	 * 
	 * @param numTarjeta ID de la tarjeta
	 * @param pin Pin de la tarjeta
	 * @param tipoTarjeta Tipo de la tarjeta
	 * @param idCliente Id del cliente dueño de la tarjeta
	 * @param idCuenta Id de la cuenta asociada a la tarjeta
	 */
    public TarjetaDTO(String numTarjeta, int pin, TipoTarjeta tipoTarjeta, String idCliente, String idCuenta) {
        this.numTarjeta = numTarjeta;
        this.pin = pin;
        this.tipoTarjeta = tipoTarjeta;
        this.idCliente = idCliente;
        this.idCuenta = idCuenta;
    }

    /**
	 * Devuelve el número de la tarjeta
	 * 
	 * @return Número de la tarjeta
	 */
    public String getNumTarjeta() {
        return numTarjeta;
    }

    /**
	 * Asigna un número a una tarjeta
	 * 
	 * @param numTarjeta Número a asignar a la tarjeta
	 */
    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    /**
	 * Devuelve el pin de la tarjeta
	 * 
	 * @return Pin de la tarjeta
	 */
    public int getPin() {
        return pin;
    }

    /**
	 * Asigna un pin a una tarjeta
	 * 
	 * @param pin Pin a asignar a la tarjeta
	 */
    public void setPin(int pin) {
        this.pin = pin;
    }

    /**
	 * Devuelve el tipo de la tarjeta
	 * 
	 * @return Tipo de la tarjeta
	 */
    public TipoTarjeta getTipotarjeta() {
        return tipoTarjeta;
    }

    /**
	 * Asigna un tipo a una tarjeta
	 * 
	 * @param pin Tipo a asignar a la tarjeta
	 */
    public void setTipotarjeta(TipoTarjeta tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    /**
	 * Devuelve el id del cliente dueño de la tarjeta
	 * 
	 * @return Id del cliente dueño de la tarjeta
	 */
    public String getIdCliente() {
        return idCliente;
    }

    /**
	 * Asigna la id del cliente dueño a la tarjeta
	 * 
	 * @param idCliente Id del cliente dueño a asignar a la tarjeta
	 */
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    /**
	 * Devuelve la id de la cuenta asociada a la tarjeta
	 * 
	 * @return Id de la cuenta asociada a asignar a de la tarjeta
	 */
    public String getIdCuenta() {
        return idCuenta;
    }

    /**
	 * Asigna una id de la cuenta asociada a la tarjeta
	 * 
	 * @param idCuenta Id de la cuenta asociada a asignar a la tarjeta
	 */
    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

	@Override
	/**
	 * Devuelve el contenido del objeto como cadena
	 * 
	 * @return Cadena con el contenido
	 */
	public String toString() {
		return "TarjetaDTO [numTarjeta=" + numTarjeta + ", pin=" + pin + ", tipoTarjeta=" + tipoTarjeta + ", idCliente="
				+ idCliente + ", idCuenta=" + idCuenta + "]";
	}

}