package doxflow.models.diagram

const val ONE_TO_N = """ "1" -- "N" """
const val ONE_TO_ONE = """ "1" -- "1" """
const val N_TO_N = """ "N" -- "N" """
const val ASSOCIATE = """ -- """
const val PLAY_TO = """ ..> """

interface Association {
    var associationType: AssociationType
}

enum class AssociationType {
    ONE_TO_ONE,
    ONE_TO_N,
    N_TO_N,
    NONE
}