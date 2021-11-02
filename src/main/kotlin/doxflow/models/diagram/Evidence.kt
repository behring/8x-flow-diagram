package doxflow.models.diagram

import common.Diagram
import common.Diagram.Color.PINK
import common.Diagram.Color.YELLOW
import common.Element
import doxflow.dsl.evidence
import doxflow.models.ability.BusinessAbility
import doxflow.models.ability.BusinessAbilityCreator
import doxflow.models.diagram.Relationship.Companion.ONE_TO_ONE
import kotlin.reflect.KClass

abstract class Evidence<T : Any>(
    val element: Element,
    type: KClass<out T>,
    val party: Party? = null,
    private val note: String? = null,
    override var resource: String = ""
) : BusinessAbility<T>, Diagram.KeyInfo<T> {

    init {
        element.backgroundColor = PINK
        element.stereoType = type.simpleName.toString()
    }

    var isRole: Boolean = false
        set(value) {
            if (value) element.backgroundColor = YELLOW
            field = value
        }
    var timestamps: Array<out String>? = null
    private var data: Array<out String>? = null

    var relationship: String = ONE_TO_ONE
    private var evidence: evidence? = null

    fun evidence(name: String, evidence: (evidence.() -> Unit)? = null): evidence {
        this.evidence = evidence(Element(name, "class"))
        return this.evidence!!.apply { evidence?.let { it() } }
    }

    open fun getUriPrefix(): String = ""

    open fun addBusinessAbility(abilityCreator: BusinessAbilityCreator) {
        abilityCreator.appendBusinessAbility(
            resource,
            relationship,
            getUriPrefix(),
            element.displayName,
            party?.element?.displayName ?: ""
        )
    }

    override fun key_timestamps(vararg timestamps: String) = timestamps.let { this.timestamps = it }

    override fun key_data(vararg data: String) = data.let { this.data = it }

    override fun toString(): String = buildString {
        val party = generateParty(party)
        appendLine(
            """
            |${note ?: ""}
            |$element {
            | ${if (!isRole) party ?: "" else ""} 
            | ${if (party != null && timestamps != null) "..\n" else ""} 
            | ${timestamps?.joinToString() ?: ""}
            | ${if (timestamps != null && data != null) "..\n" else ""} 
            | ${data?.joinToString() ?: ""}
            |}
        """.trimIndent()
        )
        evidence?.let {
            appendLine(evidence.toString())
            appendLine("""${it.element.displayName} ${Relationship.NONE} ${element.displayName}""")
        }
    }

    private fun generateParty(party: Party?): String? = party?.let {
        "|<${party.element.backgroundColor}> <size:14>${party.element.displayName}</size> |"
    }
}