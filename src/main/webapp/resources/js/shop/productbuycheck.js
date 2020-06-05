$(function () {
    var productName = "";

    getProductSellDailyList();

    getList();

    function getList() {
        var listUrl = "/o2o/shopadmin/listuserproductmapsbyshop?pageIndex=1&pageSize=999&productName=" + productName;
        //访问后台，获取该店铺的购买信息列表
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userProductMapList = data.userProductMapList;
                var tempHtml = "";
                userProductMapList.map(function (item, index) {
                    tempHtml += '<div class="row row-productbuycheck">' +
                        '<div class="col-20">' + item.product.productName + '</div>' +
                        '<div class="col-40">' + new Date(item.createTime).Format("yyyy-MM-dd") + '</div>' +
                        '<div class="col-20">' + item.user.name + '</div>' +
                        '<div class="col-20">' + item.point + '</div>' +
                        '</div>';
                });
                $(".productbuycheck-wrap").html(tempHtml);
            }
        });
    }

    /** 根据商品名模糊查询 **/
    $("#search").on("change", function (e) {
        //当在搜索框里输入信息的时候，依据输入的商品名模糊查询该商品的购买记录
        productName = e.target.value;
        //先清空之前的商品购买记录列表
        $(".productbuycheck-wrap").empty();
        //重新加载
        getList();
    });

    /**
     * 获取7天的销量
     */
    function getProductSellDailyList() {
        //获取该店铺商品7天销量的URL
        var listProductSellDailyUrl = "/o2o/shopadmin/listproductselldailyinfobyshop";
        //访问后台
        $.getJSON(listProductSellDailyUrl, function (data) {
            if (data.success) {
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById("chart"));
                //生成静态的Echart信息的部分
                var option = generateStaticEchartPart();
                //遍历商品销量统计列表，动态设定echart的值
                option.legend.data = data.legendData;//商品名列表
                option.xAxis = data.xAxis;//日期
                option.series = data.series;
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
    }

    /**
     * 生成静态的Echart信息部分
     */
    function generateStaticEchartPart() {
        /** echarts逻辑部分 **/
            // 指定图表的配置项和数据
        var option = {
                //提示框，鼠标悬浮交互是的信息提示
                tooltip: {
                    trigger: "axis",
                    axisPointer: { //坐标轴指示器，坐标轴触发有效
                        type: "shadow" //鼠标移动到轴的时候显示阴影
                    }
                },
                //图例，每个图表最多仅有一个图例
                legend: {
                    //图例内容数组，数组项通常为{String}, 每一项代表一个系列的name
                    //data: ["茉香奶茶", "绿茶拿铁", "冰雪奇缘"]
                },
                //直角坐标系内绘图网格
                grid: {
                    left: "3%",
                    right: "4%",
                    bottom: "3%",
                    containLabel: true
                },
                //直角坐标系中横轴数组，数组中每一项代表一条横轴坐标轴
                xAxis: [{
                    //类目型：需要指定类目列表，坐标轴内有且仅有这些指定类目坐标
                    //type: "category",
                    //data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]

                }],
                //直角坐标系中纵轴数组，数组中每一项代表一条纵轴坐标轴
                yAxis: [{
                    type: "value"
                }],

                //驱动图表生成的数据内容数组，数组中每一项为一个系列的选项及数据
                /*series: [
                    {
                        name: "茉香奶茶",
                        type: "bar",
                        data: [120, 132, 101, 134, 290, 230, 220]
                    },
                    {
                        name: "绿茶拿铁",
                        type: "bar",
                        data: [60, 72, 71, 74, 190, 130, 110]
                    },
                    {
                        name: "冰雪奇缘",
                        type: "bar",
                        data: [62, 82, 91, 84, 109, 110, 120]
                    }
                ]*/
            };
        return option;
    }
});
