function changeBackgroundColor() {
    document.body.style.background = "red";
}

function hideImage(id) {
    document.getElementById(id).style.display = 'none';
}
function hideImages() {
    var elements = document.getElementsByClassName("image");
    for (let i = 0; i < elements.length; i++) {
        elements[i].style.display = 'none';
    }
}

function validatePassword() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;

    if (password == confirmPassword && password.length >= 8) {
        document.getElementById("passwordStatus").innerText = "Пароли введены правильно!"
    } else {
        document.getElementById("passwordStatus").innerText = "Проверьте корректность паролей!"
    }

}