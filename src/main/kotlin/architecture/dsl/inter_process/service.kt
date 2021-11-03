package architecture.dsl.inter_process

import common.Element
import common.DSL
import common.Diagram.Companion.POSITION

class service(val element: Element) : DSL<service> {
    private val processes: MutableList<process> = mutableListOf()

    fun process(name: String, color: String? = null, function: (process.() -> Unit)? = null): process {
        val process = process(Element(name, "rectangle", color), this)
        processes.add(process)
        function?.let { process.it() }
        return process
    }

    infix fun above(service: service) {
        element.relate(service.element, POSITION)
    }

    infix fun below(service: service) {
        service.element.relate(element, POSITION)
    }

    override fun invoke(function: service.() -> Unit): service = apply { function() }

    override fun toString(): String = buildString {
        appendLine("$element {")
        processes.forEach {
            appendLine(it.toString())
        }
        appendLine("}")
    }
}
