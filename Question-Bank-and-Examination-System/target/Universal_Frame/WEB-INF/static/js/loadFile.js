$(document).ready(function () {
    
    $(".upload").click(function () {
        var formData = new FormData($('#uploadForm')[0])
         // console.log($('#uploadForm').text[0]);
        $.ajax({
            url: '/upload',
            //type是无所谓的 但是get只能传递1kb数据
            type: 'Post',
            // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
            data: formData,
            //上传文件不需要缓存
            cache:false,
            //data值是FormData对象，不需要对数据做处理
            processData:false,
            // 且已经声明了属性enctype="multipart/form-data"，所以这里设置为false
            contentType:false
        })
            .done(function(data) {
                $("#result").text(data);
                $("#testImg").attr('src',"/images/"+data.toString());
                $("#download").attr('href',"/download?filename="+data.toString());
            })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");
            });
    });

    $("#testImg").click(function () {
        var a = this.src;
        var filename = a.slice(a.lastIndexOf("/")+1,a.length);
        console.log(a + "       " + filename);
        $.ajax({
            url: '/download',
            //type是无所谓的 但是get只能传递1kb数据
            type: 'get',
            // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
            data: {"filename":filename},
            //默认值为true下载文件缓存
            cache:true,
            // //默认值为true 对数据做处理
            // processData:true,
            // // 默认值为true
            // contentType:true
        })
            .done(function(data) {
                console.log("下载")
            })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");
            });
    })
});