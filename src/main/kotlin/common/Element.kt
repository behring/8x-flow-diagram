package common

import doxflow.diagram_8x_flow.Companion.currentLegend
import doxflow.diagram_8x_flow.LegendType.*
import doxflow.models.diagram.Relationship


/**
 * 表示UML中的一个任意元素
 * type <size:14><b>name</b></size> color
 * package A #yellow
 * */
data class Element(
    var displayName: String,
    var type: String = "rectangle",
    var backgroundColor: String? = null,
) {
    var name: String? = "<size:14><b>$displayName"
    var stereoType: String? = null
    var relativeElements: MutableList<RelationshipWrapper> = mutableListOf()

    fun relate(relativeElementName: String, relationship: String, command: String? = null) {
        relate(Element(relativeElementName), relationship, command)
    }

    fun relate(relativeElement: Element, relationship: String, command: String? = null) =
        relativeElements.add(RelationshipWrapper(relativeElement, relationship, command))

    fun generateRelationships(): String = buildString {
        relativeElements.forEach {
            append("${displayName}${it.relationship}${it.relativeElement.displayName}")
            appendLine(with(it.command) { return@with if (!isNullOrBlank()) ":${it.command}" else "" })
        }
        relativeElements.clear()
    }

    override fun toString(): String =
        "$type \"$name\" as ${displayName.replace(" ","_")} ${if (stereoType != null && currentLegend == TacticalLegend) "<<$stereoType>>" else ""} ${backgroundColor ?: ""}"

    inner class RelationshipWrapper(val relativeElement: Element, var relationship: String, val command: String?) {
        init {
            relationship = if (currentLegend == StrategicLegend) Relationship.NONE else relationship
        }
    }

}