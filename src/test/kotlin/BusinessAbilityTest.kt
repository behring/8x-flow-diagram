
import doxflow.models.ability.BusinessAbilityCreator
import org.junit.Test
import kotlin.test.assertEquals

internal class BusinessAbilityTest {
    @Test
    fun should_can_pluralize_word() {
        var actual = BusinessAbilityCreator.pluralize("class")
        assertEquals("classes", actual)
        actual = BusinessAbilityCreator.pluralize("city")
        assertEquals("cities", actual)
        actual = BusinessAbilityCreator.pluralize("book")
        assertEquals("books", actual)
        actual = BusinessAbilityCreator.pluralize("person")
        assertEquals("people", actual)
    }
}