package architecture.dsl.inter_process

import architecture.dsl.component
import common.ChildElement
import common.DSL
import common.Element
import common.ParentContainer

class process(override val element: Element, container: ParentContainer) : ChildElement(element, container),
    ParentContainer, DSL<process> {
    private val components: MutableList<component> = mutableListOf()
    private val processInteractions: MutableList<Pair<String, String>> = mutableListOf()

    fun call(processName: String, command: String = "") {
        processInteractions.add(Pair(processName, command))
    }

    fun component(name: String, color: String? = null): component {
        val component = component(Element(name, "rectangle", color), this)
        components.add(component)
        element.childElements.add(component.element)
        return component
    }

    override fun invoke(function: process.() -> Unit): process = apply { function() }



    override fun toString(): String = buildString {
        appendLine(generateInteractions(element, processInteractions))
    }
}
