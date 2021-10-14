import architecture.diagram_inter_process
import org.junit.Test
import kotlin.test.BeforeTest

internal class diagram_architecture_test {
    @Test
    fun create_inter_process_diagram() {
        diagram_inter_process {
            layer("应用服务") {
                process("租赁信息应用服务")
                process("租赁信息应用服务")
                process("后台管理应用服务")
            }

            layer("核心业务能力") {
                process("信息推广服务")
                process("预充值服务") {
                    component("推广报价引擎")
                }
            }

            layer("领域服务") {

            }

            layer("第三方系统") {

            }
        } export "./diagrams/inter_process_diagram.png"
    }

    @Test
    fun create_tw_renting_inter_process_diagram() {
        diagram_inter_process {
            layer("前端", "#Cyan") {
                process("思沃租房通用版Web端")
                process("思沃租房App个人版Android端")
                process("思沃租房App个人版IOS端")
                process("思沃租房App经纪人版Android端")
                process("思沃租房App经纪人版IOS端")
                process("后台管理系统Web端")
            }

            layer("BFF", "#RoyalBlue") {
                process("思沃租房WebBFF")
                process("思沃租房MobileBFF")
            }

            layer("技术组件", "#RoyalBlue") {
                process("支付网关")
                process("三方服务网关")
            }

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
                process("鉴权认证服务")
            }

            layer("第三方系统", "#gray") {
                process("微信支付")
                process("支付宝支付")
                process("银联支付")
                process("ADX数据监测系统")
                process("发票代开服务")
                process("短信发送服务")
            }
        } export "./diagrams/tw_renting_inter_process_diagram.png"
    }

    @Test
    fun create_tw_renting_inter_process_communication_diagram() {
        diagram_inter_process {
            layer("前端", "#Cyan") {
                process("思沃租房通用版Web端").call("思沃租房WebBFF", "1. GET /web-bff/ads")
                process("思沃租房通用版Web端").call("ADX数据监测系统", "5. GET /adx/xxx")
            }

            layer("BFF", "#RoyalBlue") {
                process("思沃租房WebBFF").call("租赁信息应用服务", "2. GET /rental/ads")
            }

            layer("应用服务", "#LightSeaGreen") {
                process("租赁信息应用服务").call("鉴权认证服务", "3. GET /auth/check")
                process("租赁信息应用服务").call("房屋信息管理系统", "4. GET /listings/ads")
            }

            layer("技术组件", "#RoyalBlue") {
                process("三方服务网关").call("信息推广服务", "7. POST /promotion-contracts/{id}/promotion/confirmation")
            }

            layer("核心业务能力", "#HotPink") {
                process("信息推广服务")
            }

            layer("领域服务", "#orange") {
                process("房屋信息管理系统")
                process("鉴权认证服务")
            }

            layer("第三方系统", "#gray") {
                process("ADX数据监测系统").call("三方服务网关", "6. POST /3rd-party-gateway/ad-data")
            }
        } export "./diagrams/tw_renting_inter_process_communication_diagram.png"
    }
}