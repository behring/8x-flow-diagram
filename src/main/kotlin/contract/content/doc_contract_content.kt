package contract.content

import common.DSL
import common.Doc

object doc_contract_content : DSL<doc_contract_content>, Doc {
    private val contracts: MutableList<contract> = mutableListOf()
    fun contract(name: String, function: contract.() -> Unit) = contract(name).apply {
        contracts.add(this)
        function()
    }

    override fun buildDocContent(): String = buildString {
        contracts.forEach { appendLine(it.toString()) }
    }

    override fun invoke(function: doc_contract_content.() -> Unit): doc_contract_content = apply { function() }
}



