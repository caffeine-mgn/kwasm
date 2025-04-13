package pw.binom.wit.writers

import kotlin.test.Test
import kotlin.test.assertEquals

class EnumWriterTest {
    @Test
    fun test() {
        val NAME = "test"
        val elements = listOf("a", "b", "c")
        val sb = StringBuilder()
        val writer = SimpleTextWriter(sb)
        EnumWriter(writer).apply {
            start(NAME)
            elements.forEach {
                element(it)
            }
            end()
        }
        assertEquals("enum $NAME { ${elements.joinToString(", ")} }", sb.toString())
    }
}