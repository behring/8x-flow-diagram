package dsl

import models.Evidences
import models.Role

class contract(name: String) : Evidences(name, Type.CONTRACT), Flow<contract> {
    fun role_party(name: String): Role = Role(name, Role.Type.PARTY)

    fun fulfillment(name: String, fulfillment: fulfillment.() -> Unit) = fulfillment(name).fulfillment()

    override fun invoke(function: contract.() -> Unit): contract {
        return apply { function() }
    }

    override fun key_timestamps(vararg timestamps: String) {

    }

}