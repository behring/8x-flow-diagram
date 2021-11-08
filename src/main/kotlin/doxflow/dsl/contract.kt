package doxflow.dsl

import common.Element
import doxflow.models.ability.BusinessAbilityCreator
import doxflow.models.diagram.*
import doxflow.models.diagram.Relationship.Companion.DEFAULT

class contract(element: Element) :
    Evidence<contract>(element, contract::class) {
    private var context: context? = null
    private var proposal: proposal? = null
    private var parties: List<Party> = listOf()
    private var fulfillments: MutableList<fulfillment> = mutableListOf()

    constructor(element: Element, context: context, vararg parties: Party) : this(element) {
        this.context = context
        init(*parties)
    }

    constructor(element: Element, proposal: proposal, vararg parties: Party) : this(element) {
        this.proposal = proposal
        init(*parties)
    }

    private fun init(vararg parties: Party) {
        this.parties = parties.asList()
        parties.forEach { it.element.relate(element, DEFAULT) }
    }

    fun fulfillment(
        name: String,
        relationship: String = DEFAULT,
        fulfillment: fulfillment.() -> Unit
    ): fulfillment =
        fulfillment(name, this).apply {
            fulfillments.add(this)
            fulfillment()
            this@contract.relationship = relationship
            this@contract.element.relate(request.element, relationship)
        }

    override fun addBusinessAbility(abilityCreator: BusinessAbilityCreator) {
        super.addBusinessAbility(abilityCreator)
        fulfillments.forEach {
            it.request.addBusinessAbility(abilityCreator)
        }
    }

    override fun invoke(function: contract.() -> Unit): contract {
        return apply { function() }
    }

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            if (fulfillments.isNotEmpty()) fulfillments.reversed().forEach { appendLine(it.toString()) }
            appendLine(element.generateRelationships())
            proposal?.let { appendLine(it.element.generateRelationships()) }
            context?.let { appendLine(it.element.generateRelationships()) }
        }
    }
}