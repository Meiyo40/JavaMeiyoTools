$(document).ready( () => {
    let action = document.getElementById("action");
    let selectPlan = document.getElementById("planName");
    let selectRaid = document.getElementById("raidName");
    let submitBtn = document.getElementById("subBtn");
    let preview = document.getElementById("previewContainer");
    let PLAYERS = null;

    setTimeout( getPlans, 1000);

    selectRaid.addEventListener("change", () => {
        getPlans();
    });


    selectPlan.addEventListener("change", () => {
        getPlan(selectPlan.value);
    });

    submitBtn.addEventListener("click", (e) => {
        e.preventDefault();

        if(action.value === "delete") {
            let deleteIt = confirm("Voulez vous réellement ce plan ?");
            if(deleteIt) {
                deletePlan(selectPlan.value);
            }
        } else {
            postPlan();
        }
    });

    action.addEventListener("change", () => {
        if(action === "create") {
            tinymce.activeEditor.getBody().innerHTML = '';
            document.getElementById('planTitle').value = '';
            document.getElementById('planTitle').innerText = '';
        }
    });

    setInterval(() => {
        let text = tinymce.activeEditor.getBody().innerHTML;
        preview.innerHTML = coloredClass(text);
    }, 5000);

    function coloredClass(text) {
        if(PLAYERS != null) {
            for(let i = 0; i < PLAYERS.length; i++) {
                let coloredName = "<span class='" + PLAYERS[i].className + "'>" + PLAYERS[i].name + "</span>";
                text = text.replaceAll(PLAYERS[i].name, coloredName);
            }
        } else {
            PLAYERS = setPlayers();
            console.log(PLAYERS);
        }

        return text;
    }

    function setPlayers() {
        let playersContainer = document.getElementById("roster").children;
        let players = [playersContainer.length];
        for(let i = 0; i < playersContainer.length ; i++) {
            players[i] = {
                name: playersContainer[i].innerText,
                className: playersContainer[i].className
            }
        }
        return players;
    }

    function getPlayers() {
        let getUrl = "/player";
        $.ajax({
            url: getUrl,
            type: "GET",
            success: (data) => {
                return data;
            },
            error: () => {
                console.log("GET::Impossible de récupérer les joueurs.");
                return null;
            }
        });
    }

    let players = new Promise( (resolve, reject) => {

    })

    function getPlan(id) {
        let getUrl = "/manager/plan/" + id;
        $.ajax({
            url: getUrl,
            type: "GET",
            success: (data) => {
                setTextAreaContent(data);
            },
            error: () => {
                alert("Erreur.")
            }
        })
    }

    function deletePlan(id) {
        $.ajax({
            url: "/manager/delete/" + id,
            type: "GET",
            success: () => {
                alert('Plan supprimé');
                getPlans();
            },
            error: () => {
                alert("Erreur.")
            }
        })
    }

    function postPlan() {
        let postUrl = "/manager/plan";
        let plan = {
            planName: document.getElementById('planTitle').value,
            raidName: selectRaid.value,
            content: tinymce.activeEditor.getBody().innerHTML
        };
        if(action.value === "update") {
            plan.id = selectPlan.value;
        }
        $.ajax({
            contentType: 'application/json',
            type:"POST",
            url: postUrl,
            data: JSON.stringify(plan),
            dataType: "json",
            success: () => {
                alert("Plan upload.");
            },
            error: () => {
                alert("Erreur.")
            }
        });
    }

    function getPlans() {
        let getUrl = "/manager/raid/" + selectRaid.value;
        $.ajax({
            url: getUrl,
            type: "GET",
            success: (data) => {
                setPlans(data);
            },
            error: () => {
                alert("Erreur.")
            }
        })
    }

    function setPlans(data) {
        selectPlan.innerHTML = '';
        if(data.length > 0) {
            for(let i = 0; i < data.length ; i++) {
                let option = document.createElement("option");
                option.value = data[i].id;
                option.innerText = data[i].planName;
                selectPlan.appendChild(option);
                if(i == 0) {
                    setTextAreaContent(data[0]);
                }
            }
        }
    }

    function setTextAreaContent(plan) {
        let title = document.getElementById('planTitle');
        title.value = plan.planName;
        let content = tinymce.activeEditor.getBody();
        content.innerHTML = plan.content;
    }
});