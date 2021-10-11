package dsl

import models.Evidence

class rfp(name: String) : Evidence<rfp>(name) {

    override fun invoke(function: rfp.() -> Unit): rfp {
        return apply { function() }
    }

    override val type: String
        get() = rfp::class.java.simpleName
}