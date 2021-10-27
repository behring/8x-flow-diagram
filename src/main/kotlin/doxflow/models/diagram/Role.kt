package doxflow.models.diagram

import common.Element
import common.ChildElement
import common.Color.YELLOW
import doxflow.dsl.context

class Role(val element: Element, val type: Type, val context: context) : ChildElement(element, context) {
    private val associates: MutableList<Evidence<*>> = mutableListOf()
    var participant: Participant? = null

    enum class Type {
        PARTY,
        DOMAIN,
        THIRD_SYSTEM,
        EVIDENCE
    }

    infix fun played(participant: Participant): Role = apply { this.participant = participant }

    infix fun <T> associate(associate: Evidence<out T>) {
        associates.add(associate)
    }

    override fun toString(): String = buildString {
        appendLine("class ${element.name} <<${type.name.lowercase()}>> $YELLOW")
        associates.forEach {
            appendLine("${element.name} $ASSOCIATE ${it.name}")
        }
    }
}
