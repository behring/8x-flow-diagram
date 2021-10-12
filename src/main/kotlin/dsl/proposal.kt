package dsl

import models.Evidence

class proposal(name: String, context: context) : Evidence<proposal>(name, context) {

    override fun invoke(function: proposal.() -> Unit): proposal {
        return apply { function() }
    }

    override val type: String
        get() = proposal::class.java.simpleName
}