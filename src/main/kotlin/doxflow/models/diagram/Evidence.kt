package doxflow.models.diagram

import common.Diagram
import common.Diagram.Color.PINK
import common.Element
import common.Element.Type.CLASS
import common.IElement
import doxflow.dsl.evidence
import doxflow.models.ability.BusinessAbility
import doxflow.models.ability.BusinessAbilityCreator
import doxflow.models.diagram.Relationship.Companion.DEFAULT
import doxflow.models.diagram.Relationship.Companion.NONE
import kotlin.reflect.KClass

abstract class Evidence<T : Any>(
    override val element: Element,
    type: KClass<out T>,
    val party: Party? = null,
    private val note: String? = null,
    override var resource: String = ""
) : BusinessAbility<T>, Diagram.KeyInfo<T>, IElement {

    init {
        element.backgroundColor = PINK
        element.stereoType = type.simpleName.toString()
    }

    var isRole: Boolean = false
    var timestamps: Array<out String>? = null
    private var data: Array<out String>? = null

    var relationship: String = DEFAULT
    private var evidenceAndRelationship: Pair<evidence, String>? = null

    fun evidence(name: String, relationship: String = NONE, function: (evidence.() -> Unit)? = null): evidence =
        evidence(Element(name, CLASS)).apply {
            function?.let { it() }
            evidenceAndRelationship = Pair(this, relationship)
        }


    open fun getUriPrefix(): String = ""

    open fun addBusinessAbility(abilityCreator: BusinessAbilityCreator) {
        abilityCreator.appendBusinessAbility(
            resource,
            relationship,
            getUriPrefix(),
            element.name,
            party?.element?.name ?: ""
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
            |${if (!isRole) party ?: "" else ""}${if (!isRole && timestamps != null && party != null) "\n..\n" else ""}${timestamps?.joinToString() ?: ""}
            |${if (timestamps != null && data != null) "..\n" else ""}${data?.joinToString() ?: ""}
            |}
        """.trimIndent()
        )
        evidenceAndRelationship?.let {
            appendLine(it.first.toString())
            it.first.element.relate(element, it.second)
            appendLine(it.first.element.generateRelationships())
        }
    }

    private fun generateParty(party: Party?): String? = party?.let {
        """
            ||<${party.element.backgroundColor}> <size:14>${party.element.name}</size>|
        """.trimIndent()
    }
}