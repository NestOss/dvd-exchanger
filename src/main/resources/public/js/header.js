
$(document).ready(function () {
     $("#header").click(function () {
        if ($("#icon").hasClass("ui-icon-triangle-1-s")) {
            $("#icon").removeClass("ui-icon-triangle-1-s");
            $("#icon").addClass("ui-icon-triangle-1-e");
            $("#editform").hide(100);
        } else {
            $("#icon").removeClass("ui-icon-triangle-1-e");
            $("#icon").addClass("ui-icon-triangle-1-s");
            $("#editform").show(100);
        }
    });
});