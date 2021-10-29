package doxflow.models.ability

import common.DSL
import doxflow.models.diagram.Relationship

interface BusinessAbility<T> : DSL<T> {
    var resource: String
}

class BusinessAbilityCreator(private val serviceName: String) {
    private val table: BusinessAbilityTable = BusinessAbilityTable()

   companion object {
       fun getUri(
           resource: String,
           relationship: String,
           uriPrefix: String
       ): String = when (relationship) {
           Relationship.ONE_TO_ONE -> "${uriPrefix}/$resource"
           Relationship.ONE_TO_N -> "${uriPrefix}/${pluralize(resource)}/{${resource[0]}id}"
           else -> "${uriPrefix}/$resource"
       }

       fun pluralize(word: String): String {
           val vowel = arrayOf('a', 'e', 'i', 'o', 'u')
           val specificPlurals = mapOf("person" to "people")
           if (word.isBlank()) return word
           return when {
               specificPlurals.containsKey(word) -> specificPlurals[word].toString()
               word.endsWith('s') || word.endsWith('x') || word.endsWith("ch") || word.endsWith("sh") -> word + "es"
               !vowel.contains(word[word.length - 2]) && word.last() == 'y' -> word.substring(0, word.length - 1) + "ies"
               else -> word + 's'
           }
       }
   }

    fun appendBusinessAbility(
        resource: String,
        relationship: String,
        uriPrefix: String,
        displayName: String,
        roleName: String
    ) {
        if (resource.isBlank()) return
        val singularUri: String
        when (relationship) {
            Relationship.ONE_TO_ONE -> {
                singularUri = "$uriPrefix/$resource"
                table.addRow(BusinessAbilityTable.Row("POST", singularUri, "创建${displayName}", serviceName, roleName))
            }
            Relationship.ONE_TO_N -> {
                val pluralUri = "$uriPrefix/${pluralize(resource)}"
                singularUri = "$pluralUri/{${resource[0]}id}"
                table.addRow(BusinessAbilityTable.Row("POST", pluralUri, "创建${displayName}", serviceName, roleName))
                table.addRow(BusinessAbilityTable.Row("GET", pluralUri, "查看${displayName}列表", serviceName))
            }
            else -> singularUri = "$uriPrefix/$resource"
        }
        table.addRow(BusinessAbilityTable.Row("GET", singularUri, "查看${displayName}", serviceName, roleName))
        table.addRow(BusinessAbilityTable.Row("PUT", singularUri, "更改${displayName}", serviceName, roleName))
        table.addRow(BusinessAbilityTable.Row("DELETE", singularUri, "取消${displayName}", serviceName, roleName))
    }

    fun create(): String = table.toString()
}