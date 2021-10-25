package doxflow.dsl

import doxflow.diagram_8x_flow.getAssociateLink
import doxflow.models.ability.BusinessAbilityTable
import doxflow.models.diagram.*

class contract(name: String, context: context, private vararg val roles: Role) : Evidence<contract>(name, context) {
    var fulfillments: MutableList<fulfillment> = mutableListOf()

    fun fulfillment(
        name: String,
        associationType: AssociationType = AssociationType.ONE_TO_ONE,
        fulfillment: fulfillment.() -> Unit
    ): fulfillment =
        fulfillment(name, context).apply {
            this.contract = this@contract
            association_type = associationType
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
                appendLine("""$name $ONE_TO_ONE ${role.element.name}""")
                role.participant?.let {
                    appendLine("""${it.element.name} $PLAY_TO ${role.element.name}""")
                }
            }

            fulfillments.forEach {
                appendLine("""$name ${getAssociateLink(it.association_type)} ${it.request.name}""")
                appendLine(it.request.toString())
                appendLine(it.toString())
                appendLine(it.confirmation.toString())
            }
        }
    }
}