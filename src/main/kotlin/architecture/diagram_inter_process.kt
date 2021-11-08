package architecture

import architecture.dsl.inter_process.service
import common.DSL
import common.Diagram
import common.Diagram.Color.BLACK
import common.Diagram.Color.TRANSPARENT
import common.Element
import common.Element.Type.RECTANGLE

class diagram_inter_process(val name: String = "", function: diagram_inter_process.() -> Unit) : DSL<diagram_inter_process>, Diagram {
    var services: MutableList<service> = mutableListOf()
    init {
        function()
    }

    fun service(name: String, color: String = TRANSPARENT, function: service.() -> Unit): service =
        service(Element(name, RECTANGLE, color)).apply {
            services.add(this)
            function()
        }

    override fun invoke(function: diagram_inter_process.() -> Unit): diagram_inter_process = apply { function() }

    override fun buildPlantUmlString(): String = """
        |@startuml
        |skinparam rectangleFontColor white
        |$RECTANGLE <size:20><color:black>$name {
        ${buildPlantUmlContent()}
        |}
        |@enduml
        """.trimMargin()

    override fun exportResult(isSuccess: Boolean) {
        if (isSuccess) services.clear()
    }

    private fun buildPlantUmlContent(): String = buildString {
        services.reversed().forEach { appendLine(it.toString()) }
        services.forEach { appendLine(it.element.generateRelationships()) }
    }
}