   /* var demo= [
               {"id":"1","name":"dashboard","url":"/base/session_timeout.html","parentId":"0","icon":"glyphicon glyphicon-dashboard","description":""},
               {"id":"4","name":"system","url":"/base/session_timeout.html","parentId":"0","icon":"glyphicon glyphicon-cog","description":""},
               {"id":"5","name":"menuManage","url":"/base/menuManage.html","parentId":"4","icon":"glyphicon glyphicon-th-list","description":""},
               {"id":"6","name":"urlManage","url":"/base/urlManage.html","parentId":"4","icon":"glyphicon glyphicon-send","description":""},
               {"id":"7","name":"roleManage","url":"/base/roleManage.html","parentId":"4","icon":"glyphicon glyphicon-book","description":""},
               {"id":"8","name":"userManage","url":"/base/userManage.html","parentId":"4","icon":"glyphicon glyphicon-user","description":""}
              ];*/

    var menu;

    function transferMenuData(data){
        transferItemData(data,"0",0);
        return data;
    };

    function transferItemData(data,id,level){
        level = level +1;
        for(var i=0;i<data.length;i++){
            if(data[i].parentId==id){
               data[i].level=level;
               for(var j=0;j<data.length;j++){
                  data[i].isParent="0";
                  if(data[j].parentId==data[i].id){
                      j=data.length;
                      data[i].isParent="1";
                  }
               }
               if(data[i].isParent=="1"){
                    transferItemData(data,data[i].id,level);
               }
            }
        }
    };

    function buildMenu(data){
        $("#menuCnt").empty();
        buildMenuItem(data,"0",0);
    };

    function buildMenuItem(data,id){
        for(var i=0;i<data.length;i++){
            if(data[i].parentId==id){
               var menuItemCnt = $("<div class=\"menuItemCnt\"></div>");
               var menuItem = $("<div class=\"menuItem\" id=\"menuItem_"+data[i].id+"\"></div>");
               var selectFlag = $("<div class=\"selectFlag\" style=\"\"></div>");
               var left=10*(data[i].level-1);
               var icon = $("<div style=\"margin-left:"+left+"px;width:46px;height:40px;float:left;text-align:center\"><span class=\""+data[i].icon+"\"></span></div>");
               var width = 120 -left;
               var name =$("<div class=\"menu_item_name\" style=\"width:"+width+"px;height:40px;float:left;text-align:left;overflow:hidden\">"+data[i].name+"</div>");
               menuItem.append(selectFlag);
               menuItem.append(icon);
               menuItem.append(name);
               menuItemCnt.append(menuItem);
               if(data[i].isParent=="1"){
                    var subMenuFlag = $("<div class=\"subMenuFlag\" style=\"font-size:8px;width:30px;height:40px;float:left;text-align:center\"><span class=\"glyphicon glyphicon-menu-left\"></span></div>");
                    var childrenItemCnt = $("<div class=\"childrenItemCnt\" style=\"display:none\"></div>");
                    menuItem.append(subMenuFlag);
                    menuItemCnt.append(childrenItemCnt);

                     menuItem.click(function(event){
                         $(".selectFlag").css({"background":"none"});
                          $(this).find(".selectFlag").css({"background":"#367fa9"});
                          if($(this).next().css("display")=='none'){
                             $(this).find(".subMenuFlag").find("span").removeClass("glyphicon glyphicon-menu-left");
                             $(this).find(".subMenuFlag").find("span").addClass("glyphicon glyphicon-menu-down");
                             $(this).next().show();
                          }else{
                             $(this).find(".subMenuFlag").find("span").removeClass("glyphicon glyphicon-menu-down");
                             $(this).find(".subMenuFlag").find("span").addClass("glyphicon glyphicon-menu-left");
                             $(this).next().hide();
                          }
                   });
               }else{
                   menuItem.click(function(event){
                        var btnId = $(this).eq(0).attr("id");
                        var splits = btnId.split('_');
                         var thisId = splits[1];
                         for(var c=0;c<menu.length;c++){
                             if(menu[c].id==thisId){
                                 $(".selectFlag").css({"background":"none"});
                                 $(this).find(".selectFlag").css({"background":"#367fa9"});
                                 if(menu[c].description.indexOf("#OPEN#")>=0){
                                    window.open(menu[c].url);
                                 }else{
                                    loadUrl(menu[c].id);
                                 }
                             }
                         }
                   });
               }
               if(id=="0"){
                    $("#menuCnt").append(menuItemCnt);
               }else{
                    $("#menuItem_"+id).next().append(menuItemCnt);
               }
               buildMenuItem(data,data[i].id);
            }
        }
    };

    var navBar;

    function appendNavBar(html){
        $("#hrefUl").empty();
        $("#hrefUl").append($(navBar));
        $("#hrefUl").append(html);
    }

    function home(){
        $("#hrefUl").empty();
        var homeTab = $("<li ><a  class=\"breadcrumb-item\" href=\"javascript:home()\">home</a></li>");
        $("#hrefUl").append(homeTab);
        navBar = $("#hrefUl").html();
        $("#frameBody").attr("src",basePath+'/rest/pages/home');
    }

    function loadUrl(menuId){
        for(var i=0;i<menu.length;i++){
           if(menu[i].id==menuId){
              $("#hrefUl").empty();
               var homeTab = $("<li ><a  class=\"breadcrumb-item\" href=\"javascript:home()\">home</a></li>");
               $("#hrefUl").append(homeTab);
               var tmpTab = $("<li ><a  class=\"breadcrumb-item\"  href=\"javascript:loadUrl('" + menu[i].id + "')\">" + menu[i].name + "</a></li>");
               $("#hrefUl").append(tmpTab);
               navBar = $("#hrefUl").html();
              $("#frameBody").attr("src",menu[i].url);
           }
        }
    };

    function initMenu(){
          $("#userBtn").click(function(event){
                       $("#userPad").show();
                  });
                  $("#userBtn").blur(function(){
                       $("#userPad").hide();
          });


         $("#menu_switch").click(function(event){
               if($("#menuBlock").width()==50){
                   $("#menuBlock").animate({"width":"200px"},300,function(){
                        $("#expandTitle").show();
                        $(".menu_item_name").show();
                        $(".subMenuFlag").show();
                        $(".subMenuFlag").find("span").removeClass("glyphicon glyphicon-menu-down");
                        $(".subMenuFlag").find("span").addClass("glyphicon glyphicon-menu-left");
                   });
                   $("#contentBlock").animate({"left":"200px"},300,function(){
                   });
               }else if($("#menuBlock").width()==200){
                   $("#expandTitle").hide();
                   $(".menu_item_name").hide();
                   $(".subMenuFlag").hide();
                   $(".childrenItemCnt").hide();
                   $("#menuBlock").animate({"width":50+"px"},300,function(){
                   });
                    $("#contentBlock").animate({"left":"50px"},300,function(){
                   });
               }
          });
    }