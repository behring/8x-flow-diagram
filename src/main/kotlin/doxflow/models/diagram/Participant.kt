package doxflow.models.diagram

import common.Element
import common.ChildElement
import common.Color.GREEN
import doxflow.dsl.context

class Participant(val element: Element, val type: Type, val context: context) : ChildElement(element, context) {
    private val genericeEvidences: MutableList<Evidence<*>> = mutableListOf()

    enum class Type {
        PARTY,
        PLACE,
        THING
    }

    infix fun <T> relate(genericEvidence: Evidence<out T>) {
        genericeEvidences.add(genericEvidence)
    }

    override fun toString(): String = buildString {
        appendLine("${element.type} ${element.name} <<${type.name.lowercase()}>> $GREEN")
        genericeEvidences.forEach {
            appendLine("${element.name} $RELATIONSHIP ${it.name}")
        }
    }
}