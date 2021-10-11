package dsl

import models.Evidences

class evidence(name: String) : Evidences<evidence>(name) {

    override fun invoke(function: evidence.() -> Unit): evidence {
        return apply { function() }
    }

    override val type: String
        get() = evidence::class.java.simpleName
}