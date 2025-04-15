package pw.binom.wit.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class CursorTest {
    @Test
    fun testPush() {
        val c = Cursor(3)
        assertEquals(0, c.push())
        assertEquals(1, c.push())
        assertEquals(2, c.push())
        try {
            c.push()
            fail()
        } catch (e: IllegalStateException) {
            // Do nothing
        }
    }

    @Test
    fun testBack() {
        val c = Cursor(3)
        assertEquals(0, c.push())
        assertEquals(0, c.next())
        c.back()
        try {
            c.back()
            fail()
        } catch (e: IllegalStateException) {
            // Do nothing
        }
    }
}