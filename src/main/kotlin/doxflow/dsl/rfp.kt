package doxflow.dsl

import common.Element
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.Party
import doxflow.models.diagram.Relationship.Companion.ONE_TO_ONE
import doxflow.models.diagram.Role

class rfp(element: Element, val context: context, party: Party, note: String? = null) :
    Evidence<rfp>(element, rfp::class, party, note) {
    private lateinit var proposal: proposal

    fun proposal(
        name: String,
        party: Party,
        relationship: String = ONE_TO_ONE,
        proposal: proposal.() -> Unit
    ) {
        this.proposal = proposal(Element(name, "class"), this, party).apply {
            super.relationship = relationship
            context.proposals.add(this)
            proposal()
        }
        element.relate(this.proposal.element, ONE_TO_ONE)
    }

    override fun invoke(function: rfp.() -> Unit): rfp {
        return apply { function() }
    }

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            appendLine(proposal.toString())
            appendLine(context.element.generateRelationships())
        }
    }
}