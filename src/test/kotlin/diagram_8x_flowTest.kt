import dsl.detail
import dsl.diagram_8x_flow
import dsl.fulfillment
import org.junit.Test

internal class diagram_8x_flowTest {
    @Test
    fun create_contact_diagram() {
        diagram_8x_flow {

            lateinit var refundInPrepaidContext: fulfillment
            lateinit var prepaidInPrepaidContext: fulfillment

            context("预充值协议上下文") {
                val houseAgent = participant_party("房产经纪人")

                contract("预充值协议") {
                    key_timestamps("签订时间")
                    participant_place("预充值账户") associate this

                    val prepaidUser = role_party("预充值用户") played houseAgent
                    val rentingPlatform = role_party("思沃租房")

                    prepaidInPrepaidContext = fulfillment("预充值") {
                        request(rentingPlatform) {
                            key_timestamps("创建时间", "过期时间")
                            key_data("金额")
                        }

                        confirmation(prepaidUser) {
                            key_timestamps("创建时间")
                            key_data("金额")
                        }
                    }

                    refundInPrepaidContext = fulfillment("余额退款") {
                        request(prepaidUser) {
                            key_timestamps("创建时间", "过期时间")
                            key_data("金额")
                        }

                        confirmation(rentingPlatform) {
                            key_timestamps("创建时间")
                            key_data("金额")
                        }
                    }

                    fulfillment("发票开具") {
                        request(prepaidUser) {
                            key_timestamps("创建时间", "过期时间")
                            key_data("金额")
                        }

                        confirmation(rentingPlatform) {
                            key_timestamps("创建时间")
                            key_data("金额")
                        }
                    }

                    fulfillment("账单发送") {
                        request(prepaidUser) {
                            key_timestamps("创建时间", "过期时间")
                            key_data("金额")
                        }

                        confirmation(rentingPlatform) {
                            key_timestamps("发布时间")
                            key_data("金额")

                            evidence("账单") {
                                key_timestamps("创建时间")
                                key_data("账单期数, 总金额")

                                detail("账单明细") {
                                    key_timestamps("创建时间")
                                    key_data("账单期数, 总金额")
                                }
                            }
                        }
                    }
                }
            }

            context("三方支付上下文") {
                contract("XXX支付协议") {
                    key_timestamps("签订时间")
                    fulfillment("代付") {
                        request {
                            key_timestamps("创建时间", "过期时间")
                            key_data("金额")
                        }

                        confirmation {
                            key_timestamps("创建时间")
                            key_data("金额")

                            val evidence = evidence("支付凭证") {
                                key_timestamps("支付时间")
                                key_data("金额")
                            }
                            evidence role refundInPrepaidContext.confirmation
                            evidence role prepaidInPrepaidContext.confirmation


                        }
                    }
                }
            }

        }.createDiagram("./8x-flow2.png")
    }
}