package doxflow.dsl

import common.Element
import doxflow.models.diagram.Evidence

class detail(element: Element, context: context) : Evidence<detail>(element, context) {

    override fun invoke(function: detail.() -> Unit): detail {
        return apply { function() }
    }

    override val type: String
        get() = detail::class.java.simpleName
}