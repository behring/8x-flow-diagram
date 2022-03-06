package doxflow.models.diagram

interface Relationship {
    companion object {
        private const val lineColor = "[#000000]"
        const val NONE = """ -$lineColor- """
        const val ONE_TO_ONE = """ "1" -$lineColor- "1" """
        const val ONE_TO_N = """ "1" -$lineColor- "N" """
        const val N_TO_ONE = """ "N" -$lineColor- "1" """
        const val ONE_TO_ONE_OVER_CONTEXT = """ "1" .$lineColor. "1" """
        const val ONE_TO_N_OVER_CONTEXT = """ "1" .$lineColor. "N" """
        const val N_TO_ONE_OVER_CONTEXT = """ "N" .$lineColor. "1" """
        const val PLAY_TO = """ .$lineColor.> """
        const val DEFAULT = ONE_TO_ONE
    }
}
