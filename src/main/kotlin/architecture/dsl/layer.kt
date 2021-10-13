package architecture.dsl

import common.DSL

data class layer(val name: String) : DSL<layer> {

    fun process(name: String, function: process.() -> Unit): process = process(name).apply { function() }

    override fun invoke(function: layer.() -> Unit): layer = apply { function() }
}
