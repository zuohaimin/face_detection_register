$(function () {
    //默认的情况
    $("#register_page").hide();
    $("#login_page").show();
    $("#login-form").show();

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
        $("#register-form").hide();
        $("#register_image").show();
        getBase64Image($("#register_video"),$("#register_canvas"));
    });
    $("#login_take_photo").click(function(){
        $("#login-form").hide();
        $("#login_image").show();
        getBase64Image($("#login_video"),$("#login_canvas"));
    });

});

function getBase64Image(canvas){
    let video = document.getElementById("login_video");

    let base64Image;
    //首先隐藏表单，显示图片渲染界面
    let constraints = {
        video: {width: 300, height: 300},
        audio: true
    };
    let promise = navigator.mediaDevices.getUserMedia(constraints);
    promise.then(function (MediaStream) {
        video.srcObject = MediaStream;
        video.play();
    }).catch(function (PermissionDeniedError) {
        console.log(PermissionDeniedError);
    })
    // let ctx = canvas.getContext('2d');
    // ctx.drawImage(video, 0, 0, 500, 500);
    // base64Image = canvas.toDataURL().substring(22);
    // //将获取的照片转化成base64URL,存储到隐藏标签
    // $("#base64Image").text(base64Image);
    // console.log(base64Image);
}


getMedia();
let video = document.getElementById("video");
let mediaStream;
function getMedia() {

    let constraints = {
        video: {width: 500, height: 500},
        audio: true
    };
    let getMediaButton = $("#getMedia");
    getMediaButton.click(function () {
        if (getMediaButton.text() === "开启摄像头") {
            let promise = navigator.mediaDevices.getUserMedia(constraints);
            promise.then(function (MediaStream) {
                mediaStream = MediaStream;
                video.srcObject = MediaStream;
                video.play();
            }).catch(function (PermissionDeniedError) {
                console.log(PermissionDeniedError);
            })
            getMediaButton.text("关闭摄像头");
        }else {
            mediaStream.getTracks().forEach(function (item) {  item.stop()});
            getMediaButton.text("开启摄像头");

        }
    });
}

$("#snap").click(function () {
    //获得Canvas对象
    let canvas = document.getElementById("canvas");
    let ctx = canvas.getContext('2d');
    ctx.drawImage(video, 0, 0, 500, 500);
});

