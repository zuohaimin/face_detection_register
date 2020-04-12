let myChart;
let faceShapeOption = {
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
let ageOption = {
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

let faceValueOption =  {
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

let sexRadioOption =   {
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

$(function () {
    myChart = echarts.init(document.getElementById("echarts_canvas"));
    $("#ageDistributePie").click();
});

$("#ageDistributePie").click(function () {
   //设置图片数据
    $.getJSON("http://localhost:8080/background/ageDistributePie",function (data) {
        ageOption.series[0].data = data.result;
        myChart.clear();
        myChart.setOption(ageOption);
    });
   $("#charts_title").text("年龄分布图");
});

$("#faceShapePie").click(function () {
    //设置图片数据
    $.getJSON("http://localhost:8080/background/faceShapePie",function (data) {
        faceShapeOption.series[0].data = data.result;
        myChart.clear();
        myChart.setOption(faceShapeOption);
    });
    $("#charts_title").text("脸型比例图");
});

$("#faceValueLine").click(function () {
    //设置图片数据
    $.getJSON("http://localhost:8080/background/faceValueLine",function (data) {
        faceValueOption.series[0].data = data.result;
        myChart.clear();
        myChart.setOption(faceValueOption);
    });

    $("#charts_title").text("颜值曲线图");
});

$("#sexRadioChart").click(function () {
    //设置图片数据
    $.getJSON("http://localhost:8080/background/sexRadioChart",function (data) {
        sexRadioOption.series[0].data = data.result;
        myChart.clear();
        myChart.setOption(sexRadioOption);
    });
    $("#charts_title").text("性别比例图");
});

