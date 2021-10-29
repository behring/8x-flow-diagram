package architecture.dsl.inter_process

import architecture.dsl.component
import common.*
import doxflow.models.diagram.Relationship.Companion.ASSOCIATE

class process(val element: Element, val service: service) : DSL<process> {
    init {
        if (element.backgroundColor == null)
            element.backgroundColor = service.element.backgroundColor
    }

    private val components: MutableList<component> = mutableListOf()

    fun call(processName: String, command: String = "") {
        element.relate(processName, ASSOCIATE, command)
    }

    fun component(name: String, color: String? = null): component {
        val component = component(Element(name, "rectangle", color), this)
        components.add(component)
        return component
    }

    override fun invoke(function: process.() -> Unit): process = apply { function() }


    override fun toString(): String = buildString {
        appendLine("$element {")
        components.forEach {
            appendLine(it.toString())
        }
        appendLine("}")
        appendLine(element.generateRelationships())
        appendLine(service.element.generateRelationships())
    }
}
