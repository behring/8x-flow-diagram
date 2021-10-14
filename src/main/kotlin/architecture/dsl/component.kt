package architecture.dsl

import architecture.models.Element
import common.ChildElement
import common.DSL

data class component(val element: Element, val process: process) : ChildElement(element, process),
    DSL<component> {

    override fun invoke(function: component.() -> Unit): component = apply { function() }
}
