package doxflow.models.diagram

import doxflow.diagram_8x_flow.LegendType.*
import doxflow.diagram_8x_flow.currentLegend

interface Relationship {
    companion object {
        private const val lineColor = "[#000000]"
        const val ONE_TO_N = """ "1" -$lineColor- "N" """
        const val ONE_TO_ONE = """ "1" -$lineColor- "1" """
        const val N_TO_N = """ "N" -$lineColor- "N" """
        const val NONE = """ -$lineColor- """
        const val PLAY_TO = """ .$lineColor.|> """
        const val ASSOCIATE = """ -$lineColor-> """
        var DEFAULT = if (currentLegend == StrategicLegend)  NONE else ONE_TO_ONE
    }
}
