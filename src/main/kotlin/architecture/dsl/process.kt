package architecture.dsl

import common.ChildElement
import common.DSL

class process(name: String, private val layer: layer) : ChildElement(name, layer), DSL<process> {
    infix fun call(process: process) {

    }

    fun component(name: String): component = component(name, layer.backgroundColor, layer)

    override fun invoke(function: process.() -> Unit): process = apply { function() }

}
