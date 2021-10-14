package architecture.dsl

import architecture.models.Element
import common.ChildElement
import common.DSL
import common.ParentContainer

class process(val element: Element, val layer: layer) : ChildElement(element, layer), ParentContainer, DSL<process> {
    private val childComponents: MutableList<String> = mutableListOf()
    private val processRelations: MutableList<Pair<String, String>> = mutableListOf()

    fun call(processName: String, command: String = "") {
        processRelations.add(Pair(processName, command))
    }

    fun component(name: String, color: String? = null): component = component(Element(name, "rectangle", color), this)
    
    private fun generateProcessRelations(): String = buildString {
        processRelations.forEach {
            append("[${element.name}]-->[${it.first}]")
            appendLine(with(it.second) {
                return@with if (!isNullOrBlank()) ":${it.second}" else ""
            })
        }
    }

    override fun invoke(function: process.() -> Unit): process = apply { function() }

    override fun addElement(element: Element) {
        childComponents.add("${element.type} ${element.name} ${element.color ?: this.element.color ?: ""}")
    }

    override fun toString(): String = buildString {
        with(childComponents) { if (isNotEmpty()) addElements(childComponents, layer.element) }
        appendLine(generateProcessRelations())
    }
}
