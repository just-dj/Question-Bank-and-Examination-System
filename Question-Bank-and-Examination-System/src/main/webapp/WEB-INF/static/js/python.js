$( document ).ready(function() {
   sendJson();
});

function sendJson() {
    $.ajax({
        url: '/json/jackson',
        //type是无所谓的 但是get只能传递1kb数据
        contentType:"application/json",
        type: 'post',
        // dataType:"json",
        // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
        data:
            JSON.stringify({
            name:"西游记",
            author:"大鹏",
            price:"12" }),

    }) .done(function(data) {
        console.log(data + "心情不太好");
        $(".info").text(data);
    })
        .fail(function() {
            console.log("error");
        })
        .always(function() {
            // console.log("complete");
        });
}