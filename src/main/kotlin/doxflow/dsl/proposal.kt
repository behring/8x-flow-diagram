package doxflow.dsl

import doxflow.models.*

class proposal(name: String, context: context, generics: String?, note: String? = null) :
    Evidence<proposal>(name, context, generics, note), Association {
    private lateinit var contract: contract
    private lateinit var associateType: AssociationType

    fun contract(name: String, vararg roles: Role, contract: contract.() -> Unit) {
        this.contract = contract(name, context, *roles).apply {
            context.contracts.add(this)
            contract()
        }
    }

    override val type: String
        get() = proposal::class.java.simpleName

    override fun invoke(function: proposal.() -> Unit): proposal = apply { function() }

    override fun associate(type: AssociationType) {
        associateType = type
    }

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            appendLine("$name $ONE_TO_ONE ${contract.name}")
        }
    }
}