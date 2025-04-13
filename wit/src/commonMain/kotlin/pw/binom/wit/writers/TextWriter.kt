package pw.binom.wit.writers

interface TextWriter {
    fun append(value: String): TextWriter
    fun appendLine(): TextWriter
    fun levelInc(): TextWriter
    fun levelDec(): TextWriter
}