package common

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
    var name: String? = "<size:16><b>$displayName"
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
        "$type \"$name\" as $displayName ${if (stereoType != null) "<<$stereoType>>" else ""} ${backgroundColor ?: ""}"

    data class RelationshipWrapper(val relativeElement: Element, val relationship: String, val command: String?)

}