import architecture.diagram_inter_process

diagram_inter_process {

    service("前端", "#Cyan") {
        process("思沃租房通用版Web端").call("思沃租房WebBFF","1. GET /web-bff/ads")
        process("思沃租房通用版Web端").call("ADX数据监测系统","5. GET /adx/xxx")
    }

    service("BFF", "#RoyalBlue") {
        process("思沃租房WebBFF").call("租赁信息应用服务","2. GET /rental/ads")
    }

    service("应用服务", "#LightSeaGreen") {
        process("租赁信息应用服务").call("鉴权认证服务","3. GET /auth/check")
        process("租赁信息应用服务").call("房屋信息管理系统","4. GET /listings/ads")
    }

    service("技术组件", "#RoyalBlue") {
        process("三方服务网关").call("信息推广服务","7. POST /promotion-contracts/{id}/promotion/confirmation")
    }

    service("核心业务能力", "#HotPink") {
        process("信息推广服务")
    }

    service("领域服务", "#orange") {
        process("房屋信息管理系统")
        process("鉴权认证服务")
    }

    service("第三方系统", "#gray") {
        process("ADX数据监测系统").call("三方服务网关","6. POST /3rd-party-gateway/ad-data")
    }
} export "./diagrams/tw_renting_inter_process_communication_diagram.png"