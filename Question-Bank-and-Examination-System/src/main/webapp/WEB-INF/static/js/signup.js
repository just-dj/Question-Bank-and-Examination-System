var signupForm = new Vue({
    el:"#signup-form",

    data:{
        account:'',
        email:'',
        password:'',
    },

    methods:{
        changeway:function(){
            // console.log("改变登录方式");
            this.ok = !this.ok;
        },
        signup:function(){
            var that = this;
            $.ajax({
                url: '/signup/result',
                //type是无所谓的 但是get只能传递1kb数据
                type: 'get',
                // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
                data: {
                    account:that.account,
                    email:that.email,
                    password:that.password,
                },
            }) .done(function(data) {
                    console.log(data + "心情不太好");
                    if (data == "success"){
                        window.location.href="http://localhost:8080/mylibrary";
                        //设置cookie
                            console.log("cookie 不存在.");
                            var cookietime = new Date();
                            cookietime.setTime(cookietime.getTime() + (120 * 60 * 1000));//coockie保存一分钟
                            $.cookie("shan", that.account,{expires:cookietime});
                            $.cookie("email", that.email,{expires:cookietime});
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


