package doxflow.samples

import doxflow.diagram_8x_flow
import doxflow.models.diagram.AssociationType

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

                fulfillment("推广重启", AssociationType.ONE_TO_N) {
                    request(advertiser) {
                        key_timestamps("创建时间", "过期时间")
                    }
                    confirmation(promoter) {
                        key_timestamps("启动时间")
                    }
                }

                fulfillment("推广取消", AssociationType.ONE_TO_N) {
                    request(advertiser) {
                        key_timestamps("创建时间", "过期时间")
                    }
                    confirmation(promoter) {
                        key_timestamps("取消时间")
                    }
                }

                fulfillment("支付", AssociationType.ONE_TO_N) {
                    request(promoter) {
                        key_timestamps("创建时间", "过期时间", "终止时间")
                        key_data("金额")
                    }
                    confirmation(advertiser) {
                        key_timestamps("创建时间")
                        key_data("金额")
                    }
                }

                fulfillment("信息推广", AssociationType.ONE_TO_N) {
                    request(advertiser) {
                        key_timestamps("创建时间", "过期时间", "终止时间")
                    }
                    confirmation(promoter) {
                        key_timestamps("创建时间")
                    }
                    associate(AssociationType.ONE_TO_N)
                }

            }
        }
    }
} export "./diagrams/info_promotion_contract_diagram.png"