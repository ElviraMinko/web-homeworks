window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.ajaxWithLists = function (map, string) {
    window.ajax(map, function (response) {
        notify(response[string].creationTime);
    });
}

window.ajaxWithoutLists = function (map, $error) {
    window.ajax(map, function (response) {
        if (response["error"]) {
            $error.text(response["error"]);
        } else {
            location.href = response["redirect"];
        }
    });
}



window.ajax = function (map, f){
$.ajax({
    type: "POST",
    url: "",
    dataType: "json",
    data: map,
    success: f
});
}

