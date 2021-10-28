package doxflow.samples

import doxflow.diagram_8x_flow

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