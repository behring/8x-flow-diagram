package doxflow.dsl

import common.Element
import doxflow.models.diagram.RelationShipType
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.ONE_TO_ONE
import doxflow.models.diagram.Role

class rfp(element: Element, context: context, role: Role, note: String? = null) :
    Evidence<rfp>(element, context, role, note) {
    private lateinit var proposal: proposal

    fun proposal(
        name: String,
        role: Role,
        relationShipType: RelationShipType = RelationShipType.ONE_TO_ONE,
        proposal: proposal.() -> Unit
    ) {
        this.proposal = proposal(Element(name, "class"), context, role).apply {
            context.proposals.add(this)
            rfp = this@rfp
            this.relationship_type = relationShipType
            proposal()
        }
    }

    override fun invoke(function: rfp.() -> Unit): rfp {
        return apply { function() }
    }

    override val type: String
        get() = rfp::class.java.simpleName

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            appendLine("${element.name}$ONE_TO_ONE${proposal.element.name}")
        }
    }
}