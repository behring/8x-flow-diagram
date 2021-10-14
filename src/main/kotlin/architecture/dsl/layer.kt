package architecture.dsl

import architecture.models.Element
import common.DSL
import common.ParentContainer
import doxflow.dsl.proposal

data class layer(val element: Element) : DSL<layer>, ParentContainer {
    private val childComponents: MutableList<String> = mutableListOf()
    val processes: MutableList<process> = mutableListOf()

    override val backgroundColor: String?
        get() = element.color

    fun process(name: String, color: String? = null, function: (process.() -> Unit)? = null): process =
        process(Element(name, "rectangle", color), this).apply {
            processes.add(this)
            function?.let { it() }
        }

    override fun invoke(function: layer.() -> Unit): layer = apply { function() }

    override fun addElement(element: Element) {
        childComponents.add("${element.type} ${element.name} ${element.color ?: this.element.color ?: ""}")
    }

    override fun toString(): String = buildString {
        addElements(childComponents, element)
    }
}
