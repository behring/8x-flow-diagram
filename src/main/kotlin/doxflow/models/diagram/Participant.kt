package doxflow.models.diagram

import common.Element
import common.Diagram.Color.GREEN
import doxflow.dsl.context
import doxflow.models.diagram.Relationship.Companion.NONE

class Participant(override val element: Element, type: Type, val context: context) : Party {
    init {
        element.stereoType = type.name.lowercase()
        element.backgroundColor = GREEN
    }

    enum class Type {
        PARTY,
        PLACE,
        THING
    }

    infix fun relate(genericEvidence: Evidence<*>) {
        element.relate(genericEvidence.element, NONE)
    }

    override fun toString(): String = buildString {
        appendLine(element)
        appendLine(element.generateRelationships())
    }
}