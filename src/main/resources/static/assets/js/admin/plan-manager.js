$(document).ready( () => {
    let action = document.getElementById("action");
    let selectPlan = document.getElementById("planName");
    let selectRaid = document.getElementById("raidName");
    let submitBtn = document.getElementById("subBtn");

    selectRaid.addEventListener("change", () => {
        getPlans();
    });


    selectPlan.addEventListener("change", () => {
        getPlan(selectPlan.value);
    });

    submitBtn.addEventListener("click", (e) => {
        e.preventDefault();

        if(action.value === "delete") {
            deletePlan(selectPlan.value);
        } else {
            postPlan();
        }
    });
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

    async function setTextAreaContent(plan) {
        let title = document.getElementById('planTitle');
        title.value = plan.planName;
        let content = tinymce.activeEditor.getBody();
        content.innerHTML = plan.content;
    }
});