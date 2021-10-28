package doxflow.dsl

import doxflow.models.diagram.RelationShipType
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.ONE_TO_ONE
import doxflow.models.diagram.Role

class rfp(name: String, context: context, role: Role, note: String? = null) :
    Evidence<rfp>(name, context, role, note) {
    private lateinit var proposal: proposal

    fun proposal(
        name: String,
        role: Role,
        relationShipType: RelationShipType = RelationShipType.ONE_TO_ONE,
        proposal: proposal.() -> Unit
    ) {
        this.proposal = proposal(name, context, role).apply {
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
            appendLine("$name$ONE_TO_ONE${proposal.name}")
        }
    }
}