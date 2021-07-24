document.querySelector('#standsBtn').onclick = function() {
    document.querySelector('#standsList').style.display = "";
    document.querySelector('#usersList').style.display = "none";
    document.querySelector('#usersBtn').disabled = false;
    this.disabled = true;
}

document.querySelector('#usersBtn').onclick = function() {
    document.querySelector('#standsList').style.display = "none";
    document.querySelector('#usersList').style.display = "";
    document.querySelector('#standsBtn').disabled = false;
    this.disabled = true;
}


