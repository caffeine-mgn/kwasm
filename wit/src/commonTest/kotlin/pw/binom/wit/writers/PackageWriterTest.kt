package pw.binom.wit.writers

import kotlin.test.Test
import kotlin.test.assertEquals

class PackageWriterTest {

    companion object {
        const val moduleName = "binom"
        const val fieldName = "pipeline"
        const val version = "1.0.0"
    }

    @Test
    fun withoutVersion() {
        val sb = StringBuilder()
        val writer = SimpleTextWriter(sb)
        PackageWriter(writer).apply {
            start()
            moduleName(moduleName)
            fieldName(fieldName)
            end()
        }
        assertEquals("package binom:pipeline; ", sb.toString())
    }

    @Test
    fun withVersion() {
        val sb = StringBuilder()
        val writer = SimpleTextWriter(sb)
        PackageWriter(writer).apply {
            start()
            moduleName(moduleName)
            fieldName(fieldName)
            version(version)
            end()
        }
        assertEquals("package binom:pipeline@1.0.0; ", sb.toString())
    }
}