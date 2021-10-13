package architecture.dsl

import common.DSL

data class component(val name: String) : DSL<component> {

    fun process(name: String, function: process.() -> Unit): process = process(name).apply { function() }

    override fun invoke(function: component.() -> Unit): component = apply { function() }
}
