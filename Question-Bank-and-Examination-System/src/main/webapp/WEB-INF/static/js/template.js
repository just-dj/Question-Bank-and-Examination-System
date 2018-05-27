

var my = new Vue({
    el: '#libary-user',
    data: {
        issignin:false,
        haveaccount:false,
        user:"",
    },
    created: function () {
        if ($.cookie('shan') == null){
            this.issignin = true;
            this.haveaccount  =false;
        }else{
            this.issignin = false;
            this.haveaccount  =true;
            this.user = $.cookie('shan');
        }

    },
    methods:{
        signout:function(){
            $.removeCookie("shan");
            this.issignin = true;
            this.haveaccount=false;
        },
    },
});
var myLibary = new Vue({
  el: '#myLibary',
  data: {
    myAccount:true,
    state:false,
    recomand:false,
    lost:false,
    history:false,
     account:"",
      email:"",
    books:[
        {
            id:'012864854',
            name:"钢铁是怎样炼成的/开车的司机",
            dateGet:'2017-6-19',
            dateSend:'2017-10-13',
            xujie:'1',
            where:'分馆一',
            another:'无',
            action:'续借',
        },
        {
            id:'012864854',
            name:"钢铁是怎样炼成的/开车的司机",
            dateGet:'2017-6-19',
            dateSend:'2017-10-13',
            xujie:'1',
            where:'分馆一',
            another:'无',
            action:'续借',
        },
        {
            id:'012864854',
            name:"钢铁是怎样炼成的/开车的司机",
            dateGet:'2017-6-19',
            dateSend:'2017-10-13',
            xujie:'1',
            where:'分馆一',
            another:'无',
            action:'续借',
        },
        {
            id:'012864854',
            name:"钢铁是怎样炼成的/开车的司机",
            dateGet:'2017-6-19',
            dateSend:'2017-10-13',
            xujie:'1',
            where:'分馆一',
            another:'无',
            action:'续借',
        },
        {
            id:'012864854',
            name:"钢铁是怎样炼成的/开车的司机",
            dateGet:'2017-6-19',
            dateSend:'2017-10-13',
            xujie:'1',
            where:'分馆一',
            another:'无',
            action:'续借',
        },
        {
            id:'012864854',
            name:"钢铁是怎样炼成的/开车的司机",
            dateGet:'2017-6-19',
            dateSend:'2017-10-13',
            xujie:'1',
            where:'分馆一',
            another:'无',
            action:'续借',
        },
        {
            id:'012864854',
            name:"钢铁是怎样炼成的/开车的司机",
            dateGet:'2017-6-19',
            dateSend:'2017-10-13',
            xujie:'1',
            where:'分馆一',
            another:'无',
            action:'续借',
        },
        {
            id:'012864854',
            name:"钢铁是怎样炼成的/开车的司机",
            dateGet:'2017-6-19',
            dateSend:'2017-10-13',
            xujie:'1',
            where:'分馆一',
            another:'无',
            action:'续借',
        },
    ],
    recomands:[
        {
            name:"逻辑之旅",
            author:"[美]王皓",
            info:"浙江大学出版社2009-2",
            date:"2017-11-28",
            statue:"已处理",
            other:"图书馆已向书商订购尚未到馆，请耐心等待！",
        },
        {
            name:"逻辑之旅",
            author:"[美]王皓",
            info:"浙江大学出版社2009-2",
            date:"2017-11-28",
            statue:"已处理",
            other:"您好，非常抱歉，该书查询多处都是无货，图书馆不能订购。读秀搜索可部分阅读该书，也可通过图书馆文献传递等其它方式来获得全本.",
        },
        {
            name:"逻辑之旅",
            author:"[美]王皓",
            info:"浙江大学出版社2009-2",
            date:"2017-11-28",
            statue:"已处理",
            other:"图书馆已向书商订购尚未到馆，请耐心等待！",
        },
    ],
    losts:[
        {
            id:"012657823",
            author:"钢铁是怎样炼成的/开车的司机",
            isbn:"978-7-5686-0052-1",
            date:"2017-14-5",
            price:"78",
            where:"分管一",
            statue:"已缴纳",
        },
        {
            id:"012657823",
            author:"钢铁是怎样炼成的/开车的司机",
            isbn:"978-7-5686-0052-1",
            date:"2017-14-5",
            price:"78",
            where:"分管一",
            statue:"未缴纳",
        },
    ],
    historys:[
        {
            num:"1",
            id:"01286594",
            author:"钢铁是怎样炼成的/开车的司机",
            getdate:"2016-07-12",
            senddate:"2017-2-12",
            amount:"1",
            where:"分馆一",
        },
        {
            num:"2",
            id:"01286594",
            author:"钢铁是怎样炼成的/开车的司机",
            getdate:"2016-07-12",
            senddate:"2017-2-12",
            amount:"0",
            where:"分馆一",
        },
        {
            num:"3",
            id:"01286594",
            author:"钢铁是怎样炼成的/开车的司机",
            getdate:"2016-07-12",
            senddate:"2017-2-12",
            amount:"1",
            where:"分馆一",
        },
        {
            num:"4",
            id:"01286594",
            author:"钢铁是怎样炼成的/开车的司机",
            getdate:"2016-07-12",
            senddate:"2017-2-12",
            amount:"0",
            where:"分馆一",
        },
        {
            num:"5",
            id:"01286594",
            author:"钢铁是怎样炼成的/开车的司机",
            getdate:"2016-07-12",
            senddate:"2017-2-12",
            amount:"1",
            where:"分馆二",
        },
        {
            num:"6",
            id:"01286594",
            author:"钢铁是怎样炼成的/开车的司机",
            getdate:"2016-07-12",
            senddate:"2017-2-12",
            amount:"0",
            where:"分馆一",
        },
        {
            num:"7",
            id:"01286594",
            author:"钢铁是怎样炼成的/开车的司机",
            getdate:"2016-07-12",
            senddate:"2017-2-12",
            amount:"0",
            where:"分馆一",
        },
        {
            num:"8",
            id:"01286594",
            author:"钢铁是怎样炼成的/开车的司机",
            getdate:"2016-07-12",
            senddate:"2017-2-12",
            amount:"0",
            where:"分馆一",
        },
        {
            num:"9",
            id:"01286594",
            author:"钢铁是怎样炼成的/开车的司机",
            getdate:"2016-07-12",
            senddate:"2017-2-12",
            amount:"0",
            where:"分馆一",
        },
        {
            num:"10",
            id:"01286594",
            author:"钢铁是怎样炼成的/开车的司机",
            getdate:"2016-07-12",
            senddate:"2017-2-12",
            amount:"0",
            where:"分馆一",
        },
    ],
  },
    created: function () {
      if ($.cookie('shan') == null){
          console.log(1 + "哈哈");
      }else{
          this.account = $.cookie('shan');
          this.email = $.cookie('email');
      }

    },
  methods:{
    recover:function(){
        this.myAccount = false;
        this.state = false;
        this.recomand = false;
        this.lost = false;
        this.history = false;
        scrollTo(0,0);
        // window.scrollTo(0,document.body.scrollHeight);
    },
    qmyaccount:function(){
        if(this.myAccount != true){
            this.recover();
            this.myAccount = true;
        }  
    },
    qstate:function(){
        if(this.state != true){
            this.recover();
            this.state = true;
        }  
    },
    qrecomand:function(){
        if(this.recomand != true){
            this.recover();
            this.recomand = true;
        }  
    },
    qlost:function(){
        if(this.lost != true){
            this.recover();
            this.lost = true;
        }  
    },
    qhistory:function(){
        if(this.history != true){
            this.recover();
            this.history = true;
        }  
    },
  },
});






var data1 = [
            {name:'计算机',value:45},
            {name:'艺术',value:17},
            {name:'小说',value:15},
            {name:'历史',value:23},
        ];


new sChart('canvas1', 'pie', data1, {
            title: '借阅分类分布',
            bgColor: '#ffffff',
            titleColor: '#283E56',
            legendColor: '#283E56'
        });

var data2 = [
            {name:'2015',value:33},
            {name:'2016',value:12},
            {name:'2017',value:25}
        ];
new sChart('canvas2', 'bar', data2, {
            title: '借阅时间分布',
            bgColor: '#ffffff',
            titleColor: '#283E56',
            fillColor: '#283E56',
            axisColor: '#283E56',
            contentColor: '#283E56'
        });
