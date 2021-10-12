package dsl

import models.Evidence

class detail(name: String) : Evidence<detail>(name) {

    override fun invoke(function: detail.() -> Unit): detail {
        return apply { function() }
    }

    override val type: String
        get() = detail::class.java.simpleName
}