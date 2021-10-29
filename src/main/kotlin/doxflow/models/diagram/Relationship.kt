package doxflow.models.diagram

interface Relationship {
    companion object {
        const val ONE_TO_N = """ "1" -- "N" """
        const val ONE_TO_ONE = """ "1" -- "1" """
        const val N_TO_N = """ "N" -- "N" """
        const val NONE = """ -- """
        const val PLAY_TO = """ ..> """
    }
}
