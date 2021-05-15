/*
Permite crear una contraseña aleatoria para resetearla
*/

let prehashPassword = document.getElementById("prehashPassword");

let hashPassword = document.getElementById("password");

let submitButton = document.getElementById("submitBtn");

submitButton.addEventListener("click", function () {
    const characters ='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

    for (let i = 0; i < 10; i++) {
        prehashPassword.value += characters.charAt(Math.floor(Math.random() * characters.length));
    }

    hashPassword.value = prehashPassword.value;
});