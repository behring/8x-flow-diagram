package dsl

import models.Evidences
import models.Role

class contract(name: String) : Evidences<contract>(name) {
    var fulfillments: MutableList<fulfillment> = mutableListOf()
    var roles: MutableList<Role> = mutableListOf()

    fun role_party(name: String): Role = Role(name, Role.Type.PARTY).apply { roles.add(this) }

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