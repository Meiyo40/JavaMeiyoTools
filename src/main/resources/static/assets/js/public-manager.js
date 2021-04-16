$(document).ready(function() {
    let PlanContainer = document.getElementById("PlanContainer");
    let RaidSelector = document.getElementById("RaidSelector");
    let PlanSelector = document.getElementById("PlanSelector");
    let PLAYERS = setPlayers();
    let CURRENT = null;
    const REFRESH_TIMER = 10000;

    setTimeout( () => {
        getPlans();
    }, 1000);

    RaidSelector.addEventListener("change", () => {
        getPlans();
    });


    PlanSelector.addEventListener("change", () => {
        getPlan(PlanSelector.value);
    });

    setInterval(() => {
        getPlan(PlanSelector.value);
    }, REFRESH_TIMER);

    function getPlans() {
        let getUrl = "/manager/raid/" + RaidSelector.value;
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
        PlanSelector.innerHTML = '';
        if(data.length > 0) {
            for(let i = 0; i < data.length ; i++) {
                let option = document.createElement("option");
                option.value = data[i].id;
                option.innerText = data[i].planName;
                PlanSelector.appendChild(option);
                //default
                PlanContainer.innerHTML = coloredClass(data[0].content);
            }
        }
    }

    function getPlan(id) {
        let getUrl = "/manager/plan/" + id;
        $.ajax({
            url: getUrl,
            type: "GET",
            success: (data) => {
                setContent(data)
            },
            error: () => {
                ajaxMessage("fail", "Erreur dans l'actualisation.")
            }
        })
    }

    function setContent(data) {
        if(CURRENT == null) {
            CURRENT = data;
            PlanContainer.innerHTML = coloredClass(data.content);
        } else {
            if(CURRENT.version == data.version) {
                return;
            }
            PlanContainer.innerHTML = coloredClass(data.content);
            CURRENT = data;
        }
    }

    function $_GET(param) {
        var vars = {};
        window.location.href.replace(location.hash, '').replace(
            /[?&]+([^=&]+)=?([^&]*)?/gi, // regexp
            function(m, key, value) { // callback
                vars[key] = value !== undefined ? value : '';
            }
        );

        if (param) {
            return vars[param] ? vars[param] : null;
        }
        return vars;
    }

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
});