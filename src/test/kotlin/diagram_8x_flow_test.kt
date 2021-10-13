import doxflow.diagram_8x_flow
import doxflow.dsl.fulfillment
import doxflow.models.AssociationType.*
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

internal class diagram_8x_flow_test {
    @Test
    fun create_prepaid_contract_diagram() {
        diagram_8x_flow {
            lateinit var refundInPrepaidContext: fulfillment
            lateinit var prepaidInPrepaidContext: fulfillment
            lateinit var invoiceInPrepaidContext: fulfillment

            context("预充值协议上下文") {
                val houseAgent = participant_party("房产经纪人")
                val prepaidUser = role_party("预充值用户") played houseAgent
                val rentingPlatform = role_party("思沃租房")

                contract("预充值协议", prepaidUser, rentingPlatform) {
                    key_timestamps("签订时间")
                    participant_place("预充值账户") associate this

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

                    invoiceInPrepaidContext = fulfillment("发票开具") {
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

                    fulfillment("支付推广费用") {
                        request(rentingPlatform) {
                            key_timestamps("创建时间", "过期时间")
                            key_data("金额")
                        }

                        confirmation(prepaidUser) {
                            key_timestamps("创建时间")
                            key_data("金额")
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

            context("发票代开服务上下文") {
                contract("发票代开协议") {
                    key_timestamps("签订时间")
                    fulfillment("发票代开") {
                        request {
                            key_timestamps("创建时间", "过期时间")
                            key_data("金额")
                        }

                        confirmation {
                            key_timestamps("创建时间")
                            key_data("金额")

                            val evidence = evidence("发票") {
                                key_timestamps("开具时间")
                                key_data("金额")
                            }
                            evidence role invoiceInPrepaidContext.confirmation
                        }
                    }
                }
            }

        } export "./diagrams/prepaid_contract_diagram.png"

    }

    @Test
    fun create_info_promotion_contract_diagram() {
        diagram_8x_flow {
            context("信息推广上下文") {
                val advertiser = role_party("广告主") played participant_party("预充值用户")
                val promoter = role_party("推广商") played participant_party("思沃租房")

                proposal("信息推广方案", promoter) {
                    key_timestamps("创建时间")
                    key_data("点击报价")

                    participant_thing("用户账号")

                    participant_thing("房屋租赁信息") associate this

                    contract("信息推广服务合同", advertiser, promoter) {
                        key_timestamps("签订时间")

                        fulfillment("推广重启", ONE_TO_N) {
                            request(advertiser) {
                                key_timestamps("创建时间", "过期时间")
                            }
                            confirmation(promoter) {
                                key_timestamps("启动时间")
                            }
                        }

                        fulfillment("推广取消", ONE_TO_N) {
                            request(advertiser) {
                                key_timestamps("创建时间", "过期时间")
                            }
                            confirmation(promoter) {
                                key_timestamps("取消时间")
                            }
                        }

                        fulfillment("支付", ONE_TO_N) {
                            request(promoter) {
                                key_timestamps("创建时间", "过期时间", "终止时间")
                                key_data("金额")
                            }
                            confirmation(advertiser) {
                                key_timestamps("创建时间")
                                key_data("金额")
                            }
                        }

                        fulfillment("信息推广", ONE_TO_N) {
                            request(advertiser) {
                                key_timestamps("创建时间", "过期时间", "终止时间")
                            }
                            confirmation(promoter) {
                                key_timestamps("创建时间")
                            }
                            associate(ONE_TO_N)
                        }

                    }
                }
            }
        } export "./diagrams/info_promotion_contract_diagram.png"
    }

    @Test
    fun create_contract_with_rfp_diagram() {
        diagram_8x_flow {
            context("商品销售上下文") {
                val seller = role_party("卖家")
                val buyer = role_party("买家")

                rfp("询问商品价格", buyer) {
                    key_timestamps("创建时间")

                    proposal("商品报价方案", seller) {
                        key_timestamps("创建时间")
                        key_data("报价金额")

                        participant_thing("商品") associate this

                        contract("商品订单合同", seller, buyer) {
                            key_timestamps("签订时间")
                        }
                    }
                }
            }
        } export "./diagrams/contract_with_rfp_diagram.png"
    }
}