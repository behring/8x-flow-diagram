package dsl

import models.Participant

class context(val name: String) : Flow<context> {
    var contracts: MutableList<contract> = mutableListOf()
    var participants: MutableList<Participant> = mutableListOf()

    fun participant_party(name: String): Participant =
        Participant(name, Participant.Type.PARTY).apply { participants.add(this) }

    fun participant_place(name: String): Participant =
        Participant(name, Participant.Type.PLACE).apply { participants.add(this) }

    fun participant_thing(name: String): Participant =
        Participant(name, Participant.Type.THING).apply { participants.add(this) }

    fun contract(name: String, contract: contract.() -> Unit) = with(contract(name)) {
        contracts.add(this)
        contract()
    }

    override fun invoke(function: context.() -> Unit): context {
        return apply { function() }
    }

    override fun toString(): String {
        return """        
            package <color:black>$name</color> {
                ${generateClassesInContext()}
            }
        """.trimIndent()
    }

    private fun generateClassesInContext() = buildString {
        participants.forEach {
            appendLine("class ${it.name}")
        }
        contracts.forEach { contract ->
            appendLine("class ${contract.name}")
            contract.roles.forEach { role ->
                appendLine("class ${role.name}")
            }
            contract.fulfillments.forEach { fulfillment ->
                appendLine("class ${fulfillment.request.name}")
                appendLine("class ${fulfillment.confirmation.name}")
                fulfillment.confirmation.generateClassesInContext(this)
                fulfillment.confirmation.evidence?.generateClassesInContext(this)
            }
        }
    }
}