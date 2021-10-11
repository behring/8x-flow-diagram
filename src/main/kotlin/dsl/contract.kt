package dsl

import models.Evidences

class contract(name: String) : Evidences<contract>(name) {
    var fulfillments: MutableList<fulfillment> = mutableListOf()

    fun fulfillment(name: String, fulfillment: fulfillment.() -> Unit) =  with(fulfillment(name)) {
        fulfillments.add(this)
        fulfillment()
    }

    override fun invoke(function: contract.() -> Unit): contract {
        return apply { function() }
    }

    override val type: String
        get() = contract::class.java.simpleName
}