package doxflow.dsl

import common.Element
import doxflow.models.diagram.*
import doxflow.models.diagram.Relationship.Companion.ONE_TO_ONE

class proposal(element: Element, context: context, role: Role?, note: String? = null) :
    Evidence<proposal>(element, context, proposal::class, role, note) {


    private lateinit var contract: contract

    init {
        resource = proposal::class.java.simpleName
    }

    // 当前proposal是否有对应的rfp
    var rfp: rfp? = null

    fun contract(name: String, vararg roles: Role, contract: contract.() -> Unit) {
        this.contract = contract(Element(name, "class"), context, *roles).apply {
            relationship = ONE_TO_ONE
            contract()
        }
    }

    override fun invoke(function: proposal.() -> Unit): proposal = apply { function() }

    override fun getUriPrefix(): String {
        rfp?.let {
            return it.getUri()
        }
        return super.getUriPrefix()
    }

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            appendLine(contract.toString())
            appendLine("${element.displayName} $ONE_TO_ONE ${contract.element.displayName}")
        }
    }
}