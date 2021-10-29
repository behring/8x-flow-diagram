package doxflow.dsl

import common.Element
import doxflow.models.ability.BusinessAbilityTable
import doxflow.models.diagram.*
import doxflow.models.diagram.Relationship.Companion.ONE_TO_ONE

class contract(element: Element) :
    Evidence<contract>(element, contract::class) {
    private var context: context? = null
    private var proposal: proposal? = null
    private var roles: List<Role> = listOf()
    private var fulfillments: MutableList<fulfillment> = mutableListOf()

    constructor(element: Element, context: context, vararg roles: Role) : this(element) {
        this.context = context
        init(*roles)
    }

    constructor(element: Element, proposal: proposal, vararg roles: Role) : this(element) {
        this.proposal = proposal
        init(*roles)
    }

    private fun init(vararg roles: Role) {
        this.roles = roles.asList()
        roles.forEach { it.element.relate(element, ONE_TO_ONE) }
    }

    fun fulfillment(
        name: String,
        relationship: String = ONE_TO_ONE,
        fulfillment: fulfillment.() -> Unit
    ): fulfillment =
        fulfillment(name, this).apply {
            fulfillments.add(this)
            fulfillment()
            this@contract.relationship = relationship
            this@contract.element.relate(request.element, relationship)
        }

    override fun addBusinessAbility(table: BusinessAbilityTable) {
        super.addBusinessAbility(table)
        fulfillments.forEach {
            it.request.addBusinessAbility(table)
        }
    }

    override fun invoke(function: contract.() -> Unit): contract {
        return apply { function() }
    }

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            if (fulfillments.isNotEmpty()) fulfillments.forEach { appendLine(it.toString()) }
            appendLine(element.generateRelationships())
            proposal?.let { appendLine(it.element.generateRelationships()) }
            context?.let { appendLine(it.element.generateRelationships()) }
        }
    }
}