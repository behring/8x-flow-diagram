import architecture.diagram_inter_process

diagram_inter_process {
    layer("应用服务", "#LightSeaGreen") {
        process("租赁信息应用服务")
        process("推广服务应用服务")
        process("后台管理应用服务")
    }

    layer("核心业务能力", "#HotPink") {
        process("信息推广服务") {
            component("推广报价引擎", "#orange")
        }
        process("预充值服务")
    }

    layer("领域服务", "#orange") {
        process("房屋信息管理系统")
        process("用户账户管理系统")
    }

    layer("第三方系统", "#gray") {
        process("微信支付")
        process("支付宝支付")
        process("银联支付")
        process("ADX数据监测系统")
        process("发票代开服务")
        process("短信发送服务")
    }
} export "./diagrams/inter_process_diagram.png"