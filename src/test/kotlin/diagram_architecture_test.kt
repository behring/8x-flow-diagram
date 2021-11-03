import architecture.diagram_inter_process
import architecture.diagram_intra_process
import common.Diagram.Color.BLUE
import common.Diagram.Color.DARK_GREY
import common.Diagram.Color.GREEN
import common.Diagram.Color.GREY
import common.Diagram.Color.PINK
import common.Diagram.Color.PURPLE
import common.Diagram.Color.WAVE_BLUE
import common.Diagram.Color.YELLOW
import org.junit.Test

internal class diagram_architecture_test {
    @Test
    fun create_inter_process_diagram() {
        diagram_inter_process("链家系统架构图") {
            val applicationService = service("应用服务", GREEN) {
                process("租赁信息应用服务")
                process("推广服务应用服务")
                process("后台管理应用服务")
            }

            val coreBusinessService = service("核心业务能力", PINK) {
                process("信息推广服务") {
                    component("推广报价引擎", YELLOW)
                }
                process("预充值服务")
            }

            val domainService = service("领域服务", YELLOW) {
                process("房屋信息管理系统")
                process("用户账户管理系统")
            }

            val thirdSystemService = service("第三方系统", DARK_GREY) {
                process("微信支付")
                process("支付宝支付")
                process("银联支付")
                process("ADX数据监测系统")
                process("发票代开服务")
                process("短信发送服务")
            }

            applicationService above coreBusinessService
            applicationService above domainService
            coreBusinessService above thirdSystemService

        } export "./diagrams/inter_process_diagram.png"
    }

    @Test
    fun create_lianjia_inter_process_diagram() {
        diagram_inter_process {
            service("前端", PURPLE) {
                process("链家租房通用版 Web端")
                process("链家租房App个人版 Android端")
                process("链家租房App个人版 IOS端")
                process("链家租房App经纪人版 Android端")
                process("链家租房App经纪人版 IOS端")
                process("后台管理系统 Web端")
            }

            service("BFF", WAVE_BLUE) {
                process("链家租房WebBFF")
                process("链家租房MobileBFF")
            }

            service("技术组件", BLUE) {
                process("支付网关")
                process("三方服务网关")
            }

            service("应用服务", GREEN) {
                process("租赁信息应用服务")
                process("推广服务应用服务")
                process("后台管理应用服务")
            }

            service("核心业务能力", PINK) {
                process("信息推广服务") {
                    component("推广报价引擎", YELLOW)
                }
                process("预充值服务")
            }

            service("领域服务", YELLOW) {
                process("房屋信息管理系统")
                process("用户账户管理系统")
                process("鉴权认证服务")
            }

            service("第三方系统", DARK_GREY) {
                process("微信支付")
                process("支付宝支付")
                process("银联支付")
                process("ADX数据监测系统")
                process("发票代开服务")
                process("短信发送服务")
            }
        } export "./diagrams/lianjia_inter_process_diagram.png"
    }

    @Test
    fun create_lianjia_inter_process_communication_diagram() {
        diagram_inter_process {
            service("前端", GREEN) {
                process("链家租房通用版 Web端").call("链家租房Web BFF", "1. GET /web-bff/ads")
                process("链家租房通用版 Web端").call("ADX数据监测系统", "5. GET /adx/xxx")
            }

            service("BFF", PURPLE) {
                process("链家租房Web BFF").call("租赁信息应用服务", "2. GET /rental/ads")
            }

            service("应用服务", WAVE_BLUE) {
                process("租赁信息应用服务").call("鉴权认证服务", "3. GET /auth/check")
                process("租赁信息应用服务").call("房屋信息管理系统", "4. GET /listings/ads")
            }

            service("技术组件", BLUE) {
                process("三方服务网关").call("信息推广服务", "7. POST /promotion-contracts/{id}/promotion/confirmation")
            }

            service("核心业务能力", PINK) {
                process("信息推广服务")
            }

            service("领域服务", YELLOW) {
                process("房屋信息管理系统")
                process("鉴权认证服务")
            }

            service("第三方系统", DARK_GREY) {
                process("ADX数据监测系统").call("三方服务网关", "6. POST /3rd-party-gateway/ad-data")
            }
        } export "./diagrams/lianjia_inter_process_communication_diagram.png"
    }

    @Test
    fun create_intra_process_diagram_diagram() {
        diagram_intra_process("链家Android应用进程内架构图") {
            layer("应用层", PINK) {
                component("Activity").call("ViewModel", "方法调用")
                component("ViewModel").call("Presenter", "方法调用")
                component("Service").call("Presenter", "方法调用")
            }

            layer("业务层", YELLOW) {
                component("Presenter").call("Repository", "方法调用")
            }

            layer("数据层", GREEN) {
                val repo = component("Repository")
                repo.call("DBDataSource", "方法调用")
                repo.call("RemoteDataSource", "方法调用")

                component("DBDataSource").call("SqliteDB", "读写")
                component("RemoteDataSource").call("MobileBFF", "Http请求")
            }

            process("PushService").call("Service", "消息推送")
            process("MobileBFF")
            process("SqliteDB")

        } export "./diagrams/intra_process_diagram.png"
    }
}