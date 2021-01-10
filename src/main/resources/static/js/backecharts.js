// let myEChart;
var faceShapeOption = {
    backgroundColor: '#2c343c',
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    series: [
        {
            name: '访问来源',
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data: [
                {value: 335, name: '直接访问'},
                {value: 310, name: '邮件营销'},
                {value: 274, name: '联盟广告'},
                {value: 235, name: '视频广告'},
                {value: 400, name: '搜索引擎'}
            ],
            roseType: 'radius',
            label: {
                color: 'rgba(255, 255, 255, 0.3)'
            },
            labelLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 0.3)'
                },
                smooth: 0.2,
                length: 10,
                length2: 20
            },
            itemStyle: {
                color: '#c23531',
                shadowBlur: 200,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            },

            animationType: 'scale',
            animationEasing: 'elasticOut',
            animationDelay: function (idx) {
                return Math.random() * 200;
            }
        }
    ]
};
var ageOption = {
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
        orient: 'vertical',
        left: 10,
        data: ["少年", "青少年", "青年","中年","中老年","老年"]
    },
    series: [{
        name:"年龄分布",
        radius: ['50%', '70%'],
        label: {
            show: false,
            position: 'center'
        },
        emphasis: {
            label: {
                show: true,
                fontSize: '30',
                fontWeight: 'bold'
            }
        },
        data: [{"name":"青年","value":2},{"name":"中老年","value":1},{"name":"青少年","value":0},{"name":"少年","value":0},{"name":"老年","value":0},{"name":"中年","value":0}],
        type: 'pie'
    }]
};

var faceValueOption =  {
    tooltip: {
        trigger:"axis",

    },
    xAxis: {
        name:"颜值",
        type: 'value',
    },
    yAxis: {
        name:"数量",
        type: 'value'
    },
    series: [{
        name:"数量",
        data: [[ 60, 5],[80, 100],[90, 5]],
        type: 'line',
        smooth: true
    }]
};

var sexRadioOption =   {
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c}人 ({d}%)'
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['男性', '女性']
    },
    series: [
        {
            name: '男女比例',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
                {value: 335, name: '男性'},
                {value: 310, name: '女性'},

            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
var login_token;
var baseUrl = "http://localhost:8080";
var urlList = initUrlList();

$(function () {
    myEChart = echarts.init(document.getElementById("echarts_canvas"));
    login_token = window.localStorage.getItem("face_detection_token");
    $("#ageDistributePie").click();
    //显示用户头像，以及用户名
    showUserIcon(login_token);
});

function showUserIcon(login_token){
    $.ajax({
        type: "GET",
        dataType: "json",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        url: urlList[4],
        success: function (data) {
            if (data.error_code == 0) {
               $("#sidebar_user_icon").attr("src",data.result.base64Image);
               $("#sidebar_user_name").html(data.result.userName);
            } else {
                // alert(data.error_msg);
                window.location.href = baseUrl;
            }

        }
    });
}


$("#ageDistributePie").click(function () {
    $("#e_chart").show();
    $("#background_control").hide();
   //设置图片数据
    $.ajax({
        type: "GET",
        dataType: "json",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        url: urlList[0],
        success: function (data) {
            if (data.error_code == 0) {
                ageOption.series[0].data = data.result;
                myEChart.clear();
                myEChart.setOption(ageOption);
            } else {
                // alert(data.error_msg);
                window.location.href = baseUrl;
            }

        }
    });
    // $.getJSON("http://localhost:8080/background/ageDistributePie",function (data) {
    //     ageOption.series[0].data = data.result;
    //     myEChart.clear();
    //     myEChart.setOption(ageOption);
    // });
   $("#charts_title").text("年龄分布图");
});
$("#faceShapePie").click(function () {
    $("#e_chart").show();
    $("#background_control").hide();
    //设置图片数据
    $.ajax({
        type: "GET",
        dataType: "json",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        url: urlList[1],
        success: function (data) {
            if (data.error_code == 0) {
                faceShapeOption.series[0].data = data.result;
                myEChart.clear();
                myEChart.setOption(faceShapeOption);
            } else {
                // alert(data.error_msg);
                window.location.href = baseUrl;
            }

        }
    });
    // $.getJSON("http://localhost:8080/background/faceShapePie",function (data) {
    //     faceShapeOption.series[0].data = data.result;
    //     myEChart.clear();
    //     myEChart.setOption(faceShapeOption);
    // });
    $("#charts_title").text("脸型比例图");
});

$("#faceValueLine").click(function () {
    $("#e_chart").show();
    $("#background_control").hide();
    //设置图片数据
    $.ajax({
        type: "GET",
        dataType: "json",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        url: urlList[2],
        success: function (data) {
            if (data.error_code == 0) {
                faceValueOption.series[0].data = data.result;
                myEChart.clear();
                myEChart.setOption(faceValueOption);
            } else {
                // alert(data.error_msg);
                window.location.href = baseUrl;
            }

        }
    });
    // $.getJSON("http://localhost:8080/background/faceValueLine",function (data) {
    //     faceValueOption.series[0].data = data.result;
    //     myEChart.clear();
    //     myEChart.setOption(faceValueOption);
    // });

    $("#charts_title").text("颜值曲线图");
});


$("#sexRadioChart").click(function () {
    $("#e_chart").show();
    $("#background_control").hide();
    //设置图片数据
    $.ajax({
        type: "GET",
        dataType: "json",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", login_token);
        },
        url: urlList[3],
        success: function (data) {
            if (data.error_code == 0) {
                sexRadioOption.series[0].data = data.result;
                myEChart.clear();
                myEChart.setOption(sexRadioOption);
            } else {
                // alert(data.error_msg);
                window.location.href = baseUrl;
            }

        }
    });
    // $.getJSON("http://localhost:8080/background/sexRadioChart",function (data) {
    //     sexRadioOption.series[0].data = data.result;
    //     myEChart.clear();
    //     myEChart.setOption(sexRadioOption);
    // });
    $("#charts_title").text("性别比例图");
});
$("#log_out").click(function () {
    window.localStorage.removeItem("face_detection_token");
    window.location.href = baseUrl;
});
function initUrlList() {
    var urlList = [];
    let path = ["/background/ageDistributePie","/background/faceShapePie","/background/faceValueLine","/background/sexRadioChart","/userRole/getCurrentUserMsg"];
    for(let i = 0; i < path.length; i++) {
        urlList[i] = baseUrl + path[i];
    }
    return urlList;

}