package models

import dsl.Flow

abstract class Evidences<T>(val name: String, val note: String? = null) : Flow<T> {
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
            class "<$type>\n$name" as $name  {
                ${if (timestamps != null) timestamps.contentToString() else ""}
                ${if (timestamps != null && data != null) ".." else ""}
                ${if (data != null) data.contentToString() else ""}
            }
        """.trimIndent()
    }
}