package doxflow.models.ability

class BusinessAbilityTable {
    private var table: MutableList<Row> = mutableListOf()

    init {
        addTableHeader()
    }

    private fun addTableHeader() {
        table.add(Row("HTTP方法", "URI", "业务能力", "业务能力服务", "角色"))
        table.add(Row("---", "---", "---", "---", "---"))
    }

    fun addRow(row: Row) {
        table.add(row)
    }

    override fun toString(): String = buildString {
        table.forEach { appendLine(it.toString()) }
    }

    //角色，HTTP方法，URI，业务能力，业务能力服务
    data class Row(
        val method: String,
        val uri: String,
        val ability: String = "",
        val abilityInService: String = "",
        val role: String = ""
    ) {
        override fun toString(): String = "| $role | $method | $uri | $ability | $abilityInService |"
    }
}