function ajaxMessage(state, message) {
    if(state === "success") {
        let success = document.getElementById("ajaxsuccess");
        success.style.display = "flex";
        success.innerText = message;
        setTimeout( () => {
            success.style.display = "none";
        }, 4000)
    } else if (state === "fail") {
        let fail = document.getElementById("ajaxerror");
        fail.style.display = "flex";
        fail.innerText = message;
        setTimeout( () => {
            fail.style.display = "none";
        }, 4000)
    } else if (state === "warning") {
        let warning = document.getElementById("ajaxwarning");
        warning.style.display = "flex";
        warning.innerText = message;
        setTimeout( () => {
            warning.style.display = "none";
        }, 4000)
    }
}