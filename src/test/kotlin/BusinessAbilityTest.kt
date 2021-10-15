import doxflow.BusinessAbility
import org.junit.Test
import kotlin.test.assertEquals

internal class BusinessAbilityTest {
    @Test
    fun should_can_pluralize_word() {
        var actual = contract("class").pluralize()
        assertEquals("classes", actual)
        actual = contract("city").pluralize()
        assertEquals("cities", actual)
        actual = contract("book").pluralize()
        assertEquals("books", actual)
        actual = contract("person").pluralize()
        assertEquals("people", actual)
    }

    class contract(override var resource: String?) : BusinessAbility<contract> {
        override fun invoke(function: contract.() -> Unit): contract = this
        fun pluralize(): String? {
            return resource?.pluralize()
        }
    }
}