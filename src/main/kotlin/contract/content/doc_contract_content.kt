package contract.content

import common.DSL
import common.Doc
import contract.content.dsl.contract

object doc_contract_content : DSL<doc_contract_content>, Doc {
    fun contract(name: String, function: contract.() -> Unit) = with(contract(name)) { function() }

    override fun buildDocContent(): String {
        return ""
    }

    override fun invoke(function: doc_contract_content.() -> Unit): doc_contract_content = apply { function() }
}



