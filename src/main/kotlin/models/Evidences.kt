package models

abstract class Evidences(val name: String, val type: Type) {
    enum class Type {
        RFP,
        PROPOSAL,
        CONTRACT,
        REQUEST,
        CONFIRMATION,
        EVIDENCE
    }

    abstract fun key_timestamps(vararg timestamps: String)

    fun key_data(vararg data: String) {}
}