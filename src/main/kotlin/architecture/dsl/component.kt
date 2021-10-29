package architecture.dsl

import architecture.dsl.inter_process.process
import architecture.dsl.intra_process.layer
import common.*

data class component(val element: Element) : Interactions, DSL<component> {
    constructor(element: Element, process: process) : this(element) {
        if (element.backgroundColor == null)
            element.backgroundColor = process.element.backgroundColor
    }

    constructor(element: Element, layer: layer) : this(element) {
        if (element.backgroundColor == null)
            element.backgroundColor = layer.element.backgroundColor
    }

    private val componentInteractions: MutableList<Pair<String, String>> = mutableListOf()

    fun call(componentName: String, command: String = "") {
        componentInteractions.add(Pair(componentName, command))
    }

    override fun invoke(function: component.() -> Unit): component = apply { function() }

    override fun toString(): String = buildString {
        appendLine(element)
        appendLine(generateInteractions(element, componentInteractions))
    }
}
