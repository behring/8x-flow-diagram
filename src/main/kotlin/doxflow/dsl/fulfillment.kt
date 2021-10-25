package doxflow.dsl

import doxflow.models.ability.BusinessAbility
import doxflow.diagram_8x_flow.getAssociateLink
import doxflow.models.diagram.Association
import doxflow.models.diagram.AssociationType
import doxflow.models.diagram.Evidence
import doxflow.models.diagram.Role

class fulfillment(val name: String, val context: context, override var resource: String = "",
                  override var association_type: AssociationType = AssociationType.ONE_TO_ONE
) :
    BusinessAbility<fulfillment>, Association {
    lateinit var request: request
    lateinit var confirmation: confirmation

    fun request(role: Role? = null, request: request.() -> Unit) {
        this.request = request("${name}请求", context, role).apply { request() }
    }

    fun confirmation(role: Role? = null, confirmation: confirmation.() -> Unit) {
        this.confirmation = confirmation("${name}确认", context, role).apply { confirmation() }
    }

    override fun invoke(function: fulfillment.() -> Unit): fulfillment {
        return apply { function() }
    }

    override fun toString(): String {
        return buildString {
            appendLine("""${request.name} ${getAssociateLink(association_type)} ${confirmation.name}""")
        }
    }
}