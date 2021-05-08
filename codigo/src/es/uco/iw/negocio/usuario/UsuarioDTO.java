package es.uco.iw.negocio.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String dni;
	private String password;
	private String nombre;
	private String apellidos;
	private String email;
	private String direccion;
	private int telefono;
	private ArrayList<String> cuentasBancarias;
	private ArrayList<String> tarjetas;

	/**
	 * Constructor con dni de un usuario 
	 * 
	 * @param dni Dni del nuevo usuario
	 */
	public UsuarioDTO(String dni) {
		this(dni, "", "", "", "", 0, new ArrayList<String>(), new ArrayList<String>());
	}

	/**
	 * Constructor completo de un usuario 
	 * 
	 * @param dni Dni del nuevo usuario
	 * @param nombre Nombre del nuevo usuario
	 * @param apellidos Apellidos del nuevo usuario
	 * @param email Email del nuevo usuario
	 * @param direccion Direccion del nuevo usuario
	 * @param telefono Telefono del nuevo usuario
	 * @param cuentasBancarias Lista de cuentas bancarias del nuevo usuario
	 * @param tarjetas Lista de tarjetas bancarias del nuevo usuario
	 */
	public UsuarioDTO(String dni, String nombre, String apellidos, String email, String direccion, int telefono, ArrayList<String> cuentasBancarias, ArrayList<String> tarjetas) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.direccion = direccion;
		this.telefono = telefono;
		this.cuentasBancarias = cuentasBancarias;
		this.tarjetas = tarjetas;
	}

	/**
	 * Devuelve el dni de un contato
	 * 
	 * @return Dni del usuario
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Asigna un dni a un usuario
	 * 
	 * @param dni Dni a asignar en el usuario
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	/**
	 * Devuelve la contraseña de un contato
	 * 
	 * @return Contraseña del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Asigna una contraseña a un usuario
	 * 
	 * @param password Contraseña a asignar en el usuario
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Devuelve el nombre de un usuario
	 * 
	 * @return Nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna un nombre a un usuario
	 * 
	 * @param nombre Nombre a asignar al usuario
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve los apellidos de un usuario
	 * 
	 * @return Apellidos del usuario
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Asigna apellidos a un usuario
	 * @param apellidos Apellidos a asignar al usuario
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Devuelve el email de un usuario
	 * 
	 * @return Email del usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Asigna email a un usuario
	 * @param email Email a asignar al usuario
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Devuelve el direccion de un usuario
	 * 
	 * @return Direccion del usuario
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Asigna direccion a un usuario
	 * @param direccion Direccion a asignar al usuario
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Devuelve el telefono de un usuario
	 * 
	 * @return Telefono del usuario
	 */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * Asigna telefono a un usuario
	 * @param telefono Direccion a asignar al usuario
	 */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	/**
	 * Devuelve una lista de cuantas bancarias de un usuario
	 * 
	 * @return Lista de cuentas bancarias del usuario
	 */
	public ArrayList<String> getCuentasBancarias() {
		return cuentasBancarias;
	}

	/**
	 * Asigna una lista de cuentas bancarias a un usuario
	 * 
	 * @param cuentasBancarias Lista de cuentas bancarias a asignar al usuario
	 */
	public void setCuentasBancarias(ArrayList<String> cuentasBancarias) {
		this.cuentasBancarias = cuentasBancarias;
	}

	/**
	 * Asigna un array de cuentas bancarias a un usuario
	 * 
	 * @param cuentasBancarias Array de cuentas bancarias a asignar al usuario
	 */
	public void setCuentasBancarias(String[] cuentasBancarias) {
		Collections.addAll(this.cuentasBancarias, cuentasBancarias);
	}

	/**
	 * AÃ±ade una cuenta bancaria a la lista de cuentas bancarias de un usuario
	 * 
	 * @param cuentasBancaria Cuenta bancaria a introducir a la lista
	 * @return true si se introduce y false en caso contrario
	 */
	public boolean introducirCuentaBancaria(String cuentasBancaria) {
		return cuentasBancarias.add(cuentasBancaria);
	}

	/**
	 * Borra una cuenta bancaria de la lista de cuentas bancarias de un usuario
	 * 
	 * @param cuentasBancaria Cuenta bancaria a borrar de la lista
	 * @return true si se borra y false en caso contrario
	 */
	public boolean borrarCuentaBancaria(String cuentasBancaria) {
		return cuentasBancarias.remove(cuentasBancaria);
	}

	/**
	 * Devuelve una lista de tarjetas bancarias de un usuario
	 * 
	 * @return Lista de tarjetas bancarias del usuario
	 */
	public ArrayList<String> getTarjetas() {
		return tarjetas;
	}

	/**
	 * Asigna una lista de tarjetas bancarias a un usuario
	 * 
	 * @param tarjetas Lista de tarjetas bancarias a asignar al usuario
	 */
	public void setTarjetas(ArrayList<String> tarjetas) {
		this.tarjetas = tarjetas;
	}

	/**
	 * Asigna un array de tarjetas bancarias a un usuario
	 * 
	 * @param tarjetas Array de tarjetas bancarias a asignar al usuario
	 */
	public void setTarjetas(String[] tarjetas) {
		Collections.addAll(this.tarjetas, tarjetas);
	}

	/**
	 * AÃ±ade una tarjeta bancaria a la lista de tarjetas bancarias de un usuario
	 * 
	 * @param tarjeta Tarjeta bancaria a introducir a la lista
	 * @return true si se introduce y false en caso contrario
	 */
	public boolean introducirTarjeta(String tarjeta) {
		return tarjetas.add(tarjeta);
	}

	/**
	 * Borra una cuenta bancaria de la lista de tarjetas bancarias de un usuario
	 * 
	 * @param tarjeta Tarjeta bancaria a borrar de la lista
	 * @return true si se borra y false en caso contrario
	 */
	public boolean borrarTarjeta(String tarjeta) {
		return tarjetas.remove(tarjeta);
	}
	
}