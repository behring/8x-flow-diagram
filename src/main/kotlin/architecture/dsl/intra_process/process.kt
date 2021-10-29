package architecture.dsl.intra_process

import common.Element
import common.Interactions

class process(val element: Element) : Interactions {
    private val componentInteractions: MutableList<Pair<String, String>> = mutableListOf()

    fun call(componentName: String, command: String = "") {
        componentInteractions.add(Pair(componentName, command))
    }

    override fun toString(): String = buildString {
        appendLine(element)
        appendLine(generateInteractions(element, componentInteractions))
    }
}
