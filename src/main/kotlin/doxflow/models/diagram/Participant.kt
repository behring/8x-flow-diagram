package doxflow.models.diagram

import common.Element
import common.Diagram.Color.GREEN
import doxflow.dsl.context
import doxflow.models.diagram.Relationship.Companion.NONE

class Participant(val element: Element, val type: Type, val context: context) {
    init {
        element.stereoType = type.name.lowercase()
        element.backgroundColor = GREEN
    }

    private val genericeEvidences: MutableList<Evidence<*>> = mutableListOf()

    enum class Type {
        PARTY,
        PLACE,
        THING
    }

    infix fun relate(genericEvidence: Evidence<*>) {
        genericeEvidences.add(genericEvidence)
    }

    override fun toString(): String = buildString {
        appendLine("$element")

        genericeEvidences.forEach {
            appendLine("${element.displayName} $NONE ${it.element.displayName}")
        }
    }
}