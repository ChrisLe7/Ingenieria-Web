/*
Crea un filtro para las transacciones
*/

class Filtro {
	constructor(idTransaccion, operacion, cantidadMin, cantidadMax, fechaInicio, fechaFin, idCuenta) {
		this.idTransaccion_ = idTransaccion;
		this.operacion_ = operacion;
		this.cantidadMin_ = cantidadMin;
		this.cantidadMax_ = cantidadMax;
		this.fechaInicio_ = fechaInicio;
		this.fechaFin_ = fechaFin;
		this.idCuenta_ = idCuenta;
	}
	get idTransaccion() {
		return this.idTransaccion_;
	}
	get operacion() {
		return this.operacion_;
	}
	get cantidadMin() {
		return this.cantidadMin_;
	}
	get cantidadMax() {
		return this.cantidadMax_;
	}
	get fechaInicio() {
		return this.fechaInicio_;
	}
	get fechaFin() {
		return this.fechaFin_;
	}
	get idCuenta() {
		return this.idCuenta_;
	}
}

let filtroForm = document.getElementById('filter');
filtroForm.addEventListener("submit", function () {
	let filtroObj = new Filtro(
			filtroForm.idTransaccion.value,
			filtroForm.tipoOperacion.value,
			filtroForm.cantidadMin.value,
			filtroForm.cantidadMax.value,
			filtroForm.fechaInicio.value,
			filtroForm.fechaFin.value,
			filtroForm.idCuenta.value
		);
	sessionStorage.setItem('filtro', JSON.stringify(filtroObj));
});