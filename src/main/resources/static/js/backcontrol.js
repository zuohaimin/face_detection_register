$("#user_role_control").click(function () {
    $("#e_chart").hide();
    $("#role_resource").hide();
    $("#role_icon_select").hide();
    $("#background_control").show();
    $("#user_select_form").show();
    $("#selecter").hide();
});
$("#role_resource_control").click(function () {
    $("#e_chart").hide();
    $("#background_control").hide();
    $("#role_resource").show();
    $("#role_icon_select").show();
    $("#resource_control").hide();
    $("#selecter_resource").hide();
});

// Transfer.transfer(settings);

//查询用户名
$("#search").click(function () {
    //调用查询用户列表接口
    var userName = $("#user_name").val().trim();
    var data = {
        "userName":userName
    };
    var urlList = initUrlList(urlList);
    $.ajax({
        type: "POST",
        url: urlList[0],
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data) {
            if (data.error_code == 0) {
                var userHtml = "<ol> \n";
                for (var i in data.result) {
                    userHtml += "<li class='info'><span hidden>" + data.result[i].userId + "|</span>" + data.result[i].userName + "</li>";
                }
                ;
                userHtml += "\n</ol>";
                $("#user-search-result").html(userHtml);
            }else {
                alert(data.error_msg);
            }
        }

    });
});

$("#user-search-result").click(function () {
    //当查询列表被点击
    $(".transfer").empty();
    $("#user_select_form").hide();
    $("#selecter").show();
    //填充emege_msg属性
    var userMsg = $("li.info:first").text().split("|");
    $("#element_msg").text(": "+userMsg[1]);
    $("#element_id").text(userMsg[0]);
    //查询系统全部的角色，配置当前用户角色
    var data = {"userId": $("#element_id").text()};
    var result = [];
    var urlList = initUrlList(urlList);
    $.ajax({
        type: "POST",
        url: urlList[1],
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        async : false,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (userRoleResult) {
            if (userRoleResult.error_code == 0) {
                //组装数据
                for (var i=0;i<=userRoleResult.result.length;i++) {
                    var role = userRoleResult.result[i];
                    result[i] = { "language": role.roleName,"value": role.keyId};
                }
                //TODO 组装groupData
            }else {
                alert(userRoleResult.error_msg);
            }
        }

    });
    //组装item数组数据

    var settings = {
        "inputId": "languageInput",
        "data": result,
        "groupData": [],
        "itemName": "language",
        "groupItemName": "groupName",
        "groupListName" : "groupData",
        "container": "transfer",
        "valueName": "value",
        "callable" : function (data, names) {
            console.log("Selected ID：" + data);
            //回调更新接口（直接删除原有的，添加现有的）
            // $("#selectedItemSpan").text(names)
            if (data != null && data.length > 0) {
                var param = {
                    "roleIds": data,
                    "userId": $("#element_id").text()
                };
                var urlList = initUrlList(urlList);

                $.ajax({
                    type: "POST",
                    url: urlList[2],
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader("Authorization", login_token);
                    },
                    contentType: "application/json",
                    data: JSON.stringify(param),
                    success: function (result) {
                        alert("用户角色关系更新成功！")
                    }
                });
            }

        }
    };
    Transfer.transfer(settings);


});

$("#role_add").click(function () {
    //请求资源数据并去除转义
    var urlList = initUrlList(urlList);

    $.ajax({
        type: "POST",
        url: urlList[3],
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        contentType: "application/json",
        async : false,
        success: function (result) {
            if (result.error_code == 0) {
                var reg = /\\/g;
                var selectHtml = result.result;
                selectHtml.replace(reg, '');
                $("#resources-select").html(selectHtml);
                $("#resources-select.selectpicker").selectpicker('refresh');
                //usertable的页面刷新
                // $("#roleAddModal").bootstrapTable('refresh');
                // $("#roleAddModal").on("shown.bs.modal", function () {}).roleAddModal('show');
            }else {
                alert(result.error_msg);
            }
        }
    });
});

$("#roleName").change(function () {
    //判空
    var roleName = $("#roleName").val();
    if (roleName == null || roleName.length < 1){
        alert("角色名不能为空");
    }
    var param = {"roleName": roleName};
    //判重
    var urlList = initUrlList(urlList);

    $.ajax({
        type: "POST",
        url: urlList[4],
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        contentType: "application/json",
        data: JSON.stringify(param),
        success: function (result) {
            if (result.error_code == 0){
                if (!result.result) {
                    alert("角色名重复，请重新填写！");
                    $("#register_ok_image").hide();
                    $("#register_erro_image").show();
                }else {
                    $("#register_ok_image").show();
                    $("#register_erro_image").hide();
                }

            } else {
                alert(result.error_msg);
            }
        }
    });
});

$("#role_add_btn").click(function () {
   //调用添加角色接口
    var methodNameList = $(".filter-option-inner-inner").text().split(",");
    var roleName = $("#roleName").val();
    var param = {"methodNameList":methodNameList,"role":{"roleName":roleName}};
    var urlList = initUrlList(urlList);

    $.ajax({
        type: "POST",
        url: urlList[5],
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        contentType: "application/json",
        data: JSON.stringify(param),
        success: function (result) {
           if (result.error_code == 0){
               alert("添加角色成功");
           } else {
               alert(result.error_msg);
           }
            $("#close_modal").click();
        }
    });
});

$("#role_manage").click(function () {
   //渲染页面
    $("#resource_control").show();
    $("#role_icon_select").hide();

});
$("#search_resource").click(function () {
    var param = {"roleName": $("#role_name").val()};
    var urlList = initUrlList(urlList);

    $.ajax({
        type: "POST",
        url: urlList[6],
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        contentType: "application/json",
        data: JSON.stringify(param),
        success: function (result) {
            //组装数据
            if (result.error_code == 0){
                var innerHtml = "";
                for (var i in result.result) {
                    innerHtml+="<option value=\"";
                    innerHtml+=result.result[i].keyId;
                    innerHtml+="\">";
                    innerHtml+=result.result[i].roleName;
                    innerHtml+="</option>\n";
                }
                $("#role_select").html(innerHtml);
                $("#role_select.selectpicker").selectpicker('refresh');
            } else {
                alert(result.error_msg);
            }
        }
    });
});

$("#manage_resource").click(function () {
    //渲染页面
    $("#selecter_resource").show();
    $("#resource_form").hide();
    $("#role_msg").val($(".filter-option-inner-inner:last").text());
    //查询全部资源分组数据
    //请求data数据
    var transferData;
    var transferGroupData;
    var urlList = initUrlList(urlList);

    $.ajax({
        type: "POST",
        url: urlList[7],
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        contentType: "application/json",
        async : false,
        success: function (result) {
            transferGroupData = result.result;
        }
    });

    $.ajax({
        type: "POST",
        url: urlList[8],
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        contentType: "application/json",
        async : false,
        success: function (result) {
            transferData = result.result;
        }
    });
    var resource_settings = {
        "inputId": "resourceInput",
        "data": transferData,
        "groupData": transferGroupData,
        "itemName": "methodName",
        "groupItemName": "groupName",
        "groupListName" : "groupData",
        "container": "resource_transfer",
        "valueName": "keyId",
        "callable" : function (data, names) {
            console.log("Selected ID：" + data);
            //回调更新接口（直接删除原有的，添加现有的）
            var param = {"resourceId":data,"role":{"roleName": $("#role_msg").val()}};
            var urlList = initUrlList(urlList);

            $.ajax({
                type: "POST",
                url: urlList[9],
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("Authorization", login_token);
                },
                contentType: "application/json",
                data:JSON.stringify(param),
                success: function (result) {
                    if (result.error_code == 0) {
                        alert("角色资源关系配置成功！")
                    } else {
                        alert(result.error_msg);
                    }
                }
            });

        }
    };
    Transfer.transfer(resource_settings);
});

$("#delete_role").click(function () {
   //判空
    var roleName = $(".filter-option-inner-inner:last").text();
    if (roleName == null || roleName.length < 1) {
        alert("角色名不能为空，请先选中需要删除的角色名！")
    }
    var param = [];
    param[0] = roleName;
    //调用删除角色方法
    var urlList = initUrlList(urlList);

    $.ajax({
        type: "POST",
        url: urlList[10],
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        contentType: "application/json",
        data:JSON.stringify(param),
        success: function (result) {
            if (result.error_code == 0) {
                alert("角色删除成功！")
            } else {
                alert(result.error_msg);
            }
        }
    });
});
// var login_token = window.localStorage.getItem("face_detection_token");
// var baseUrl = "http://localhost:8080";
function initUrlList() {
    var urlList = [];
    let requestPath = ["/userRole/selectUsers","/role/selectAllRole","/userRole/updateUserRole","/role/selectAllResource","/role/verifyRoleName","/role/addRole","/role/selectRoleByLikeRoleName","/role/selectAllResource2TransferGroupData","/role/selectAllResource2TansferData","/role/updateRole","/role/deleteRole"];
    for(let i = 0; i < requestPath.length; i++) {
        urlList[i] = baseUrl + requestPath[i];
    }
    return urlList;
}