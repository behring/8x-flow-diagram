package architecture.dsl

import common.*

data class component(val element: Element, val container: ParentContainer) : Interactions, DSL<component> {
    init {
        container.addElement(element)
    }

    private val componentInteractions: MutableList<Pair<String, String>> = mutableListOf()

    fun call(componentName: String, command: String = "") {
        componentInteractions.add(Pair(componentName, command))
    }

    override fun invoke(function: component.() -> Unit): component = apply { function() }

    override fun toString(): String = buildString {
        appendLine(generateInteractions(element, componentInteractions))
    }
}
