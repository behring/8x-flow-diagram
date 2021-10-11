package dsl

import models.Evidences

class proposal(name: String) : Evidences<proposal>(name) {

    override fun invoke(function: proposal.() -> Unit): proposal {
        return apply { function() }
    }

    override fun key_timestamps(vararg timestamps: String) {

    }
}