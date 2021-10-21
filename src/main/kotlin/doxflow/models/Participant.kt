package doxflow.models

import common.Element
import common.ChildElement
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

    override fun toString(): String {
        return buildString {
            appendLine("""${element.type} ${element.name} <<${type.name.lowercase()}>> #ForestGreen""".trimIndent())
            associates.forEach {
                appendLine("${element.name} $ASSOCIATE ${it.name}")
            }
        }
    }
}