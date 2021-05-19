/*
Permite mostrar los movimientos que cumplen con el filtro
*/

let movimientos = document.getElementsByClassName('transaccion');

let filtro = JSON.parse(sessionStorage.getItem('filtro'));

let movimiento = null;

for (let i = 0; i < movimientos.length; i++) {
	movimiento = movimientos[i].getElementsByTagName('form')[0];

	if (!mostrarMovimiento(movimiento)) {
		movimientos[i].style.display = "none";
	}

	if (i == movimientos.length - 1) {
		sessionStorage.removeItem('filtro');
	}
}

function mostrarMovimiento (movimiento) {		
	if (filtro == null) {
		return true;
	}
		
	if (filtro.idTransaccion_ != undefined && filtro.idTransaccion_ != "" && movimiento.idTransaccion.value.indexOf(filtro.idTransaccion_) == -1) {
		return false;
	}

	if (filtro.operacion_ != undefined && filtro.operacion_ != "") {
		if (filtro.operacion_ == "Pagar" && movimiento.idCuentaOrigen.value.indexOf(movimiento.idCuentaSeleccionada.value) == -1) {
			return false;
		}
		else if (filtro.operacion_ == "Recibir" && movimiento.idCuentaDestino.value.indexOf(movimiento.idCuentaSeleccionada.value) == -1) {
			return false;
		}
	}

	if (filtro.cantidadMin_ != undefined && filtro.cantidadMin_ != "" && parseFloat(movimiento.cantidad.value) < filtro.cantidadMin_) {
		return false;
	}

	if (filtro.cantidadMax_ != undefined && filtro.cantidadMax_ != "" && parseFloat(movimiento.cantidad.value) > filtro.cantidadMax_) {
		return false;
	}

	if (filtro.fechaInicio_ != undefined && filtro.fechaInicio_ != "") {
		let fechaInicio = new Date(new Date(filtro.fechaInicio_).toDateString()).getTime()
		let fechaMovimiento = new Date(new Date(movimiento.fecha.value.replace(/CEST /g, "")).toDateString()).getTime()

		if (fechaInicio > fechaMovimiento) {
			return false;
		}
	}

	if (filtro.fechaFin_ != undefined && filtro.fechaFin_ != "") {
		let fechaFin = new Date(new Date(filtro.fechaFin_).toDateString()).getTime()
		let fechaMovimiento = new Date(new Date(movimiento.fecha.value.replace(/CEST /g, "")).toDateString()).getTime()

		if (fechaFin < fechaMovimiento) {
			return false;
		}
	}

	if (filtro.idCuenta_ != undefined && filtro.idCuenta_ != "") {
		if (movimiento.idCuentaOrigen.value.indexOf(filtro.idCuenta_) == -1 && movimiento.idCuentaDestino.value.indexOf(filtro.idCuenta_) == -1) {
			return false;
		}
	}

	return true;
}