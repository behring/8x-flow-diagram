package dsl

import models.Role

class fulfillment(val name: String) : Flow<fulfillment> {
    lateinit var request: request
    lateinit var confirmation: confirmation

    override fun invoke(function: fulfillment.() -> Unit): fulfillment {
        return apply { function() }
    }

    fun request(role: Role, request: request.() -> Unit) {
        this.request = request("${name}请求", role, generateGenerics(role)).apply { request() }
    }

    fun confirmation(role: Role, confirmation: confirmation.() -> Unit) {
        this.confirmation = confirmation("${name}确认", role, generateGenerics(role)).apply { confirmation() }
    }

    private fun generateGenerics(role: Role): String = """
            <${role.type.name.lowercase()}\n${role.name}>
        """.trimIndent()
}