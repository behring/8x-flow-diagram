package doxflow.dsl

import doxflow.models.diagram.Evidence

class detail(name: String, context: context) : Evidence<detail>(name, context) {

    override fun invoke(function: detail.() -> Unit): detail {
        return apply { function() }
    }

    override val type: String
        get() = detail::class.java.simpleName
}