package doxflow.models.diagram

import common.Element
import common.Diagram.Color.YELLOW
import doxflow.dsl.context
import doxflow.models.diagram.Relationship.Companion.NONE
import doxflow.models.diagram.Relationship.Companion.PLAY_TO

class Role(val element: Element, type: Type, val context: context) {
    init {
        element.backgroundColor = YELLOW
        element.stereoType = type.name.lowercase()
    }

    enum class Type {
        PARTY,
        DOMAIN,
        THIRD_SYSTEM,
        EVIDENCE
    }

    infix fun played(participant: Participant): Role = apply {
        // TODO: 2021/10/29 这里应该是 participant PLAY_TO role,重构时候优化
        element.relate(participant.element, PLAY_TO)
    }

    infix fun relate(genericEvidence: Evidence<*>) {
        element.relate(genericEvidence.element, NONE)
    }

    override fun toString(): String = buildString {
        appendLine(element)
        appendLine(element.generateRelationships())
    }
}
