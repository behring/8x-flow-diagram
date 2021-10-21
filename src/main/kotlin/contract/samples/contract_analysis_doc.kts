package contract.samples

import contract.content.doc_contract_content
import contract.content.dsl.person

doc_contract_content {
     // 定义合约
    contract("预充值合约") {
        // 定义合约的签订方
        val twPlatform = person("甲方", "思沃租房")
        val realtor = person("乙方", "房产经纪人")

        // 定义履约项并提供证明履约完成的凭证
        fulfillment("预充值", "预充值支付凭证") {
            // 甲方请求(乙方)履约
            twPlatform request this
            // 乙方必须在10分钟内完成履约
            realtor confirmation_within "10分钟"
        }

        fulfillment("余额退款", "余额退款凭证") {
            realtor request this
            twPlatform confirmation_within "3个工作日"
        }

        fulfillment("发票开具", "发票") {
            realtor request this
            twPlatform confirmation_within "7个工作日"
        }
    }
} export "./diagrams/contract_analysis_doc.md"
