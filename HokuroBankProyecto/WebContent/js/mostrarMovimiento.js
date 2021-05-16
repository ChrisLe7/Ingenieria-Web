/*
Permite mostrar los movimientos que cumplen con el filtro
*/

let movimientos = document.getElementsByClassName('Transacciones');

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
	console.log(movimiento);
	
	
	if (filtro == null) {
		return true;
	}
	
	console.log(filtro);

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
		let fechaInicioTexto = Date.parse(filtro.fechaInicio_);
		let fechaInicio = new Date(fechaInicioTexto);
		let fechaMovimientoTexto = Date.parse(movimiento.fecha.value);
		let fechaMovimiento = new Date(fechaMovimientoTexto);

		if (fechaInicio.getTime() > fechaMovimiento.getTime()) {
			return false;
		}
	}

	if (filtro.fechaFin_ != undefined && filtro.fechaFin_ != "") {
		let fechaFinTexto = Date.parse(filtro.fechaFin_);
		let fechaFin = new Date(fechaFinTexto);
		let fechaMovimientoTexto = Date.parse(movimiento.fecha.value);
		let fechaMovimiento = new Date(fechaMovimientoTexto);

		if (fechaFin.getTime() < fechaMovimiento.getTime()) {
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