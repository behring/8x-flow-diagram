package doxflow.dsl

import common.DSL
import doxflow.diagram_8x_flow.generateGenerics
import doxflow.diagram_8x_flow.getAssociateLink
import doxflow.models.Association
import doxflow.models.AssociationType
import doxflow.models.Role

class fulfillment(val name: String, val context: context) : DSL<fulfillment>, Association {
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