package dsl

import models.Evidences

class evidence(name: String) : Evidences(name, Type.EVIDENCE), Flow<evidence> {

    override fun invoke(function: evidence.() -> Unit): evidence {
        return apply { function() }
    }

    override fun key_timestamps(vararg timestamps: String) {
    }
}