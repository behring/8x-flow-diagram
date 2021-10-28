package doxflow.models.diagram

import common.ChildElement
import common.Color.PINK
import common.Color.YELLOW
import doxflow.models.ability.BusinessAbility
import common.KeyInfo
import doxflow.diagram_8x_flow.generateRole
import doxflow.dsl.context
import doxflow.models.ability.BusinessAbilityTable

abstract class Evidence<T>(
    val name: String,
    val context: context,
    val role: Role? = null,
    private val note: String? = null,
    override var resource: String = ""
) : ChildElement(name, "class", context), BusinessAbility<T>, KeyInfo<T>, Relationship {
    var isRole: Boolean = false
    var timestamps: Array<out String>? = null
    private var data: Array<out String>? = null

    /**
     * Evidence的类型：包括rfp，proposal，contract，request, confirmation
     * */
    abstract val type: String

    open fun getUriPrefix(): String = ""

    override var relationship_type: RelationShipType = RelationShipType.ONE_TO_ONE

    open fun getUri(): String = when (relationship_type) {
        RelationShipType.ONE_TO_ONE -> "${getUriPrefix()}/$resource"
        RelationShipType.ONE_TO_N -> "${getUriPrefix()}/${resource.pluralize()}/{${resource[0]}id}"
        else -> "${getUriPrefix()}/$resource"
    }

    open fun addBusinessAbility(table: BusinessAbilityTable) {
        if (resource.isBlank()) return
        val roleName = role?.element?.name ?: ""
        val serviceName = "${context.element.name}服务"
        val singularUri: String
        when (relationship_type) {
            RelationShipType.ONE_TO_ONE -> {
                singularUri = "${getUriPrefix()}/$resource"
                table.addRow(BusinessAbilityTable.Row("POST", singularUri, "创建$name", serviceName, roleName))
            }
            RelationShipType.ONE_TO_N -> {
                val pluralUri = "${getUriPrefix()}/${resource.pluralize()}"
                singularUri = "$pluralUri/{${resource[0]}id}"
                table.addRow(BusinessAbilityTable.Row("POST", pluralUri, "创建$name", serviceName, roleName))
                table.addRow(BusinessAbilityTable.Row("GET", pluralUri, "查看${name}列表", serviceName))
            }
            else -> singularUri = "${getUriPrefix()}/$resource"
        }
        table.addRow(BusinessAbilityTable.Row("GET", singularUri, "查看$name", serviceName, roleName))
        table.addRow(BusinessAbilityTable.Row("PUT", singularUri, "更改$name", serviceName, roleName))
        table.addRow(BusinessAbilityTable.Row("DELETE", singularUri, "取消$name", serviceName, roleName))
    }

    override fun key_timestamps(vararg timestamps: String) = timestamps.let { this.timestamps = it }

    override fun key_data(vararg data: String) = data.let { this.data = it }

    override fun toString(): String {
        return """
            |${note ?: ""}
            |class $name <<$type>> ${if (isRole) YELLOW else PINK}{
            |   ${generateRole(role)?:""} ${timestamps?.joinToString() ?: ""}
            |   ${if (timestamps != null && data != null) ".." else ""}
            |   ${data?.joinToString() ?: ""}
            }
        """.trimIndent()
    }
}