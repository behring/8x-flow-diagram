package dsl

import dsl.diagram_8x_flow.generateGenerics
import models.Role

class fulfillment(val name: String, val context: context) : Flow<fulfillment> {
    lateinit var request: request
    lateinit var confirmation: confirmation

    override fun invoke(function: fulfillment.() -> Unit): fulfillment {
        return apply { function() }
    }

    fun request(role: Role? = null, request: request.() -> Unit) {
        this.request = request("${name}请求", context, generateGenerics(role)).apply { request() }
    }

    fun confirmation(role: Role? = null, confirmation: confirmation.() -> Unit) {
        this.confirmation = confirmation("${name}确认", context, generateGenerics(role)).apply { confirmation() }
    }
}