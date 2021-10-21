package architecture.dsl

import common.ChildElement
import common.DSL
import common.Element
import common.ParentContainer

data class component(val element: Element, val container: ParentContainer) : ChildElement(element, container),
    DSL<component> {
    private val componentInteractions: MutableList<Pair<String, String>> = mutableListOf()

    fun call(componentName: String, command: String = "") {
        componentInteractions.add(Pair(componentName, command))
    }

    override fun invoke(function: component.() -> Unit): component = apply { function() }

    override fun toString(): String = buildString {
        appendLine(generateInteractions(element, componentInteractions))
    }
}
