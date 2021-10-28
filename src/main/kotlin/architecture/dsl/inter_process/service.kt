package architecture.dsl.inter_process

import common.Element
import common.DSL
import common.ParentContainer

class service(override val element: Element) : DSL<service>, ParentContainer {
    private val childComponents: MutableList<Element> = mutableListOf()
    val processes: MutableList<process> = mutableListOf()

    override val backgroundColor: String?
        get() = element.backgroundColor

    fun process(name: String, color: String? = null, function: (process.() -> Unit)? = null): process {
        val process = process(Element(name, "rectangle", color), this)
        processes.add(process)
        element.childElements.add(process.element)
        function?.let { process.it() }
        return process
    }


    override fun invoke(function: service.() -> Unit): service = apply { function() }

    override fun addElement(element: Element) {
        childComponents.add(element)
    }

    override fun toString(): String = buildString {
        with(childComponents) { if (isNotEmpty()) addElements(childComponents, element) }
    }
}
