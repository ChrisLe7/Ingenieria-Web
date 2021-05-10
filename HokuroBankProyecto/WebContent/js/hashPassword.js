/*
Permite encriptar la contraseña antes de que se envié al servidor
*/

let password = document.getElementById("password");

let submitBtn = document.getElementById("submitBtn");

submitBtn.addEventListener("click", function () {
	password.value = sha512(password.value);
});