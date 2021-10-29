package architecture.dsl.intra_process

import architecture.dsl.component
import common.Element
import common.DSL

class layer(val element: Element) : DSL<layer> {
    private val components: MutableList<component> = mutableListOf()

    fun component(name: String, color: String? = null, function: (component.() -> Unit)? = null): component =
        component(Element(name, "rectangle", color), this).apply {
            components.add(this)
            function?.let { it() }
        }

    override fun invoke(function: layer.() -> Unit): layer = apply { function() }

    override fun toString(): String = buildString {
        appendLine("$element {")
        components.forEach {
            appendLine(it.toString())
        }
        appendLine("}")
    }
}
