package doxflow.dsl

import common.Element
import doxflow.models.diagram.*
import doxflow.models.diagram.Relationship.Companion.ONE_TO_ONE

class proposal(element: Element, role: Role?, note: String? = null) :
    Evidence<proposal>(element, proposal::class, role, note) {
    private var context: context? = null
    private var rfp: rfp? = null

    constructor(element: Element, context: context, role: Role?, note: String? = null) : this(element, role, note) {
        this.context = context
    }

    constructor(element: Element, rfp: rfp, role: Role?, note: String? = null) : this(element, role, note) {
        this.rfp = rfp
    }

    private lateinit var contract: contract

    init {
        resource = proposal::class.java.simpleName
    }

    fun contract(name: String, vararg roles: Role, function: contract.() -> Unit) {
        this.contract = contract(Element(name, "class"), this, *roles).apply {
            relationship = ONE_TO_ONE
            function()
        }
        element.relate(this.contract.element, ONE_TO_ONE)
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
            rfp?.let { appendLine(it.element.generateRelationships()) }
        }
    }
}