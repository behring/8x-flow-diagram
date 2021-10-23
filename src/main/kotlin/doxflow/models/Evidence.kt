package doxflow.models

import common.ChildElement
import doxflow.models.BusinessAbility
import common.KeyInfo
import doxflow.dsl.context

abstract class Evidence<T>(
    val name: String,
    val context: context,
    override var resource: String? = null,
    private val generics: String? = null,
    private val note: String? = null
) : ChildElement(name, "class", context), BusinessAbility<T>, KeyInfo<T> {
    var isRole: Boolean = false
    var timestamps: Array<out String>? = null
    private var data: Array<out String>? = null

    /**
     * Evidence的类型：包括rfp，proposal，contract，request, confirmation
     * */
    abstract val type: String

    override fun key_timestamps(vararg timestamps: String) = timestamps.let { this.timestamps = it }

    override fun key_data(vararg data: String) = data.let { this.data = it }

    override fun toString(): String {
        return """
            ${note ?: ""}
            class $name${generics ?: ""}<<$type>> ${if (isRole) "#Orange" else "#HotPink"}{
                ${if (timestamps != null) timestamps.contentToString() else ""}
                ${if (timestamps != null && data != null) ".." else ""}
                ${if (data != null) data.contentToString() else ""}
            }
        """.trimIndent()
    }
}