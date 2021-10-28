package doxflow.dsl

import common.Element
import doxflow.models.ability.BusinessAbilityTable
import doxflow.models.diagram.*

class contract(element: Element, context: context, private vararg val roles: Role) :
    Evidence<contract>(element, context) {
    var fulfillments: MutableList<fulfillment> = mutableListOf()

    fun fulfillment(
        name: String,
        relationShipType: RelationShipType = RelationShipType.ONE_TO_ONE,
        fulfillment: fulfillment.() -> Unit
    ): fulfillment =
        fulfillment(name, context).apply {
            this.contract = this@contract
            relationship_type = relationShipType
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

    override val type: String
        get() = contract::class.java.simpleName

    override fun toString(): String {
        return buildString {
            appendLine(super.toString())
            roles.forEach { role ->
                appendLine(role.toString())
                appendLine("""${element.displayName} $ONE_TO_ONE ${role.element.displayName}""")
            }

            fulfillments.forEach {
                appendLine("""${element.displayName} ${getRelationshipLine(it.relationship_type)} ${it.request.element.displayName}""")
                appendLine(it.request.toString())
                appendLine(it.toString())
                appendLine(it.confirmation.toString())
            }
        }
    }
}