import dsl.diagram_8x_flow
import org.junit.Test

internal class diagram_8x_flowTest {
    @Test
    fun should_build_sucess() {
        diagram_8x_flow {

            context("预充值协议上下文") {
                val houseAgent = participant_party("房产经纪人")

                contract("预充值协议") {
                    val prepaidUser = role_party("预充值用户") party houseAgent
                    val rentingPlatform = role_party("思沃租房") party houseAgent
                    key_timestamps("签订时间")

                    fulfillment("预充值") {

                        request(rentingPlatform) {
                            key_timestamps("创建时间","过期时间")
                            key_data("金额")
                        }

                        confirmation(prepaidUser) {
                            key_timestamps("创建时间")
                            key_data("金额")

                           val evidence = evidence("支付凭证") {
                                key_timestamps("支付时间")
                                key_data("金额")
                            }
                            party(evidence)
                        }
                    }
                }
            }
        }
    }
}