package doxflow.samples

import doxflow.diagram_8x_flow
import doxflow.models.diagram.AssociationType.ONE_TO_N

diagram_8x_flow {
    context("商品销售上下文") {
        val seller = role_party("卖家")
        val buyer = role_party("买家")

        rfp("询问商品价格", buyer) {
            resource = "order-request"
            key_timestamps("创建时间")

            proposal("商品报价方案", seller, ONE_TO_N) {
                key_timestamps("创建时间")
                key_data("报价金额")

                participant_thing("商品") associate this

                contract("商品订单合同", seller, buyer) {
                    resource = "order"
                    key_timestamps("签订时间")
                }
            }
        }
    }
} export_doc "./docs/order_api_doc.md"