package architecture.dsl

import architecture.models.Element
import common.ChildElement
import common.DSL
import common.ParentContainer

class process(val element: Element, val layer: layer) : ChildElement(element, layer), ParentContainer, DSL<process> {
    private val childComponents: MutableList<String> = mutableListOf()

    infix fun call(process: process) {

    }

    fun component(name: String, color: String? = null): component = component(Element(name, "rectangle", color), this)

    override fun invoke(function: process.() -> Unit): process = apply { function() }

    override fun addElement(element: Element) {
        childComponents.add("${element.type} ${element.name} ${element.color ?: this.element.color ?: ""}")
    }

    override fun toString(): String = buildString {
        with(childComponents) { if (isNotEmpty()) addElements(childComponents, layer.element) }
    }
}
