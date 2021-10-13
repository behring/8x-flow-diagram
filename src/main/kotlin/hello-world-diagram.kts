import dsl.diagram_8x_flow

diagram_8x_flow {
    context("商品销售上下文") {
        contract("商品订单合同") {
            key_timestamps("签订时间")
        }
    }
} diagram "../../../diagrams/hello-word-diagram.png"