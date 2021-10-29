package doxflow.dsl

import common.Element
import doxflow.models.diagram.Evidence

class detail(element: Element) : Evidence<detail>(element, detail::class) {

    override fun invoke(function: detail.() -> Unit): detail {
        return apply { function() }
    }
}