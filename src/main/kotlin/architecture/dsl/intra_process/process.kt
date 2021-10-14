package architecture.dsl.intra_process

import architecture.models.Element
import common.DSL

class process(val element: Element) : DSL<process> {
    private val componentInteractions: MutableList<Pair<String, String>> = mutableListOf()

    fun call(componentName: String, command: String = "") {
        componentInteractions.add(Pair(componentName, command))
    }

    override fun invoke(function: process.() -> Unit): process = apply { function() }

    override fun toString(): String = buildString {
        with(element) { appendLine("$type $name $color") }
        appendLine(generateInteractions(element, componentInteractions))
    }
}
