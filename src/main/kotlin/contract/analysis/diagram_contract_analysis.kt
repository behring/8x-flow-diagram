package contract.analysis

import common.*

object diagram_contract_analysis : DSL<diagram_contract_analysis>, Diagram {
    override fun buildPlantUmlString(): String = """
        |@startuml
        |skinparam classFontColor White
        |skinparam classAttributeFontColor White
        |skinparam roundCorner 10
        |hide circle
        ${buildPlantUmlContent()}
        |@enduml
        """.trimMargin()

    private fun buildPlantUmlContent(): String {
        return ""
    }

    override fun invoke(function: diagram_contract_analysis.() -> Unit): diagram_contract_analysis =
        apply { function() }
}



