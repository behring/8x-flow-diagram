package doxflow.dsl

import common.Element
import doxflow.models.ability.BusinessAbilityTable
import doxflow.models.diagram.*
import doxflow.models.diagram.Relationship.Companion.ONE_TO_ONE

class contract(element: Element, context: context, private vararg val roles: Role) :
    Evidence<contract>(element, context, contract::class) {
    var fulfillments: MutableList<fulfillment> = mutableListOf()

    fun fulfillment(
        name: String,
        relationship: String = ONE_TO_ONE,
        fulfillment: fulfillment.() -> Unit
    ): fulfillment =
        fulfillment(name, context).apply {
            this.contract = this@contract
            super.relationship = relationship
            fulfillments.add(this)
            fulfillment()
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
            roles.forEach { role ->
                appendLine(role.toString())
                appendLine("""${element.displayName} $ONE_TO_ONE ${role.element.displayName}""")
            }

            fulfillments.forEach {
                appendLine(it.toString())
                appendLine("""${element.displayName} $relationship ${it.request.element.displayName}""")
            }
        }
    }
}