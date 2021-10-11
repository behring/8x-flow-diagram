package dsl

import models.Evidences

class evidence(name: String) : Evidences<evidence>(name) {

    override fun invoke(function: evidence.() -> Unit): evidence {
        return apply { function() }
    }

    override fun key_timestamps(vararg timestamps: String) {
    }

    override val type: String
        get() = evidence::class.java.simpleName
}