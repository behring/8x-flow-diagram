package models

data class Role(val name: String, val type: Type) {
    var participant: Participant? = null

    enum class Type {
        PARTY,
        DOMAIN,
        `3RD_SYSTEM`,
        EVIDENCE
    }

    infix fun played(participant: Participant): Role = apply { this.participant = participant }

    override fun toString(): String {
        return """
            class $name <<${type.name.lowercase()}>> #orange
        """.trimIndent()
    }
}
