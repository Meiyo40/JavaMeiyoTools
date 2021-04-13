$(document).ready(function() {
    let PlanContainer = document.getElementById("PlanContainer");
    let RaidSelector = document.getElementById("RaidSelector");
    let PlanSelector = document.getElementById("PlanSelector");
    const REFRESH_TIMER = 30000;
    let CURRENT_PLAN = $_GET("current");


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
})