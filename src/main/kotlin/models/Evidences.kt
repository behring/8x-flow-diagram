package models

import dsl.Flow

abstract class Evidences<T>(val name: String) : Flow<T> {

    abstract fun key_timestamps(vararg timestamps: String)

    inline fun <reified T> getType(): String {
        return T::class.java.simpleName
    }

    fun key_data(vararg data: String) {}
}