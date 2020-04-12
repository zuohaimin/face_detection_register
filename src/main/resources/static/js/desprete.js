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