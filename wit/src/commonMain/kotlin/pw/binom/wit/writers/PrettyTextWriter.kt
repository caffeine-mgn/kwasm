package pw.binom.wit.writers

class PrettyTextWriter(
    val appendable: Appendable,
    val tabSize: Int = 4,
    val tabChar: Char = ' ',
) : TextWriter {
    private var lineStart = true
    private var level = 0
    var line = 0
        private set
    var column = 0
        private set

    private fun insetPad() {
        if (lineStart) {
            repeat(level) {
                repeat(tabSize) {
                    appendable.append(tabChar)
                    column++
                }
            }
            lineStart = false
        }
    }

    override fun append(value: String): PrettyTextWriter {
        insetPad()
        appendable.append(value)
        column += value.length
        return this
    }

    override fun appendLine(): PrettyTextWriter {
        insetPad()
        appendable.append("\n")
        lineStart = true
        line++
        column = 0
        return this
    }

    override fun levelInc(): PrettyTextWriter {
        level++
        return this
    }

    override fun levelDec(): PrettyTextWriter {
        require(level > 0)
        level--
        return this
    }
}