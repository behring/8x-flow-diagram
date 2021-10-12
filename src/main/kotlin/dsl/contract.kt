package dsl

import models.Evidence
import models.Role

class contract(name: String, context: context) : Evidence<contract>(name, context) {
    var fulfillments: MutableList<fulfillment> = mutableListOf()
    var roles: MutableList<Role> = mutableListOf()

    fun role_party(name: String): Role = Role(name, Role.Type.PARTY, context).apply { roles.add(this) }

    fun fulfillment(name: String, fulfillment: fulfillment.() -> Unit): fulfillment = fulfillment(name, context).apply {
        fulfillments.add(this)
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
                appendLine("""$name $ONE_TO_ONE ${role.name}""")
                role.participant?.let {
                    appendLine("""${it.name} $PLAY_TO ${role.name}""")
                }
            }

            fulfillments.forEach { fulfillment ->
                appendLine("""$name $ONE_TO_N ${fulfillment.request.name}""")
                appendLine(fulfillment.request.toString())
                appendLine("""${fulfillment.request.name} $ONE_TO_ONE ${fulfillment.confirmation.name}""")
                appendLine(fulfillment.confirmation.toString())
            }
        }
    }
}