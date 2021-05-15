/*
Crea un filtro para las transacciones
*/

class Filtro {
	constructor(idTransaccion, operacion, cantidadMin, cantidadMax, fechaInicio, fechaFin, idCuenta) {
		this.idTransaccion = idTransaccion;
		this.operacion = operacion;
		this.cantidadMin = cantidadMin;
		this.cantidadMax = cantidadMax;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.idCuenta = idCuenta;
	}
	get idTransaccion() {
		return this.idTransaccion;
	}
	get operacion() {
		return this.operacion;
	}
	get cantidadMin() {
		return this.cantidadMin;
	}
	get cantidadMax() {
		return this.cantidadMax;
	}
	get fechaInicio() {
		return this.fechaInicio;
	}
	get fechaFin() {
		return this.fechaFin;
	}
	get idCuenta() {
		return this.idCuenta;
	}
}

let filtro = document.getElementById('filter');
filtro.addEventListener("submit", function () {
	let filtroObj = new Filtro(
			filtro.idTransaccion.value,
			filtro.tipoOperacion.value,
			filtro.cantidadMin.value,
			filtro.cantidadMax.value,
			filtro.fechaInicio.value,
			filtro.fechaFin.value,
			filtro.idCuenta.value
		);
	sessionStorage.setItem('filtro', JSON.stringify(filtroObj));
});