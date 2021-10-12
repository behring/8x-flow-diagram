package dsl

import models.Evidence

class rfp(name: String, context: context) : Evidence<rfp>(name, context) {

    override fun invoke(function: rfp.() -> Unit): rfp {
        return apply { function() }
    }

    override val type: String
        get() = rfp::class.java.simpleName
}