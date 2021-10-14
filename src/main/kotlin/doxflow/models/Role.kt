package doxflow.models

import common.ChildElement
import doxflow.dsl.context

class Role(name: String, val type: Type, val context: context) : ChildElement(name, context) {
    var participant: Participant? = null

    enum class Type {
        PARTY,
        DOMAIN,
        THIRD_SYSTEM,
        EVIDENCE
    }

    infix fun played(participant: Participant): Role = apply { this.participant = participant }

    override fun toString(): String {
        return """
            class $name <<${type.name.lowercase()}>> #orange
        """.trimIndent()
    }
}
