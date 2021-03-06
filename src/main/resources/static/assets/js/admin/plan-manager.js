$(document).ready(() => {
    let action = document.getElementById("action");
    let selectPlan = document.getElementById("planName");
    let selectRaid = document.getElementById("raidName");
    let submitBtn = document.getElementById("subBtn");
    let preview = document.getElementById("previewContainer");
    let rosterBox = document.getElementById("rosterBox");
    let dropzoneBox = document.getElementById("dropZoneBox");
    let planListBox = document.getElementById("planListBox");
    let PLAYERS = setPlayers();
    let isTinyNotSet = true;
    let TIMER = null;


    setTimeout(() => {
        setPlansButtonListener();
        if (isTinyNotSet) {
            let pValues = [PLAYERS.length];
            for (let i = 0; i < PLAYERS.length; i++) {
                pValues[i] = {
                    text: PLAYERS[i].name.toLowerCase(),
                    value: PLAYERS[i].name
                }
            }
            newTinyMCE(pValues);
            isTinyNotSet = false;
            setTimeout(getPlans, 500);
        }
    }, 500);

    selectRaid.addEventListener("change", () => {
        getPlans();
    });

    rosterBox.addEventListener("change", () => {
        if (rosterBox.checked === true) {
            document.getElementById("rosterDisplay").style.display = "flex";
        } else {
            document.getElementById("rosterDisplay").style.display = "none";
        }
    });

    dropzoneBox.addEventListener("change", () => {
        if (dropzoneBox.checked === true) {
            document.getElementById("drop-area").style.display = "flex";
        } else {
            document.getElementById("drop-area").style.display = "none";
        }
    });

    planListBox.addEventListener("change", () => {
        if (planListBox.checked === true) {
            document.getElementById("planListContainer").style.display = "flex";
        } else {
            document.getElementById("planListContainer").style.display = "none";
        }
    });

    selectPlan.addEventListener("change", () => {
        getPlan(selectPlan.value);
    });

    submitBtn.addEventListener("click", (e) => {
        e.preventDefault();

        if (action.value === "delete") {
            let deleteIt = confirm("Voulez vous r??ellement supprimer ce plan ?");
            if (deleteIt) {
                deletePlan(selectPlan.value);
            }
        } else {
            postPlan();
        }
    });

    action.addEventListener("change", () => {
        if (action === "create") {
            tinymce.activeEditor.getBody().innerHTML = '';
            document.getElementById('planTitle').value = '';
            document.getElementById('planTitle').innerText = '';
            submitBtn.dataset.planid = "";
            submitBtn.dataset.planversion = "0";
        }
    });

    function coloredClass(text, PLAYERS) {
        if (PLAYERS != null) {
            for (let i = 0; i < PLAYERS.length; i++) {
                let coloredName = "<span class='" + PLAYERS[i].className + "'>" + PLAYERS[i].name + "</span>";
                text = text.replaceAll(PLAYERS[i].name, coloredName);
            }
        } else {
            PLAYERS = setPlayers();
        }
        return text;
    }

    function updatePreview(editor = null) {
        TIMER = setTimeout(() => {
            let text = tinymce.activeEditor.getBody().innerHTML;
            preview.innerHTML = coloredClass(text, PLAYERS);
        }, 1000);
    }

    function setPlayers() {
        let playersContainer = document.getElementById("roster").children;
        let players = [playersContainer.length];
        for (let i = 0; i < playersContainer.length; i++) {
            players[i] = {
                name: playersContainer[i].innerText,
                className: playersContainer[i].className
            };
        }
        return players;
    }

    function newTinyMCE(specialChars) {
        //var specialChars = val;
        tinymce.init({
            onchange_callback: "updatePreview",
            selector: 'textarea',
            height: 300,
            menubar: false,
            plugins: [
                'advlist autolink lists link image charmap print preview anchor',
                'searchreplace visualblocks code fullscreen',
                'insertdatetime media table paste code help wordcount'
            ],
            forced_root_block: "",
            entity_encoding: 'raw',
            encoding: "UTF-8",
            toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons',
            content_css: [
                '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
                '//www.tiny.cloud/css/codepen.min.css'
            ],
            setup: function(editor) {

                editor.on("keyup", function(e) {
                    updatePreview(e);
                });

                var onAction = function(autocompleteApi, rng, value) {
                    editor.selection.setRng(rng);
                    editor.insertContent(value);
                    autocompleteApi.hide();
                };

                var getMatchedChars = function(pattern) {
                    return specialChars.filter(function(char) {
                        return char.text.indexOf(pattern) !== -1;
                    });
                };

                /**
                 * An autocompleter that allows you to insert special characters.
                 * Items are built using the CardMenuItem.
                 */
                editor.ui.registry.addAutocompleter('specialchars_cardmenuitems', {
                    ch: '@',
                    minChars: 1,
                    columns: 1,
                    highlightOn: ['char_name'],
                    onAction: onAction,
                    fetch: function(pattern) {
                        return new tinymce.util.Promise(function(resolve) {
                            var results = getMatchedChars(pattern).map(function(char) {
                                return {
                                    type: 'cardmenuitem',
                                    value: char.value,
                                    label: char.text,
                                    items: [{
                                        type: 'cardcontainer',
                                        direction: 'vertical',
                                        items: [{
                                                type: 'cardtext',
                                                text: char.text,
                                                name: 'char_name'
                                            },
                                            {
                                                type: 'cardtext',
                                                text: char.value
                                            }
                                        ]
                                    }]
                                }
                            });
                            resolve(results);
                        });
                    }
                });
            },
            content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
        });
    }

    function getPlan(id) {
        let getUrl = "/manager/plan/" + id;
        $.ajax({
            url: getUrl,
            type: "GET",
            success: (data) => {
                setTextAreaContent(data);
            },
            error: () => {
                ajaxMessage("fail", "Impossible de r??cup??rer le plan: " + id);
            }
        })
    }

    function deletePlan(id) {
        $.ajax({
            url: "/manager/delete/" + id,
            type: "GET",
            success: () => {
                ajaxMessage("success", "L'??l??ment a bien ??t?? supprim??.");
                getPlans();
            },
            error: () => {
                ajaxMessage("fail", "Impossible d'effectuer cette action.");
            }
        })
    }

    function postPlan() {
        let postUrl = "/manager/plan";
        let plan = {
            planName: document.getElementById('planTitle').value,
            raidName: selectRaid.value,
            content: tinymce.activeEditor.getBody().innerHTML,
            priority: document.getElementById("priority").value,
            version: 1
        };
        if (action.value === "update") {
            plan.id = selectPlan.value;
            plan.version = submitBtn.dataset.planversion;
        }
        $.ajax({
            contentType: 'application/json',
            type: "POST",
            url: postUrl,
            data: JSON.stringify(plan),
            dataType: "json",
            success: () => {
                submitBtn.dataset.planversion = parseInt(submitBtn.dataset.planversion) + 1;
                ajaxMessage("success", "Plan r??ceptionn?? par le serveur.");
            },
            error: () => {
                ajaxMessage("fail", "Erreur dans le processus");
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
                ajaxMessage("fail", "Impossible de r??cup??rer les plans.")
            }
        })
    }

    function setPlans(data) {
        selectPlan.innerHTML = '';
        if (data.length > 0) {
            for (let i = 0; i < data.length; i++) {
                let option = document.createElement("option");
                option.value = data[i].id;
                option.innerText = data[i].planName;
                selectPlan.appendChild(option);
                if (i == 0) {
                    setTextAreaContent(data[0]);
                    updatePreview();
                }
            }
        }
    }

    function setTextAreaContent(plan) {
        let title = document.getElementById('planTitle');
        title.value = plan.planName;
        document.getElementById("priority").value = plan.priority;
        let content = tinymce.activeEditor.getBody();
        content.innerHTML = plan.content;

        submitBtn.dataset.planid = plan.id;
        submitBtn.dataset.planversion = plan.version;
    }

    function setPlansButtonListener() {
        let downgradeBtn = document.getElementsByClassName("downgrade");
        let upgradeBtn = document.getElementsByClassName("upgrade");

        for (let i = 0; i < downgradeBtn.length; i++) {
            downgradeBtn[i].addEventListener("click", () => {changePriority(downgradeBtn[i], 1)});
        }
        for (let i = 0; i < upgradeBtn.length; i++) {
            upgradeBtn[i].addEventListener("click", () => {changePriority(upgradeBtn[i], -1)});
        }
    }

    function changePriority(btn, pPriority) {
        let planId = btn.dataset.planid;
        let plan = document.getElementById("plan-" + planId);
        let myData = setPriority(plan, pPriority);
        if(myData != null) {
            callToPriority(planId, myData.newPriority, myData.list, true, plan)
        } else {
            ajaxMessage("fail", "Impossible de mettre ?? jour ce plan. (Erreur donn??es)")
        }
    }

    function callToPriority(planId, newPriority, list = null, executeRoutine = false, plan = null) {
        $.ajax({
            url: "/manager/plan/priority/" + planId + "/" + newPriority,
            method: "GET",
            success: (data) => {
                if(executeRoutine) {
                    ajaxMessage("success", "Priorit?? mise ?? jours. Tentative de rafra??chissement, press F5 en cas de p??pin.");
                    setPlansDisplay(list, plan.parentNode);
                }
            },
            error: () => {
                ajaxMessage("fail", "Impossible de mettre ?? jour ce plan. (Erreur requ??te)")
            }
        })
    }

    function setPriority(plan, order) {
        let raidType = null;
        plan.classList.forEach( (e) => {
            if(e.includes("raidType-")) {
                raidType = e.match("raidType-").input;
            }
        });
        if(raidType != null) {
            let current;
            let list = document.getElementsByClassName(raidType);
            for(let i = 0; i < list.length; i++) {
                if(list[i].id === plan.id) {
                    current = i;
                    break;
                }
            }
            console.log('current: ' + current + " order: " + order);
            if(
                (order > 0 && (current < list.length-1)) ||
                (order < 0 && (current > 0))
            ) {
                let newList = new Array(list.length);
                let index = order < 0 ? current - 1 : current + 1;

                let or = list[current].dataset.priority;
                let dest =  list[index].dataset.priority;

                for(let i = 0; i < newList.length; i++) {
                    let div = document.createElement("div");
                    div.className = list[0].className;
                    if( i == index) {
                        div.id = list[current].id;
                        div.innerHTML = list[current].innerHTML;
                        div.dataset.priority = or === dest ? parseInt(dest) + 1 : list[index].dataset.priority;
                        div.dataset.planid = div.id.replace("plan-", "");
                    } else if (i == current) {
                        div.id = list[index].id;
                        div.innerHTML = list[index].innerHTML;
                        div.dataset.priority = list[current].dataset.priority;
                        div.dataset.planid = div.id.replace("plan-", "");
                        callToPriority(div.dataset.planid, div.dataset.priority);
                    } else {
                        div.id = list[i].id;
                        div.innerHTML = list[i].innerHTML;
                        div.dataset.priority = list[i].dataset.priority;
                        div.dataset.planid = div.id.replace("plan-", "");
                    }
                    newList[i] = div;
                }
                let data = {
                    newPriority: newList[index].dataset.priority,
                    list: newList
                };
                console.log(data);
                return data;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    function setPlansDisplay(data, parent) {
        parent.innerHTML = "";
        for(let i = 0; i < data.length; i++) {
            parent.appendChild(data[i]);
            let val = document.getElementById("planval-" + data[i].dataset.planid);
            val.innerText = data[i].dataset.priority;
        }
        setPlansButtonListener();
    }
});