package dsl

import models.Evidences

class contract(name: String) : Evidences<contract>(name) {

    fun fulfillment(name: String, fulfillment: fulfillment.() -> Unit) = fulfillment(name).fulfillment()

    override fun invoke(function: contract.() -> Unit): contract {
        return apply { function() }
    }

    override fun key_timestamps(vararg timestamps: String) {
        this.timestamps = timestamps
    }

    override val type: String
        get() = contract::class.java.simpleName
}