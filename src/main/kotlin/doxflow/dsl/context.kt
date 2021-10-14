package doxflow.dsl

import common.DSL
import common.TopContainer
import doxflow.diagram_8x_flow.generateGenerics
import doxflow.models.Participant
import doxflow.models.Role

class context(val name: String) : DSL<context>, TopContainer {
    val proposals: MutableList<proposal> = mutableListOf()
    val contracts: MutableList<contract> = mutableListOf()
    private val childClasses: MutableList<String> = mutableListOf()
    private val rfps: MutableList<rfp> = mutableListOf()
    private val participants: MutableList<Participant> = mutableListOf()
    private var roles: MutableList<Role> = mutableListOf()

    fun role_party(name: String): Role = Role(name, Role.Type.PARTY, this).apply { roles.add(this) }

    fun role_domain(name: String): Role = Role(name, Role.Type.DOMAIN, this).apply { roles.add(this) }

    fun role_3rd_system(name: String): Role = Role(name, Role.Type.THIRD_SYSTEM, this).apply { roles.add(this) }

    fun participant_party(name: String): Participant =
        Participant(name, Participant.Type.PARTY, this).apply { participants.add(this) }

    fun participant_place(name: String): Participant =
        Participant(name, Participant.Type.PLACE, this).apply { participants.add(this) }

    fun participant_thing(name: String): Participant =
        Participant(name, Participant.Type.THING, this).apply { participants.add(this) }


    fun rfp(name: String, role: Role, rfp: rfp.() -> Unit) = with(
        rfp(name, this, generateGenerics(role))
    ) {
        rfps.add(this)
        rfp()
    }

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

    override fun invoke(function: context.() -> Unit): context = apply { function() }

    override fun addElement(element: String) {
        childClasses.add("class $element")
    }

    override fun toString(): String = buildString {
        addElements(childClasses, name, "package")
        generateClasses()
    }

    private fun StringBuilder.generateClasses() = arrayOf(participants, roles, rfps, proposals, contracts)
        .flatMap { it }.forEach { appendLine(it.toString()) }


}