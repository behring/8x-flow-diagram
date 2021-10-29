package architecture.dsl.intra_process

import common.Element
import doxflow.models.diagram.Relationship.Companion.ASSOCIATE

class process(val element: Element) {

    fun call(componentName: String, command: String = "") {
        element.relate(componentName, ASSOCIATE, command)
    }

    override fun toString(): String = buildString {
        appendLine(element)
    }
}
