package doxflow.dsl

import common.Element
import common.Element.Type.CLASS
import doxflow.models.ability.BusinessAbilityCreator
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.Party
import doxflow.models.diagram.Relationship.Companion.DEFAULT

class rfp(element: Element, val context: context, party: Party, note: String? = null) :
    Evidence<rfp>(element, rfp::class, party, note) {
    private lateinit var proposal: proposal

    fun proposal(
        name: String,
        party: Party,
        relationship: String = DEFAULT,
        proposal: proposal.() -> Unit
    ) {
        this.proposal = proposal(Element(name, CLASS), this, party).apply {
            super.relationship = relationship
            proposal()
        }
        element.relate(this.proposal.element, DEFAULT)
    }

    override fun addBusinessAbility(abilityCreator: BusinessAbilityCreator) {
        super.addBusinessAbility(abilityCreator)
        proposal.addBusinessAbility(abilityCreator)
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