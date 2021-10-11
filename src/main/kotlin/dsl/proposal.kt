package dsl

import models.Evidence

class proposal(name: String) : Evidence<proposal>(name) {

    override fun invoke(function: proposal.() -> Unit): proposal {
        return apply { function() }
    }

    override val type: String
        get() = proposal::class.java.simpleName
}