package doxflow.dsl

import common.Element
import doxflow.models.ability.BusinessAbility
import doxflow.models.diagram.Relationship
import doxflow.models.diagram.Relationship.Companion.ONE_TO_ONE
import doxflow.models.diagram.Role

class fulfillment(
    val name: String, val context: context, override var resource: String = "",
    var relationship: String = ONE_TO_ONE
) :
    BusinessAbility<fulfillment>, Relationship {
    lateinit var contract: contract
    lateinit var request: request
    lateinit var confirmation: confirmation

    fun request(role: Role? = null, request: request.() -> Unit) {
        this.request = request(Element("${name}请求", "class"), context, role).apply { request() }
        this.request.resource = resource
        this.request.contract = contract
        this.request.relationship = relationship
    }

    fun confirmation(role: Role? = null, confirmation: confirmation.() -> Unit) {
        this.confirmation = confirmation(Element("${name}确认", "class"), context, role).apply { confirmation() }
    }

    override fun invoke(function: fulfillment.() -> Unit): fulfillment {
        return apply { function() }
    }

    override fun toString(): String {
        return buildString {
            appendLine(request.toString())
            appendLine(confirmation.toString())
            appendLine("""${request.element.displayName} $relationship ${confirmation.element.displayName}""")
        }
    }
}