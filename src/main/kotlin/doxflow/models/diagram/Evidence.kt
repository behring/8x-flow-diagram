package doxflow.models.diagram

import common.Diagram
import common.Diagram.Color.PINK
import common.Diagram.Color.YELLOW
import common.Element
import doxflow.models.ability.BusinessAbility
import doxflow.dsl.context
import doxflow.models.ability.BusinessAbilityTable
import doxflow.models.ability.BusinessAbilityTable.Row
import kotlin.reflect.KClass

abstract class Evidence<T : Any>(
    val element: Element,
    val context: context,
    type: KClass<out T>,
    val role: Role? = null,
    private val note: String? = null,
    override var resource: String = ""
) : BusinessAbility<T>, Diagram.KeyInfo<T>, Relationship {

    init {
        element.backgroundColor = PINK
        element.stereoType = "<<${type.simpleName.toString()}>>"
        context.addElement(element)
    }

    var isRole: Boolean = false
        set(value) {
            if (value) element.backgroundColor = YELLOW
            field = value
        }
    var timestamps: Array<out String>? = null
    private var data: Array<out String>? = null

    open fun getUriPrefix(): String = ""

    override var relationship_type: RelationShipType = RelationShipType.ONE_TO_ONE

    open fun getUri(): String = when (relationship_type) {
        RelationShipType.ONE_TO_ONE -> "${getUriPrefix()}/$resource"
        RelationShipType.ONE_TO_N -> "${getUriPrefix()}/${resource.pluralize()}/{${resource[0]}id}"
        else -> "${getUriPrefix()}/$resource"
    }

    open fun addBusinessAbility(table: BusinessAbilityTable) {
        if (resource.isBlank()) return
        val roleName = role?.element?.displayName ?: ""
        val serviceName = "${context.element.displayName}服务"
        val singularUri: String
        when (relationship_type) {
            RelationShipType.ONE_TO_ONE -> {
                singularUri = "${getUriPrefix()}/$resource"
                table.addRow(Row("POST", singularUri, "创建${element.displayName}", serviceName, roleName))
            }
            RelationShipType.ONE_TO_N -> {
                val pluralUri = "${getUriPrefix()}/${resource.pluralize()}"
                singularUri = "$pluralUri/{${resource[0]}id}"
                table.addRow(Row("POST", pluralUri, "创建${element.displayName}", serviceName, roleName))
                table.addRow(Row("GET", pluralUri, "查看${element.displayName}列表", serviceName))
            }
            else -> singularUri = "${getUriPrefix()}/$resource"
        }
        table.addRow(Row("GET", singularUri, "查看${element.displayName}", serviceName, roleName))
        table.addRow(Row("PUT", singularUri, "更改${element.displayName}", serviceName, roleName))
        table.addRow(Row("DELETE", singularUri, "取消${element.displayName}", serviceName, roleName))
    }

    override fun key_timestamps(vararg timestamps: String) = timestamps.let { this.timestamps = it }

    override fun key_data(vararg data: String) = data.let { this.data = it }

    override fun toString(): String {
        return """
            |${note ?: ""}
            |$element {
            |   ${if (!isRole) generateRole(role) ?: "" else ""} ${timestamps?.joinToString() ?: ""}
            |   ${if (timestamps != null && data != null) "..\n" else ""} ${data?.joinToString() ?: ""}
            |}
        """.trimIndent()
    }


    private fun generateRole(role: Role?): String? = role?.let {
        """
            |<${role.element.backgroundColor}> <size:14>${role.element.displayName}</size> |
            |..
            |
        """.trimIndent()
    }
}