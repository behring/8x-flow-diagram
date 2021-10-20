package common

interface KeyInfo<T> : DSL<T> {
    fun key_timestamps(vararg timestamps: String)

    fun key_data(vararg data: String)
}