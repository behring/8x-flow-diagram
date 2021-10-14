package architecture.dsl.intra_process

import architecture.dsl.component
import architecture.models.Element
import common.DSL
import common.ParentContainer

class layer(override val element: Element) : DSL<layer>, ParentContainer {
    private val childComponents: MutableList<String> = mutableListOf()
    val components: MutableList<component> = mutableListOf()

    override val backgroundColor: String?
        get() = element.color

    fun component(name: String, color: String? = null, function: (component.() -> Unit)? = null): component =
        component(Element(name, "rectangle", color), this).apply {
            components.add(this)
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
