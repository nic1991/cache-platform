$("#sync-ajax").click(function(){
    ajax.post("/demo/postList",{"aa":12},function(){
        console.log(1);
    },true);
});

$("#async-ajax").click(function(){
    ajax.async_post("/demo/postList",{"aa":12},function(){console.log(1);});
});