package architecture.models

data class Element(val name: String, val type: String, val color: String? = null) {
    val childElements: MutableList<Element> = mutableListOf()
}