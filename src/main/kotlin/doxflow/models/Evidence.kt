package doxflow.models

import common.DSL
import doxflow.dsl.context

abstract class Evidence<T>(
    val name: String,
    val context: context,
    private val generics: String? = null,
    private val note: String? = null
) : DSL<T> {
    init {
        context.allClasses.add(name)
    }

    var isRole: Boolean = false
    var timestamps: Array<out String>? = null
    abstract val type: String
    private var data: Array<out String>? = null

    fun key_timestamps(vararg timestamps: String) {
        this.timestamps = timestamps
    }

    fun key_data(vararg data: String) {
        this.data = data
    }

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