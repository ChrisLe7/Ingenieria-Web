/*
Permite alternar entre modificar y ver los datos del perfil de usuario
*/

let verPerfilDiv = document.getElementById("observarP");
let modificarPerfilDiv = document.getElementById("modificarP");
let habilitarCambiosBtn = document.getElementById("observarPButton");
let deshabilitarCambiosBtn = document.getElementById("modificarPButton");

console.log(verPerfilDiv)
console.log(modificarPerfilDiv)
console.log(habilitarCambiosBtn)
console.log(deshabilitarCambiosBtn)


habilitarCambiosBtn.addEventListener("click", function () {
	verPerfilDiv.style.display = "none";
	modificarPerfilDiv.style.display = "inline";
});

deshabilitarCambiosBtn.addEventListener("click", function () {
	verPerfilDiv.style.display = "inline";
	modificarPerfilDiv.style.display = "none";
});