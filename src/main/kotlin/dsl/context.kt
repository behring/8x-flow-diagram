package dsl

import dsl.diagram_8x_flow.generateGenerics
import models.Participant
import models.Role
import java.lang.IllegalArgumentException


class context(val name: String) : Flow<context> {
    val allClasses: MutableList<String> = mutableListOf()
    val contracts: MutableList<contract> = mutableListOf()
    private val proposals: MutableList<proposal> = mutableListOf()
    private val participants: MutableList<Participant> = mutableListOf()
    private var roles: MutableList<Role> = mutableListOf()

    fun role_party(name: String): Role = Role(name, Role.Type.PARTY, this).apply { roles.add(this) }

    fun participant_party(name: String): Participant =
        Participant(name, Participant.Type.PARTY, this).apply { participants.add(this) }

    fun participant_place(name: String): Participant =
        Participant(name, Participant.Type.PLACE, this).apply { participants.add(this) }

    fun participant_thing(name: String): Participant =
        Participant(name, Participant.Type.THING, this).apply { participants.add(this) }


    fun proposal(name: String, role: Role, proposal: proposal.() -> Unit) = with(
        proposal(name, this, generateGenerics(role))
    ) {
        proposals.add(this)
        proposal()
    }

    fun contract(name: String, vararg roles: Role, contract: contract.() -> Unit) =
        with(contract(name, this, *roles)) {
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
        proposals.forEach {
            appendLine(it.toString())
        }

        participants.forEach {
            appendLine(it.toString())
        }

        contracts.forEach {
            appendLine(it.toString())
        }

        roles.forEach {
            appendLine(it.toString())
        }
    }

    private fun generateClassesInContext() = buildString {
        allClasses.forEach {
            appendLine("class $it")
        }
    }
}