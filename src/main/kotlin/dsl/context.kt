package dsl

import models.Participant



class context(val name: String) : Flow<context> {
    val allClasses:MutableList<String> = mutableListOf()
    private val contracts: MutableList<contract> = mutableListOf()
    private val participants: MutableList<Participant> = mutableListOf()

    fun participant_party(name: String): Participant =
        Participant(name, Participant.Type.PARTY, this).apply { participants.add(this) }

    fun participant_place(name: String): Participant =
        Participant(name, Participant.Type.PLACE, this).apply { participants.add(this) }

    fun participant_thing(name: String): Participant =
        Participant(name, Participant.Type.THING, this).apply { participants.add(this) }

    fun contract(name: String, contract: contract.() -> Unit) = with(contract(name, this)) {
        contracts.add(this)
        contract()
    }

    override fun invoke(function: context.() -> Unit): context {
        return apply { function() }
    }

    override fun toString(): String = buildString {
        appendLine(
            """        
            package <color:black>$name</color> {
                ${generateClassesInContext()}
            }
        """.trimIndent()
        )
        participants.forEach {
            appendLine(it.toString())
        }

        contracts.forEach { contract ->
            appendLine(contract.toString())
        }
    }

    private fun generateClassesInContext() = buildString {
        allClasses.forEach {
            appendLine("class $it")
        }
    }
}