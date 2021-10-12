package dsl

import models.Evidence
import models.Role

class proposal(name: String, context: context, generics: String?, note: String? = null) :
    Evidence<proposal>(name, context, generics, note) {
    lateinit var contract: contract

    fun contract(name: String, vararg roles: Role, contract: contract.() -> Unit) {
        this.contract = contract(name,context, *roles).apply {
            context.contracts.add(this)
            contract()
        }
    }

    override val type: String
        get() = proposal::class.java.simpleName

    override fun invoke(function: proposal.() -> Unit): proposal = apply { function() }

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            appendLine("$name $ONE_TO_ONE ${contract.name}")
        }
    }
}