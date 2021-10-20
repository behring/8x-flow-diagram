package contract.analysis

import common.*
import contract.dsl.contract
import contract.dsl.evidence
import contract.dsl.proposal
import contract.dsl.rfp

object diagram_contract_analysis : DSL<diagram_contract_analysis>, Diagram {
    private var evidences: MutableList<evidence> = mutableListOf()

    fun evidence(name: String, function: evidence.() -> Unit): evidence = dsl(name, function)

    fun rfp(name: String, function: rfp.() -> Unit): rfp = dsl(name, function)

    fun proposal(name: String, function: proposal.() -> Unit): proposal = dsl(name, function)

    fun contract(name: String, function: contract.() -> Unit): contract = dsl(name, function)

    private inline fun <reified T> dsl(name: String, function: T.() -> Unit) =
        T::class.java.getDeclaredConstructor(String::class.java).newInstance(name).apply {
            if (this is evidence) {
                evidences.add(this)
                function()
            }
        }

    override fun buildPlantUmlString(): String = """
        |@startuml
        |skinparam classFontColor White
        |skinparam classAttributeFontColor White
        |skinparam roundCorner 10
        |hide circle
        ${buildPlantUmlContent()}
        |@enduml
        """.trimMargin()

    private fun buildPlantUmlContent(): String = buildString {
        evidences.forEach { appendLine(it.toString()) }
    }

    override fun invoke(function: diagram_contract_analysis.() -> Unit): diagram_contract_analysis =
        apply { function() }
}



