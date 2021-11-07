package doxflow.models.diagram

import common.Element
import common.Diagram.Color.YELLOW
import doxflow.dsl.context
import doxflow.models.diagram.Relationship.Companion.DEFAULT
import doxflow.models.diagram.Relationship.Companion.NONE
import doxflow.models.diagram.Relationship.Companion.PLAY_TO

class Role(override val element: Element, type: Type, val context: context) : Party {
    init {
        element.backgroundColor = YELLOW
        element.stereoType = type.name.lowercase()
        element.name = context.element.name + element.name
    }

    enum class Type {
        PARTY,
        DOMAIN,
        THIRD_SYSTEM,
        EVIDENCE,
        CONTEXT
    }

    fun relate(genericEvidence: Evidence<*>, relationship: String = DEFAULT) {
        element.relate(genericEvidence.element, relationship)
    }

    infix fun play(genericEvidence: Evidence<*>) {
        element.relate(genericEvidence.element, PLAY_TO)
    }

    override fun toString(): String = buildString {
        appendLine(element)
        appendLine(element.generateRelationships())
    }
}
