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
	if (filtro == null) {
		return true;
	}

	if (filtro.idTransaccion != undefined && filtro.idTransaccion != "" && movimiento.idTransaccion.value.indexOf(filtro.idTransaccion) == -1) {
		return false;
	}

	if (filtro.operacion != undefined && filtro.operacion != "") {
		if (filtro.operacion == "Pagar" && movimiento.idCuentaOrigen.value.indexOf(movimiento.idCuentaSeleccionada.value) == -1) {
			return false;
		}
		else if (filtro.operacion == "Recibir" && movimiento.idCuentaDestino.value.indexOf(movimiento.idCuentaSeleccionada.value) == -1) {
			return false;
		}
	}

	if (filtro.cantidadMin != undefined && filtro.cantidadMin != "" && parseFloat(movimiento.cantidadMin.value) < filtro.cantidadMin) {
		return false;
	}

	if (filtro.cantidadMax != undefined && filtro.cantidadMax != "" && parseFloat(movimiento.cantidadMax.value) > filtro.cantidadMax) {
		return false;
	}

	if (filtro.fechaInicio != undefined && filtro.fechaInicio != "") {
		let fechaInicioTexto = Date.parse(filtro.fechaInicio);
		let fechaInicio = new Date(fechaInicioTexto);
		let fechaMovimientoTexto = Date.parse(movimiento.fecha.value);
		let fechaMovimiento = new Date(fechaMovimientoTexto);

		if (fechaInicio.getTime() > fechaMovimiento.getTime()) {
			return false;
		}
	}

	if (filtro.fechaFin != undefined && filtro.fechaFin != "") {
		let fechaFinTexto = Date.parse(filtro.fechaFin);
		let fechaFin = new Date(fechaFinTexto);
		let fechaMovimientoTexto = Date.parse(movimiento.fecha.value);
		let fechaMovimiento = new Date(fechaMovimientoTexto);

		if (fechaFin.getTime() < fechaMovimiento.getTime()) {
			return false;
		}
	}

	if (filtro.idCuenta != undefined && filtro.idCuenta != "") {
		if (movimiento.idCuentaOrigen.value.indexOf(filtro.idCuenta) == -1 && movimiento.idCuentaDestino.value.indexOf(filtro.idCuenta) == -1) {
			return false;
		}
	}

	return true;
}