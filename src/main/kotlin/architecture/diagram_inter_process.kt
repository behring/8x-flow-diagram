package architecture

import architecture.dsl.layer
import architecture.models.Element
import common.DSL
import common.Diagram

object diagram_inter_process : DSL<diagram_inter_process>, Diagram {
    var layers: MutableList<layer> = mutableListOf()

    fun layer(name: String, color: String? = null, function: layer.() -> Unit): layer =
        layer(Element(name, "rectangle", color)).apply {
            layers.add(this)
            function()
        }

    override fun invoke(function: diagram_inter_process.() -> Unit): diagram_inter_process = apply { function() }

    override fun buildPlantUmlString(): String = """
        |@startuml
        |skinparam componentStyle rectangle
        |skinparam rectangleFontColor black
        ${buildPlantUmlContent()}
        |@enduml
        """.trimMargin()

    private fun buildPlantUmlContent(): String = buildString {
        layers.forEach { layer ->
            appendLine(layer.toString())
            layer.processes.forEach { process ->
                appendLine(process.toString())
            }
        }
    }
}