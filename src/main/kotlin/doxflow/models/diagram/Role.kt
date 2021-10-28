package doxflow.models.diagram

import common.Element
import common.ChildElement
import common.Diagram.Color.YELLOW
import doxflow.dsl.context

class Role(val element: Element, val type: Type, val context: context) : ChildElement(element, context) {
    init {
        element.backgroundColor = YELLOW
    }

    private val genericEvidences: MutableList<Evidence<*>> = mutableListOf()
    var participant: Participant? = null

    enum class Type {
        PARTY,
        DOMAIN,
        THIRD_SYSTEM,
        EVIDENCE
    }

    infix fun played(participant: Participant): Role = apply { this.participant = participant }

    infix fun <T> relate(genericEvidence: Evidence<out T>) {
        genericEvidences.add(genericEvidence)
    }

    override fun toString(): String = buildString {
        appendLine("${element.type} ${element.displayName} <<${type.name.lowercase()}>> $YELLOW")
        genericEvidences.forEach {
            appendLine("${element.displayName} $RELATIONSHIP ${it.element.displayName}")
        }
    }
}
