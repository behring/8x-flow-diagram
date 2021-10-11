import dsl.diagram_8x_flow
import org.junit.Test

internal class diagram_8x_flowTest {
    @Test
    fun should_build_success() {
        // 创建8x flow建模图
        diagram_8x_flow {
            // 定义一个上下文
            context("预充值协议上下文") {
                // 创建一个参与方party
                val houseAgent = participant_party("房产经纪人")
                // 定义一个合约
                contract("预充值协议") {
                    // 创建2个角色party并扮演参与方party
                    val prepaidUser = role_party("预充值用户") party houseAgent
                    val rentingPlatform = role_party("思沃租房") party houseAgent

                    // 指定合约的关键时间
                    key_timestamps("签订时间")

                    // 创建合约中的履约项
                    fulfillment("预充值") {
                        // 创建履约请求
                        request(rentingPlatform) {
                            key_timestamps("创建时间","过期时间")
                            key_data("金额")
                        }

                        // 创建履约确认
                        confirmation(prepaidUser) {
                            key_timestamps("创建时间")
                            key_data("金额")

                            // 履约确认生成一个凭证
                           val evidence = evidence("支付凭证") {
                                key_timestamps("支付时间")
                                key_data("金额")
                            }
                            // 该履约项扮演此凭证(凭证角色化)
                            party(evidence)
                        }
                    }
                }
            }
        }
    }
}