package pw.binom.wit.writers

class SimpleTextWriter(val appendable: Appendable): TextWriter {
    override fun append(value: String): SimpleTextWriter {
        appendable.append(value)
        return this
    }

    override fun appendLine(): SimpleTextWriter {
        appendable.append(" ")
        return this
    }

    override fun levelInc(): SimpleTextWriter = this

    override fun levelDec(): SimpleTextWriter = this
}