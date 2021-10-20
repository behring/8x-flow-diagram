package contract.samples

import contract.analysis.diagram_contract_analysis

diagram_contract_analysis {
    // evidence表示凭证，默认属于fulfillment内产生从凭证。
    // 可以通过rfp, proposal, contract，fulfillment来定义具体是什么类型的evidence
    evidence("预充值支付凭证") {
        key_timestamps("支付时间")
        key_data("金额")
        // 通过preorder定义前序凭证，该凭证属于contract类型
        this preorder contract("预充值协议") {
            key_timestamps("签署时间")
        }
        // 通过belong_to定义该凭证属于哪个协议
        this belong_to "预充值协议"
    }

    evidence("余额退款凭证") {
        key_timestamps("退款时间")
        key_data("金额")
        this belong_to "预充值协议"
    }

    evidence("支付推广费用凭证") {
        key_timestamps("消费时间")
        key_data("金额")
        // 该凭证属于2个协议，因此存在凭证角色化
        this belong_to "预充值协议"
        this belong_to "推广协议"

        this preorder evidence("推广凭证") {
            key_timestamps("推广时间")
            key_data("推广信息ID", "数量")
            this belong_to "推广协议"

            // 通过preorder定义前序凭证，该凭证属于contract类型
            this preorder contract("推广协议") {
                key_timestamps("签署时间", "开始时间")
                key_data("推广信息ID")
                // 通过preorder定义前序凭证，该凭证属于rfp类型
                this preorder proposal("推广服务价格提案") {
                    key_timestamps("起止时间")
                    key_data("单价")
                    this belong_to "推广协议"
                }

                this postorder evidence("暂停推广凭证") {
                    key_timestamps("暂停时间")
                    key_data("推广信息ID")
                    this belong_to "推广协议"
                }

                this postorder evidence("重启推广凭证") {
                    key_timestamps("重启时间")
                    key_data("推广信息ID")
                    this belong_to "推广协议"
                }
            }
        }
    }

    evidence("发票") {
        key_timestamps("开具时间")
        key_data("金额")
        this belong_to "预充值协议"

        this preorder evidence("消费账单") {
            key_timestamps("结算时间")
            key_data("金额")
            this belong_to "预充值协议"

            this preorder evidence("账单明细") {
                key_data("推广信息ID", "单价", "点击数", "金额")
                this belong_to "预充值协议"
            }
        }
    }
} export "./diagrams/contract_analysis_diagram.png"
