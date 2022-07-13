$(document).ready(function() {
    $("button").click(function() {
        $.ajax({
            type : 'POST',
            url : 'http://localhost:8080/generate',
            data : JSON.stringify({
                "full_url" : $("#urlinput").val()
            }),
            contentType : "application/json; charset=utf-8",
            success : function(data) {
                $("#shorturltext").val(data.short_url);
            }
        });
    });
});

$(document).ready(function() {
    $("button2").click(function() {
        $.ajax({
            type : 'GET',
            url : 'http://localhost:8080//getOriginalUrl/{shortLink}',
            data : JSON.stringify({
                "full_url" : $("#urlinput").val()
            }),
            contentType : "application/json; charset=utf-8",
            success : function(data) {
                $("#originalurltext").val(data.original_url);
            }
        });
    });
});