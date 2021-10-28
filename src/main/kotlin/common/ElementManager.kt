package common

interface ParentContainer {
    fun addElement(element: Element) {}

    fun StringBuilder.addElements(
        elements: List<Element>,
        container: Element
    ) = apply {
        appendLine("${container.type} ${container.displayName} {")
        generateElementsStr(container, elements, this)
        appendLine("}")
    }

    fun generateElementsStr(container: Element, elements: List<Element>, elementsStr: StringBuilder) {
        val mutableElement = elements.toMutableList()
        if (mutableElement.isEmpty()) return
        do {
            val element = mutableElement.removeFirst()
            elementsStr.append("${element.type} ${element.displayName} ${element.backgroundColor ?: container.backgroundColor ?: ""}")
            if (element.childElements.isNotEmpty()) {
                elementsStr.appendLine("{")
                generateElementsStr(element, element.childElements, elementsStr)
                elementsStr.appendLine("}")
            } else {
                elementsStr.appendLine()
            }
        } while (mutableElement.isNotEmpty())
    }

}

/**
 * 表示UML中的一个任意元素
 * type <size:14><b>name</b></size> color
 * package A #yellow
 * */
data class Element(
    var displayName: String,
    var type: String,
    var backgroundColor: String? = null,
    var name: String? = "<size:16><b>$displayName"
) {
    var stereoType: String? = null
    val childElements: MutableList<Element> = mutableListOf()

    override fun toString(): String {
        return "$type \"$name\" as $displayName ${stereoType ?: ""} $backgroundColor"
    }
}

interface Interactions {
    fun generateInteractions(element: Element, elementInteractions: List<Pair<String, String>>): String = buildString {
        elementInteractions.forEach {
            // class element指定关系时，不能使用[]括号
            append("${element.displayName}-->${it.first}")
            appendLine(with(it.second) {
                return@with if (!isNullOrBlank()) ":${it.second}" else ""
            })
        }
    }

}