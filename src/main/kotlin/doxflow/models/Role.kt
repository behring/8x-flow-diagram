package doxflow.models

import architecture.models.Element
import common.ChildElement
import doxflow.dsl.context

class Role(val element: Element, val type: Type, val context: context) : ChildElement(element, context) {
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
            class ${element.name} <<${type.name.lowercase()}>> #orange
        """.trimIndent()
    }
}
