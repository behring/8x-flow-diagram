package architecture.dsl.inter_process

import architecture.dsl.component
import architecture.models.Element
import common.ChildElement
import common.DSL
import common.ParentContainer

class process(override val element: Element, private val container: ParentContainer) : ChildElement(element, container),
    ParentContainer, DSL<process> {
    private val childComponents: MutableList<String> = mutableListOf()
    private val processInteractions: MutableList<Pair<String, String>> = mutableListOf()

    fun call(processName: String, command: String = "") {
        processInteractions.add(Pair(processName, command))
    }

    fun component(name: String, color: String? = null): component = component(Element(name, "rectangle", color), this)

    override fun invoke(function: process.() -> Unit): process = apply { function() }

    override fun addElement(element: Element) {
        childComponents.add("${element.type} ${element.name} ${element.color ?: this.element.color ?: ""}")
    }

    override fun toString(): String = buildString {
        with(childComponents) { if (isNotEmpty()) addElements(childComponents, container.element) }
        appendLine(generateInteractions(element, processInteractions))
    }
}
