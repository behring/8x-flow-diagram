import architecture.diagram_inter_process
import org.junit.Test
import kotlin.test.BeforeTest

internal class diagram_architecture_test {
    @Test
    fun create_hello_world_inter_process_diagram() {
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
}