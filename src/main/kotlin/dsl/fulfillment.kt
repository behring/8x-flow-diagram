package dsl

import dsl.diagram_8x_flow.generateGenerics
import dsl.diagram_8x_flow.getAssociateLink
import models.Association
import models.AssociationType
import models.ONE_TO_ONE
import models.Role

class fulfillment(val name: String, val context: context) : Flow<fulfillment>, Association {
    lateinit var request: request
    lateinit var confirmation: confirmation
    private var associateType: AssociationType = AssociationType.ONE_TO_ONE

    override fun invoke(function: fulfillment.() -> Unit): fulfillment {
        return apply { function() }
    }

    fun request(role: Role? = null, request: request.() -> Unit) {
        this.request = request("${name}请求", context, generateGenerics(role)).apply { request() }
    }

    fun confirmation(role: Role? = null, confirmation: confirmation.() -> Unit) {
        this.confirmation = confirmation("${name}确认", context, generateGenerics(role)).apply { confirmation() }
    }

    override fun associate(type: AssociationType) {
        associateType = type
    }

    override fun toString(): String {
        return buildString {
            appendLine("""${request.name} ${getAssociateLink(associateType)} ${confirmation.name}""")
        }
    }
}