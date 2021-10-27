package doxflow.models.diagram

import common.Element
import common.ChildElement
import common.Color.GREEN
import doxflow.dsl.context

class Participant(val element: Element, val type: Type, val context: context) : ChildElement(element, context) {
    private val associates: MutableList<Evidence<*>> = mutableListOf()

    enum class Type {
        PARTY,
        PLACE,
        THING
    }

    infix fun <T> associate(associate: Evidence<out T>) {
        associates.add(associate)
    }

    override fun toString(): String = buildString {
        appendLine("${element.type} ${element.name} <<${type.name.lowercase()}>> $GREEN")
        associates.forEach {
            appendLine("${element.name} $ASSOCIATE ${it.name}")
        }
    }
}