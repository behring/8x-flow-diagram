package doxflow.dsl

import doxflow.models.ability.BusinessAbility
import doxflow.diagram_8x_flow.getRelationshipLine
import doxflow.models.diagram.Relationship
import doxflow.models.diagram.RelationShipType
import doxflow.models.diagram.Role

class fulfillment(val name: String, val context: context, override var resource: String = "",
                  override var relationship_type: RelationShipType = RelationShipType.ONE_TO_ONE
) :
    BusinessAbility<fulfillment>, Relationship {
    lateinit var contract: contract
    lateinit var request: request
    lateinit var confirmation: confirmation

    fun request(role: Role? = null, request: request.() -> Unit) {
        this.request = request("${name}请求", context, role).apply { request() }
        this.request.resource = resource
        this.request.contract = contract
        this.request.relationship_type = relationship_type
    }

    fun confirmation(role: Role? = null, confirmation: confirmation.() -> Unit) {
        this.confirmation = confirmation("${name}确认", context, role).apply { confirmation() }
    }

    override fun invoke(function: fulfillment.() -> Unit): fulfillment {
        return apply { function() }
    }

    override fun toString(): String {
        return buildString {
            appendLine("""${request.name} ${getRelationshipLine(relationship_type)} ${confirmation.name}""")
        }
    }
}