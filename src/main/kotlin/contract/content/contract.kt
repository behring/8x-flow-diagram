package contract.content

import common.DSL

import contract.content.person as DSLperson
import contract.content.fulfillment as DSLfulfillment

class contract(val name: String) : DSL<contract> {
    private val parties: MutableList<DSLperson> = mutableListOf()
    private val fulfillments: MutableList<DSLfulfillment> = mutableListOf()

    fun person(party: String, name: String): DSLperson = DSLperson(party, name).apply { parties.add(this) }

    fun prosecute(): DSLfulfillment = fulfillment("诉讼")

    fun fulfillment(
        name: String,
        evidence: String? = null,
        periodOfFulfil: String = "任何时间",
        function: (DSLfulfillment.() -> Unit)? = null
    ): DSLfulfillment =
        DSLfulfillment(name, evidence, periodOfFulfil, parties).apply {
            fulfillments.add(this)
            function?.let { function() }
        }

    override fun invoke(function: contract.() -> Unit): contract = apply { function() }

    override fun toString(): String = buildString {
        appendLine("## $name")
        parties.forEach {
            appendLine("- ${it.party}: ${it.name} \n")
        }
        appendLine("合约条款：\n")
        var index = 1
        fulfillments.forEach {
            if (it.isExistParties()) {
                appendLine("${index++}: $it\n")
            }
        }
    }
}

