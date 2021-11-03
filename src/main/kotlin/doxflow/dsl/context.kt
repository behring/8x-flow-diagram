package doxflow.dsl

import common.Element
import doxflow.models.ability.BusinessAbility
import doxflow.models.ability.BusinessAbilityCreator
import doxflow.models.diagram.Participant
import doxflow.models.diagram.Party
import doxflow.models.diagram.Relationship.Companion.DEFAULT
import doxflow.models.diagram.Role

class context(val element: Element, override var resource: String = "") : BusinessAbility<context> {
    // bold, plain, dotted and dashed
    private val borderStyle:String = "dotted"

    private val proposals: MutableList<proposal> = mutableListOf()
    private val contracts: MutableList<contract> = mutableListOf()
    private val rfps: MutableList<rfp> = mutableListOf()
    private val participants: MutableList<Participant> = mutableListOf()
    private var roles: MutableList<Role> = mutableListOf()

    fun role_party(name: String): Role = Role(Element(name, "class"), Role.Type.PARTY, this).apply { roles.add(this) }

    fun role_domain(name: String): Role = Role(Element(name, "class"), Role.Type.DOMAIN, this).apply { roles.add(this) }

    fun role_context(name: String): Role = Role(Element(name, "class"), Role.Type.CONTEXT, this).apply { roles.add(this) }

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
        party: Party,
        relationship: String = DEFAULT,
        rfp: rfp.() -> Unit
    ) = with(
        rfp(Element(name, "class"), this, party)
    ) {
        this.relationship = relationship
        rfps.add(this)
        rfp()
    }

    fun proposal(
        name: String,
        role: Role,
        relationship: String = DEFAULT,
        proposal: proposal.() -> Unit
    ) = with(
        proposal(Element(name, "class"), this, role)
    ) {
        this.relationship = relationship
        resource = this.javaClass.simpleName
        proposals.add(this)
        proposal()
    }

    fun contract(name: String, vararg parties: Party, contract: contract.() -> Unit) =
        with(contract(Element(name, "class"), this, *parties)) {
            this.relationship = DEFAULT
            contracts.add(this)
            contract()
        }

    fun toApiString(): String = buildString {
        appendLine("## 业务能力表 - ${element.displayName}")
        addBusinessAbilities(BusinessAbilityCreator(element.displayName))
    }

    override fun invoke(function: context.() -> Unit): context = apply { function() }

    override fun toString(): String = buildString {
        appendLine("$element #line.${borderStyle} {")
        //participants和roles必须放最后toString，因为内部有设置关联关系，关联关系必须最后设置，否则会影响之后element的name样式
        // role必须在participants之前调用toString，因为role内部设置了和participants关联关系，否则会影响之后element的name样式
        arrayOf(rfps, proposals, contracts, roles, participants)
            .flatMap { it }.forEach { appendLine(it.toString()) }
        appendLine("}")
    }

    private fun StringBuilder.addBusinessAbilities(abilityCreator: BusinessAbilityCreator) {
        arrayOf(rfps, proposals, contracts).flatMap { it }.forEach { it.addBusinessAbility(abilityCreator) }
        appendLine(abilityCreator.create())
    }


}