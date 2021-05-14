/*
Permite comprobar que el saldo introducido al hacer una transferencia no es negativo
*/

let cantidad = document.getElementById("cantidad");

cantidad.addEventListener("change", function () {
	if (cantidad.value < 0) {
		alert("Saldo inválido");
		cantidad.value = "";
	}
});