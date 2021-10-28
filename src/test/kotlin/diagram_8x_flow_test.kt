import doxflow.diagram_8x_flow
import doxflow.dsl.fulfillment
import org.junit.Test

internal class diagram_8x_flow_test {
    @Test
    fun hello_word_diagram() {
        diagram_8x_flow {
            context("商品销售上下文") {
                contract("商品订单合同") {
                    key_timestamps("签订时间")
                }
            }
        } export "./diagrams/hello_word_diagram.png"
    }

    @Test
    fun create_reader_subscription_diagram() {
        diagram_8x_flow {
            lateinit var paymentInReaderSubscriptionContext: fulfillment

            context("读者订阅上下文") {
                val reader = role_party("读者") played participant_party("极客时间注册用户")
                val contentProvider = role_party("内容提供商") played participant_party("极客时间平台")

                contract("专栏订阅合同", reader, contentProvider) {
                    key_timestamps("订阅时间")
                    participant_place("专栏") relate this

                    paymentInReaderSubscriptionContext = fulfillment("专栏付款") {
                        request(contentProvider) {
                            key_timestamps("创建时间", "过期时间")
                            key_data("金额")
                        }

                        confirmation(reader) {
                            key_timestamps("创建时间")
                            key_data("金额")
                        }
                    }

                    fulfillment("付费内容访问") {
                        request(reader) {
                            key_timestamps("创建时间", "订阅时间")
                            key_data("专栏")
                        }

                        confirmation(contentProvider) {
                            key_timestamps("订阅时间")
                            key_data("专栏")
                        }
                    }

                    fulfillment("断更补偿") {
                        request(reader) {
                            key_timestamps("创建时间", "最后更新时间")
                            key_data("金额")
                        }

                        confirmation(contentProvider) {
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
                            evidence role paymentInReaderSubscriptionContext.confirmation
                        }
                    }
                }
            }
        } export "./diagrams/reader_subscription_diagram.png"
    }

    @Test
    fun create_editor_performance_diagram() {
        diagram_8x_flow {
            context("编辑绩效协议上下文") {
                val editor = role_party("编辑")
                val geekTimePlatform = role_party("极客时间平台")

                contract("绩效协议") {
                    key_timestamps("签订时间")
                    editor relate this
                    geekTimePlatform relate this

                    fulfillment("目标设定") {
                        request(editor) {
                            key_timestamps("开始时间", "截止时间")
                            key_data("绩效指标")
                        }

                        confirmation(geekTimePlatform) {
                            key_timestamps("确认时间")
                            key_data("目标完成率")
                        }
                    }

                    fulfillment("周进度检查") {
                        request(editor) {
                            key_timestamps("开始时间", "截止时间")
                            key_data("周进度")
                        }

                        confirmation(geekTimePlatform) {
                            key_timestamps("确认时间")
                            key_data("周进度完成率")

                            this dependent_on confirmation("周进度检查确认条目") {
                                key_timestamps("确认时间")
                                key_data("专栏选题", "文章提交", "专栏立项", "交稿确认")
                            }
                        }
                    }
                }
            }
        } export "./diagrams/editor_performance_diagram.png"
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

                        participant_thing("商品") relate this

                        contract("商品订单合同", seller, buyer) {
                            key_timestamps("签订时间")
                        }
                    }
                }
            }
        } export "./diagrams/contract_with_rfp_diagram.png"
    }
}