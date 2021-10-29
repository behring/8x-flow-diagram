package common

/**
 * 表示UML中的一个任意元素
 * type <size:14><b>name</b></size> color
 * package A #yellow
 * */
data class Element(
    var displayName: String,
    var type: String,
    var backgroundColor: String? = null,
) {
    var name: String? = "<size:16><b>$displayName"
    var stereoType: String? = null
    var relativeElement: Element? = null

    fun relate(relativeElement: Element) = relativeElement.let { this.relativeElement = it }

    override fun toString(): String =
        "$type \"$name\" as $displayName ${if (stereoType != null) "<<$stereoType>>" else ""} ${backgroundColor ?: ""}"

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