$(document).ready(function() {
    let addPlayerButton = document.getElementById("PlayerAddButton");
    let editPlayer = document.getElementsByClassName("editPlayer");
    let deletePlayer = document.getElementsByClassName("deletePlayer");

    for(let i = 0; i < editPlayer.length ; i++) {
        editPlayer[i].addEventListener("click", () => {
            let modal = document.getElementById("modalEdit");
            modal.style.display = "block";
            let close = document.getElementsByClassName("close")[0];
            close.onclick = () => {
                modal.style.display = "none";
            };
            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            };

            setModalData(editPlayer[i].dataset.playerid)
        })
    }

    for(let i = 0; i < deletePlayer.length ; i++) {
        deletePlayer[i].addEventListener("click", () => {
            let deleteHim = confirm("Voulez vous réellement supprimer ce joueur ?");
            if(deleteHim) {
                let url = "/player/" + deletePlayer[i].dataset.playerid;
                $.ajax({
                    url: url,
                    type: "DELETE",
                    success: () => {
                        ajaxMessage("warning", "Joueur supprimé.");
                        let row = document.getElementById(deletePlayer[i].dataset.playerid);
                        row.parentNode.removeChild(row);
                    },
                    error: () => {
                        ajaxMessage("fail", "Erreur processus.");
                    }
                })
            }
        })
    }

    addPlayerButton.addEventListener("click", () => {
        let url = "/player";
        let data = {
            name: document.getElementById("playername").value.trim(),
            className: document.getElementById("class").value,
            role: document.getElementById("role").value,
            comment: document.getElementById("comment").value,
            main : document.getElementById("main").value
        };
        if(data.name == "") {
            alert("Vous ne pouvez pas ajouter de joueur sans pseudo.")
        } else {
            $.ajax({
                contentType: 'application/json',
                type:"POST",
                url: url,
                data: JSON.stringify(data),
                dataType: "json",
                success: (data) => {
                    ajaxMessage("success", "Joueur ajouté.");
                    createNewRow(data)
                },
                error: () => {
                    ajaxMessage("fail", "Erreur processus.");
                }
            });
        }
    });

    function createNewRow(data) {
        console.log(data);
        let table = document.getElementById("tableBody");
        let row = document.createElement("tr");

        let td = document.createElement("td");
        td.classList.add(data.className);
        td.innerText = data.name;
        row.append(td);

        td = document.createElement("td");
        td.innerText = data.className;
        row.append(td);

        td = document.createElement("td");
        td.innerText = data.role;
        row.append(td);

        td = document.createElement("td");
        td.innerText = data.comment;
        row.append(td);

        td = document.createElement("td");
        td.innerText = data.main ? "Oui" : "Non";
        row.append(td);

        td = document.createElement("td");
        let i = document.createElement("i");
        i.className = "fas fa-user-minus tooltip grab";
        i.dataset.playerid = data.id;
        let span = document.createElement("span");
        span.classList.add("tooltiptext");
        span.innerText = "Supprimer joueur";
        i.append(span);
        td.append(i);
        i = document.createElement("i");
        i.className = "fas fa-cogs tooltip grab";
        i.dataset.playerid = data.id;
        span = document.createElement("span");
        span.classList.add("tooltiptext");
        span.innerText = "Modifier joueur";
        i.append(span);
        td.append(i);
        row.append(td);

        table.append(row);
    }

    function setModalData(PlayerId) {
        let element = document.getElementById(PlayerId);
        let name = document.getElementById("modalPlayerName");
        let playerClass = document.getElementById("modalClassName");
        let role = document.getElementById("modalRoleName");
        let comment = document.getElementById("modalComment");
        let main = document.getElementById("modalMain");
        name.value = element.children[0].innerText;
        name.dataset.playerid = PlayerId;

        for(let i = 0; i < playerClass.options.length; i++) {
            if(playerClass.options[i].value == element.children[1].innerText) {
                playerClass.options[i].selected = true;
                break;
            }
        }

        for(let i = 0; i < role.options.length; i++) {
            if(role.options[i].value == element.children[2].innerText) {
                role.options[i].selected = true;
                break;
            }
        }

        for(let i = 0; i < main.options.length; i++) {
            let isMain = element.children[4].innerText === "Oui" ? "true" : "false";
            if(main.options[i].value == isMain) {
                main.options[i].selected = true;
                break;
            }
        }

        comment.value = element.children[3].innerText;

        let modalSubmit = document.getElementById("modalButtonAdd");
        modalSubmit.addEventListener("click", () => {
            let url = "/player/" + PlayerId;
            let data = {
                name: name.value,
                className: playerClass.value,
                role: role.value,
                comment: comment.value,
                main: main.value
            };
            $.ajax({
                contentType: 'application/json',
                type:"PUT",
                url: url,
                data: JSON.stringify(data),
                dataType: "json",
                success: (data) => {
                    document.getElementById(PlayerId).parentNode.removeChild(document.getElementById(PlayerId));
                    ajaxMessage("success", "Joueur modifié.");
                    createNewRow(data)
                },
                error: () => {
                    ajaxMessage("fail", "Erreur processus.");
                }
            });
        })
    }
});