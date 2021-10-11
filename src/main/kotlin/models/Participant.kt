package models

data class Participant(val name: String, val type: Type) {
    enum class Type {
        PARTY,
        PLACE,
        THING
    }
}