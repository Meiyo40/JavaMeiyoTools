$(document).ready(() => {
    $("#login-submit").click( (e) => {
        e.preventDefault();
        $.ajax({
            contentType: 'application/json',
            url: "/auth/login",
            type: "POST",
            data: JSON.stringify({
                username: document.getElementById("username").value,
                password: document.getElementById("password").value
            }),
            success: () => {
                let url = window.location.href.replace("/auth/login","/manager");
                window.location.replace(url);
            },
            error: () => {
                document.getElementById("errorLogin").style.display = "block";
            }
        });
    });
});