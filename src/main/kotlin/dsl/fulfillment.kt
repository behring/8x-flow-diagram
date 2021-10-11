package dsl

import models.Role
import java.util.*

class fulfillment(val name: String) : Flow<fulfillment> {
    lateinit var request: request
    lateinit var confirmation: confirmation

    override fun invoke(function: fulfillment.() -> Unit): fulfillment {
        return apply { function() }
    }

    fun request(role: Role, request: request.() -> Unit) {
        val className = "${name}请求"
        this.request = request(className, role, generateNote(className, role)).apply { request() }
    }

    fun confirmation(role: Role, confirmation: confirmation.() -> Unit) {
        val className = "${name}确认"
        this.confirmation = confirmation(className, role, generateNote(className, role)).apply { confirmation() }
    }

    private fun generateNote(className: String, role: Role): String = """
            note left of $className #orange
            <${role.type.name.lowercase(Locale.getDefault())}>
             ${role.name}
            end note
        """.trimIndent()
}