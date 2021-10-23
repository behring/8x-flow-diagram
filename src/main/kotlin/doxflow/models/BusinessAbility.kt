package doxflow.models

import common.DSL

interface BusinessAbility<T> : DSL<T> {
    var resource: String?

    fun String.pluralize(): String {
        val vowel = arrayOf('a', 'e', 'i', 'o', 'u')
        val specificPlurals = mapOf("person" to "people")
        if (this.isBlank()) return this
        return when {
            specificPlurals.containsKey(this) -> specificPlurals[this].toString()
            endsWith('s') || endsWith('x') || endsWith("ch") || endsWith("sh") -> this + "es"
            !vowel.contains(this[length - 2]) && last() == 'y' -> this.substring(0, length - 1) + "ies"
            else -> this + 's'
        }
    }
}