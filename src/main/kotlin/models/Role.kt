package models

data class Role(val name: String, val type: Type) {
    enum class Type {
        PARTY,
        DOMAIN,
        THIRD_SYSTEM,
        EVIDENCE
    }

    infix fun party(participant: Participant): Role {
        return this
    }

    override fun toString(): String {
        return """
            class $name <<${type.name.lowercase()}>> #orange
        """.trimIndent()
    }
}
