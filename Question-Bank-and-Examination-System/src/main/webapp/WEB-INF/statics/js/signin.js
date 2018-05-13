


var myLibary = new Vue({
    el: '#signin-form',
    data: {
        account:"",
        password:"",
        confirm:"",
        picked:"",
    },
    methods:{
        signin:function(){
            var that = this;
            $.ajax({
                url: '/signin/result',
                //type是无所谓的 但是get只能传递1kb数据
                type: 'get',
                // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
                data: {
                    account:that.account,
                    password:that.password,
                },
            }) .done(function(data) {
                console.log(data + "心情不太好");
                if (data == "success"){
                    window.location.href="http://localhost:8080/mylibrary";
                    //设置cookie
                        var cookietime = new Date();
                        cookietime.setTime(cookietime.getTime() + (120 * 60 * 1000));//coockie保存一分钟
                        $.cookie("shan", that.account,{expires:cookietime});
                }else{
                    alert(data);
                }
            })
                .fail(function() {
                    console.log("error");
                })
                .always(function() {
                    // console.log("complete");
                });
        },
    },
});
