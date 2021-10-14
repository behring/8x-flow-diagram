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

    override fun exportResult(isSuccess: Boolean) {
        if (isSuccess) layers.clear()
    }

    private fun buildPlantUmlContent(): String = buildString {
        layers.forEach { layer ->
            appendLine(layer.toString())
        }
        /**
         * 注意：此方法不能再layers.forEach中继续对process进行forEach调用，
         * 必须在所有的layer构建完成后再构建process，否则生成图中，process会脱离layer
         * */
        layers.flatMap { it.processes }.forEach {
            appendLine(it.toString())
        }
    }
}