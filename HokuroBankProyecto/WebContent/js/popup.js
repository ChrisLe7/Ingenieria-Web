/*
Permite mostrar el filtro como un pop-up
*/

let buttonOpen = document.getElementById("button-open-popup");
let overlay = document.getElementById("overlay");
let popup = document.getElementById("popup");
let buttonClose = document.getElementById("button-close-popup");

buttonOpen.addEventListener("click", function () {
    overlay.classList.add("active");
    popup.classList.add("active");
});

buttonClose.addEventListener("click", function () {
    overlay.classList.remove("active");
    popup.classList.remove("active");
});