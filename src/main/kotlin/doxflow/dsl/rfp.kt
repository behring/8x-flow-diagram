package doxflow.dsl

import common.Element
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.Relationship.Companion.ONE_TO_ONE
import doxflow.models.diagram.Role

class rfp(element: Element, context: context, role: Role, note: String? = null) :
    Evidence<rfp>(element, context, rfp::class, role, note) {
    private lateinit var proposal: proposal

    fun proposal(
        name: String,
        role: Role,
        relationship: String = ONE_TO_ONE,
        proposal: proposal.() -> Unit
    ) {
        this.proposal = proposal(Element(name, "class"), context, role).apply {
            rfp = this@rfp
            super.relationship = relationship
            proposal()
        }
    }

    override fun invoke(function: rfp.() -> Unit): rfp {
        return apply { function() }
    }

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            appendLine(proposal.toString())
            appendLine("${element.displayName}$ONE_TO_ONE${proposal.element.displayName}")
        }
    }
}