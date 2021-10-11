package models

import dsl.Flow

abstract class Evidences<T>(val name: String) : Flow<T> {
    lateinit var timestamps: Array<out String>
    abstract val type: String
    private var data: Array<out String>? = null

    abstract fun key_timestamps(vararg timestamps: String)

    fun key_data(vararg data: String) {
        this.data = data
    }

    override fun toString(): String {
        return """        
            class "<$type>\n$name" as $name  {
                ${timestamps.contentToString()}
                ${if (data != null) "..\n ${data.contentToString()}" else ""}
            }
        """.trimIndent()
    }
}