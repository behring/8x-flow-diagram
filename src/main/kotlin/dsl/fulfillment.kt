package dsl

import models.Role

class fulfillment(val name: String) : Flow<fulfillment> {

    override fun invoke(function: fulfillment.() -> Unit): fulfillment {
        return apply { function() }
    }

    fun request(role: Role, request: request.() -> Unit) {
        request(role).request()
    }

    fun confirmation(role: Role, confirmation: confirmation.() -> Unit) {
        confirmation(role).confirmation()
    }
}