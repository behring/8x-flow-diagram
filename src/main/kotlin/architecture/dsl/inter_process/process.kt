package architecture.dsl.inter_process

import architecture.dsl.component
import common.*

class process(val element: Element, container: ParentContainer) :
    ParentContainer, DSL<process>,Interactions {
    init {
        container.addElement(element)
    }

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
