package doxflow.models.diagram

const val ONE_TO_N = """ "1" -- "N" """
const val ONE_TO_ONE = """ "1" -- "1" """
const val N_TO_N = """ "N" -- "N" """
const val RELATIONSHIP = """ -- """
const val PLAY_TO = """ ..> """

interface Relationship {
    var relationship_type: RelationShipType

    fun getRelationshipLine(type: RelationShipType): String = when (type) {
        RelationShipType.ONE_TO_ONE -> ONE_TO_ONE
        RelationShipType.ONE_TO_N -> ONE_TO_N
        RelationShipType.N_TO_N -> N_TO_N
        RelationShipType.NONE -> RELATIONSHIP
    }
}

enum class RelationShipType {
    ONE_TO_ONE,
    ONE_TO_N,
    N_TO_N,
    NONE
}
