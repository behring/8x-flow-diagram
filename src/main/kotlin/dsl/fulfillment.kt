package dsl

import models.Role

class fulfillment(val name: String) : Flow<fulfillment> {
    lateinit var request: request
    lateinit var confirmation: confirmation

    override fun invoke(function: fulfillment.() -> Unit): fulfillment {
        return apply { function() }
    }

    fun request(role: Role? = null, request: request.() -> Unit) {
        this.request = request("${name}请求", generateGenerics(role)).apply { request() }
    }

    fun confirmation(role: Role? = null, confirmation: confirmation.() -> Unit) {
        this.confirmation = confirmation("${name}确认", generateGenerics(role)).apply { confirmation() }
    }

    private fun generateGenerics(role: Role?): String? = role?.let {
        """
            <${role.type.name.lowercase()}\n${role.name}>
        """.trimIndent()
    }
}