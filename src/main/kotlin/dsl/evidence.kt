package dsl

import models.Evidence

class evidence(name: String) : Evidence<evidence>(name) {

    override fun invoke(function: evidence.() -> Unit): evidence {
        return apply { function() }
    }

    override val type: String
        get() = evidence::class.java.simpleName
}