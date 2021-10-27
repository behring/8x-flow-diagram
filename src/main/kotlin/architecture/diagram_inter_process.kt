package architecture

import architecture.dsl.inter_process.service
import common.DSL
import common.Diagram
import common.Element

object diagram_inter_process : DSL<diagram_inter_process>, Diagram {
    var services: MutableList<service> = mutableListOf()

    fun service(name: String, color: String? = null, function: service.() -> Unit): service =
        service(Element(name, "rectangle", color)).apply {
            services.add(this)
            function()
        }

    override fun invoke(function: diagram_inter_process.() -> Unit): diagram_inter_process = apply { function() }

    override fun buildPlantUmlString(): String = """
        |@startuml
        |skinparam rectangleFontColor black
        ${buildPlantUmlContent()}
        |@enduml
        """.trimMargin()

    override fun exportResult(isSuccess: Boolean) {
        if (isSuccess) services.clear()
    }

    private fun buildPlantUmlContent(): String = buildString {
        services.forEach { service ->
            appendLine(service.toString())
        }
        /**
         * 注意：此方法不能再services.forEach中继续对process进行forEach调用，
         * 必须在所有的service构建完成后再构建process，否则生成图中，process会脱离service
         * */
        services.flatMap { it.processes }.forEach {
            appendLine(it.toString())
        }
    }
}