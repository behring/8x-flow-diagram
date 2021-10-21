package contract.content.dsl

import common.DSL


class contract(val name: String) : DSL<contract> {
    fun fulfillment(name: String, evidence: String, function: fulfillment.() -> Unit): fulfillment =
        fulfillment(name, evidence).apply { function() }

    override fun invoke(function: contract.() -> Unit): contract = apply {
        function()
    }
}

class fulfillment(val name: String, val evidence: String) : DSL<fulfillment> {

    override fun invoke(function: fulfillment.() -> Unit) = apply { function() }
}

class person(val party: String, val name: String) {
    infix fun request(fulfillment: fulfillment) {

    }

    infix fun confirmation_within(time: String) {

    }
}