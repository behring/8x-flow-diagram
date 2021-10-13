package architecture.dsl

import common.DSL

data class process(val name: String) : DSL<process> {
    infix fun call(process: process) {

    }

    fun component(name: String, function: (component.() -> Unit)? = null): component =
        component(name).apply { function?.let { it() } }

    override fun invoke(function: process.() -> Unit): process = apply { function() }

}
