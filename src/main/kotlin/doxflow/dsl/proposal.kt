package doxflow.dsl

import common.Element
import doxflow.models.diagram.*

class proposal(element: Element, context: context, role: Role?, note: String? = null) :
    Evidence<proposal>(element, context, role, note) {
    private lateinit var contract: contract

    init {
        resource = type
    }

    // 当前proposal是否有对应的rfp
    var rfp: rfp? = null

    fun contract(name: String, vararg roles: Role, contract: contract.() -> Unit) {
        this.contract = contract(Element(name, "class"), context, *roles).apply {
            relationship_type = RelationShipType.ONE_TO_ONE
            context.contracts.add(this)
            contract()
        }
    }

    override val type: String
        get() = proposal::class.java.simpleName

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
            appendLine("${element.displayName} $ONE_TO_ONE ${contract.element.displayName}")
        }
    }
}