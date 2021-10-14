package architecture.dsl

import common.DSL
import common.TopContainer

data class layer(val name: String, val color: String? = null) : DSL<layer>, TopContainer {
    private val childComponents: MutableList<String> = mutableListOf()

    override val backgroundColor: String?
        get() = color

    fun process(name: String, function: (process.() -> Unit)? = null): process =
        process(name, this).apply { function?.let { it() } }

    override fun invoke(function: layer.() -> Unit): layer = apply { function() }

    override fun addElement(element: String) {
        childComponents.add("rectangle $element ${color?:""}")
    }

    override fun toString(): String = buildString {
        addElements(childComponents, name, "package")
    }
}
