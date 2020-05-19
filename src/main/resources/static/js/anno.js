$(function () {
    //默认的情况
    $("#register_page").hide();
    $("#login_page").show();
    $("#login-form").show();
});
//处理登陆注册和注册导航页的跳转问题
$("#registerButton").click(function () {
    $("#register_page").show();
    $("#register-form").show();
    $("#login_page").hide();
});
$("#loginButton").click(function () {
    $("#register_page").hide();
    $("#login_page").show();
    $("#login-form").show();
});

$("#register_take_photo").click(function(){
    //设置用户名表单校验
    if (verifyUserName($("#register_username").val())) {
        $("#register-form").hide();
        $("#register_image").show();
        $("#register_snap").show();
        $("#register_video").show();
        $("#register_canvas").hide();
        getBase64Image("register_video");
    }
});
$("#login_take_photo").click(function(){
    $("#login-form").hide();
    $("#login_image").show();
    $("#login_snap").show();
    $("#login_video").show();
    $("#login_canvas").hide();
    getBase64Image("login_video");
});

// $("#login_snap").click(snapAction("login_canvas","login_video","login_image","login-form"));
// $("#register_snap").click(snapAction("register_canvas","register_video","register_image","register-form"));

let validUserName;
function verifyUserName(userName){
    var isContinue;
    $("#dashboard").empty();
    if (userName === null || userName.trim() === ""){
        $("#dashboard").text("用户名不能为空");
        $("#register_erro_image").show();
        $("#register_ok_image").hide();
        isContinue = false;
    } else {
        var requestParam = {"userName":userName};
        $.ajax({
            type:"POST",
            url: "http://localhost:8080/anno/verifyUserName",
            async : false,
            contentType: "application/json",
            data:JSON.stringify(requestParam),
            success:function (data) {
                if (data.error_msg === "SUCCESS") {
                    // 改变表单样式 TODO data.result
                    if (data.result === true) {
                        $("#register_ok_image").show();
                        $("#register_erro_image").hide();
                        isContinue = true;
                        validUserName = userName;
                    }else {
                        $("#register_erro_image").show();
                        $("#register_ok_image").hide();
                        isContinue=false;
                    }
                } else {
                    $("#dashboard").text(data.error_msg);
                    $("#register_erro_image").show();
                    $("#register_ok_image").hide();
                    isContinue=false;
                }
            }
        })
    }
    return isContinue;

};

let mediaStream;
function getBase64Image(elementId){
    let video = document.getElementById(elementId);


    //首先隐藏表单，显示图片渲染界面
    let constraints = {
        video: {width: 390, height: 305},
        audio: true
    };
    let promise = navigator.mediaDevices.getUserMedia(constraints);
    promise.then(function (MediaStream) {
        video.srcObject = MediaStream;
        mediaStream = MediaStream;
        video.play();
    }).catch(function (PermissionDeniedError) {
        console.log(PermissionDeniedError);
    })
}

$("#login_snap").click(function () {
    $("#dashboard").empty();
    let base64Image;
    //获得Canvas对象
    let canvas = document.getElementById("login_canvas");
    let loginVideo = document.getElementById("login_video");
    let ctx = canvas.getContext('2d');
    $("#login_video").hide();
    ctx.drawImage(loginVideo, loginVideo.offsetLeft, loginVideo.offsetHeight,390,305);
    $("#login_canvas").show();
    base64Image = canvas.toDataURL().substring(22);
    var imageRequestParam = {"base64Image":base64Image};
    $.ajax({
        url: "http://localhost:8080/anno/verifyBase64Image",
        contentType: "application/json",
        type: "POST",
        data: JSON.stringify(imageRequestParam),
        success:function (data) {
            console.log(data);
            var errmsg = data.error_msg;
            if (errmsg === "SUCCESS") {
                //隐藏图片，显示登陆页面
                $("#login_image").hide();
                $("#login-form").show();
                $("#dashboard").text("图像信息采集成功");
                mediaStream.getTracks().forEach(function (item) {  item.stop()});
            } else {
                //将错误信息写到看板
                $("#dashboard").text(errmsg);
                $("#login_video").show();
                $("#login_canvas").hide();
            }

        }
    });
    //将获取的照片转化成base64URL,存储到隐藏标签
    $("#base64Image").text(base64Image);
    console.log(base64Image);
});


$("#register_snap").click(function () {
    $("#dashboard").empty();
    let base64Image;
    //获得Canvas对象
    let canvas = document.getElementById("register_canvas");
    let loginVideo = document.getElementById("register_video");
    let ctx = canvas.getContext('2d');
    $("#register_video").hide();
    ctx.drawImage(loginVideo, loginVideo.offsetLeft, loginVideo.offsetHeight,390,305);
    $("#register_canvas").show();
    base64Image = canvas.toDataURL().substring(22);
    var imageRequestParam = {"base64Image":base64Image};
    $.ajax({
        url: "http://localhost:8080/anno/verifyBase64Image",
        contentType: "application/json",
        type: "POST",
        data: JSON.stringify(imageRequestParam),
        success:function (data) {
            console.log(data);
            var errmsg = data.error_msg;
            if (errmsg === "SUCCESS") {
                //隐藏图片，显示登陆页面
                $("#register_image").hide();
                $("#register-form").show();
                $("#dashboard").text("图像信息采集成功");
                mediaStream.getTracks().forEach(function (item) {  item.stop()});
            } else {
                //将错误信息写到看板
                $("#dashboard").text(errmsg);
                $("#register_video").show();
                $("#register_canvas").hide();
            }

        }
    });
    //将获取的照片转化成base64URL,存储到隐藏标签
    $("#base64Image").text(base64Image);
    console.log(base64Image);
});


$("#login").click(function () {
    $("#dashboard").empty();
    var base64Image =  $("#base64Image").text();
    if ( base64Image=== null || base64Image.trim() === "") {
        $("#dashboard").text("请先采集人像信息");
        return;
    }
    var requestParam = {"base64Image":base64Image};
    $.ajax({
        url: "http://localhost:8080/anno/login",
        contentType: "application/json",
        type: "POST",
        data: JSON.stringify(requestParam),
        success:function (data) {
            console.log(data);
            var errmsg = data.error_msg;
            if (errmsg === "SUCCESS") {
               //TODO 进入欢迎页面（个人信息管理页面）
                $("#base64Image").empty();
                localStorage.setItem("face_detection_token",data.result);
                $("#dashboard").text("登陆成功");
                window.location.href = "http://localhost:8080/home";
                console.log(data.result);
            } else {
                //将错误信息写到看板
                $("#dashboard").text(errmsg);
            }

        }
    });

});

$("#register").click(function () {
    $("#dashboard").empty();
    var base64Image =  $("#base64Image").text();
    if ( base64Image=== null || base64Image.trim() === "") {
        $("#dashboard").text("请先采集人像信息");
        return;
    }
    var requestParam = {"base64Image":base64Image,"userName":validUserName};
    $.ajax({
        url: "http://localhost:8080/anno/register",
        contentType: "application/json",
        type: "POST",
        data: JSON.stringify(requestParam),
        success:function (data) {
            console.log(data);
            var errmsg = data.error_msg;
            if (errmsg === "SUCCESS") {
                $("#dashboard").text("注册成功，请前往注册页面登陆");
                $("#base64Image").empty();
            } else {
                //将错误信息写到看板
                $("#dashboard").text(errmsg);
            }

        }
    });

});


// $("#snap").click(function () {
//     //获得Canvas对象
//     let canvas = document.getElementById("canvas");
//     let ctx = canvas.getContext('2d');
//     ctx.drawImage(video, 0, 0, 500, 500);
// });


