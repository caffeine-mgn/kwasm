package pw.binom.wit.parser

import kotlin.test.*

class CharBufferTest {

    @Test
    fun sizeTest() {
        val cb = CharBuffer(4)
        try {
            cb.get()
            fail()
        } catch (e: CharBuffer.CharBufferEmptyException) {
            // ok
        }
        assertTrue(cb.isEmpty)
        assertFalse(cb.isNotEmpty)
        assertFalse(cb.isFull)
        repeat(3) {
            cb.push('1')
            assertFalse(cb.isEmpty)
            assertTrue(cb.isNotEmpty)
            assertFalse(cb.isFull)
        }
        cb.push('1')
        assertFalse(cb.isEmpty)
        assertTrue(cb.isNotEmpty)
        assertTrue(cb.isFull)
        try {
            cb.push('1')
            fail()
        } catch (e: CharBuffer.CharBufferFullException) {
            // ok
        }
    }

    @Test
    fun contentTest() {
        val content = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
        val cb = CharBuffer(content.size)
        content.forEach {
            cb.push(it)
        }
        content.reversed().forEachIndexed { index, c ->
            assertEquals(c, cb.get())
        }
    }
}