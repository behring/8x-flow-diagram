package doxflow.samples

import common.Diagram.LayoutDirection.*
import doxflow.diagram_8x_flow

diagram_8x_flow(Horizontal) {
    context("商品销售上下文") {
        contract("商品订单合同") {
            key_timestamps("签订时间")
        }
    }
} export "./diagrams/hello_word_diagram.png"