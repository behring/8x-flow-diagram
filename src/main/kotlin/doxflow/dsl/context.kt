package doxflow.dsl

import common.Element
import doxflow.models.ability.BusinessAbility
import doxflow.models.ability.BusinessAbilityTable
import doxflow.models.diagram.Participant
import doxflow.models.diagram.Relationship.Companion.ONE_TO_ONE
import doxflow.models.diagram.Role

class context(val element: Element, override var resource: String = "") : BusinessAbility<context> {
    val proposals: MutableList<proposal> = mutableListOf()
    val contracts: MutableList<contract> = mutableListOf()
    private val rfps: MutableList<rfp> = mutableListOf()
    private val participants: MutableList<Participant> = mutableListOf()
    private var roles: MutableList<Role> = mutableListOf()

    fun role_party(name: String): Role = Role(Element(name, "class"), Role.Type.PARTY, this).apply { roles.add(this) }

    fun role_domain(name: String): Role = Role(Element(name, "class"), Role.Type.DOMAIN, this).apply { roles.add(this) }

    fun role_3rd_system(name: String): Role =
        Role(Element(name, "class"), Role.Type.THIRD_SYSTEM, this).apply { roles.add(this) }

    fun participant_party(name: String): Participant =
        Participant(Element(name, "class"), Participant.Type.PARTY, this).apply { participants.add(this) }

    fun participant_place(name: String): Participant =
        Participant(Element(name, "class"), Participant.Type.PLACE, this).apply { participants.add(this) }

    fun participant_thing(name: String): Participant =
        Participant(Element(name, "class"), Participant.Type.THING, this).apply { participants.add(this) }


    fun rfp(
        name: String,
        role: Role,
        relationship: String = ONE_TO_ONE,
        rfp: rfp.() -> Unit
    ) = with(
        rfp(Element(name, "class"), this, role)
    ) {
        this.relationship = relationship
        rfps.add(this)
        rfp()
    }

    fun proposal(
        name: String,
        role: Role,
        relationship: String = ONE_TO_ONE,
        proposal: proposal.() -> Unit
    ) = with(
        proposal(Element(name, "class"), this, role)
    ) {
        this.relationship = relationship
        resource = this.javaClass.simpleName
        proposals.add(this)
        proposal()
    }

    fun contract(name: String, vararg roles: Role, contract: contract.() -> Unit) =
        with(contract(Element(name, "class"), this, *roles)) {
            this.relationship = ONE_TO_ONE
            contracts.add(this)
            contract()
        }

    fun toApiString(): String = buildString {
        appendLine("## 业务能力表 - ${element.displayName}")
        addBusinessAbilities(BusinessAbilityTable())
    }

    override fun invoke(function: context.() -> Unit): context = apply { function() }

    override fun toString(): String = buildString {
        appendLine("$element {")
        //participants和roles必须放最后toString，因为内部有设置关联关系，关联关系必须最后设置，否则会影响之后element的name样式
        // role必须在participants之前调用toString，因为role内部设置了和participants关联关系，否则会影响之后element的name样式
        arrayOf(rfps, proposals, contracts, roles, participants)
            .flatMap { it }.forEach { appendLine(it.toString()) }
        appendLine("}")
    }

    private fun StringBuilder.addBusinessAbilities(table: BusinessAbilityTable) {
        arrayOf(rfps, proposals, contracts).flatMap { it }.forEach { it.addBusinessAbility(table) }
        appendLine(table.toString())
    }


}