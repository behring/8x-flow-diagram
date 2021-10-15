package architecture.models

/**
 * 表示UML中的一个任意元素
 * type name color
 * package A #yellow
 * */
data class Element(val name: String, val type: String, val color: String? = null) {
    val childElements: MutableList<Element> = mutableListOf()
}