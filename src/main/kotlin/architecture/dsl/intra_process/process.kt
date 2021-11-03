package architecture.dsl.intra_process

import common.Diagram.Companion.ASSOCIATE
import common.Element

class process(val element: Element) {

    fun call(componentName: String, command: String = "") {
        element.relate(componentName, ASSOCIATE, command)
    }

    override fun toString(): String = buildString {
        appendLine(element)
    }
}
