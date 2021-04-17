function ajaxMessage(state, message) {
    if(state === "success") {
        let success = document.getElementById("ajaxsuccess");
        success.style.display = "flex";
        success.innerText = message;
        success.style.left = Math.ceil(document.body.clientWidth/2) - Math.ceil(success.offsetWidth/2) + "px";
        setTimeout( () => {
            success.style.display = "none";
        }, 4000)
    } else if (state === "fail") {
        let fail = document.getElementById("ajaxerror");
        fail.style.display = "flex";
        fail.innerText = message;
        fail.style.left = Math.ceil(document.body.clientWidth/2) - Math.ceil(fail.offsetWidth/2) + "px";
        setTimeout( () => {
            fail.style.display = "none";
        }, 4000)
    } else if (state === "warning") {
        let warning = document.getElementById("ajaxwarning");
        warning.style.display = "flex";
        warning.innerText = message;
        warning.style.left = Math.ceil(document.body.clientWidth/2) - Math.ceil(warning.offsetWidth/2) + "px";
        setTimeout( () => {
            warning.style.display = "none";
        }, 4000)
    }
}