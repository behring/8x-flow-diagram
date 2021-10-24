package doxflow.dsl

import doxflow.diagram_8x_flow.getAssociateLink
import doxflow.models.diagram.*

class contract(name: String, context: context, private vararg val roles: Role) : Evidence<contract>(name, context) {
    var fulfillments: MutableList<Pair<fulfillment, AssociationType>> = mutableListOf()

    fun fulfillment(
        name: String,
        associationType: AssociationType = AssociationType.ONE_TO_ONE,
        fulfillment: fulfillment.() -> Unit
    ): fulfillment =
        fulfillment(name, context).apply {
            fulfillments.add(Pair(this, associationType))
            fulfillment()
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
                appendLine("""$name ${getAssociateLink(it.second)} ${it.first.request.name}""")
                appendLine(it.first.request.toString())
                appendLine(it.first.toString())
                appendLine(it.first.confirmation.toString())
            }
        }
    }
}