package architecture.dsl

import common.DSL
import common.TopContainer

data class layer(val name: String) : DSL<layer>, TopContainer {
    private val childComponents: MutableList<String> = mutableListOf()

    fun process(name: String, function: (process.() -> Unit)? = null): process =
        process(name, this).apply { function?.let { it() } }

    override fun invoke(function: layer.() -> Unit): layer = apply { function() }

    override fun addElement(element: String) {
        childComponents.add("component $element")
    }

    override fun toString(): String = buildString {
        addElements(childComponents, name, "package")
    }
}
