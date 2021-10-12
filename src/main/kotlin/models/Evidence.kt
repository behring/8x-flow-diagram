package models

import dsl.Flow
import dsl.context

abstract class Evidence<T>(
    val name: String,
    val context: context,
    private val generics: String? = null,
    val note: String? = null
) : Flow<T> {
    init {
        context.allClasses.add(name)
    }

    abstract val type: String
    var timestamps: Array<out String>? = null
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
            class $name${generics ?: ""}<<$type>> {
                ${if (timestamps != null) timestamps.contentToString() else ""}
                ${if (timestamps != null && data != null) ".." else ""}
                ${if (data != null) data.contentToString() else ""}
            }
        """.trimIndent()
    }
}