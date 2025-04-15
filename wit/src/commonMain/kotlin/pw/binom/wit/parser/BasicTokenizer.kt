package pw.binom.wit.parser

abstract class BasicTokenizer : Tokenizer {


    @Throws(Tokenizer.EOFException::class)
    protected abstract fun nextChar(): Char
    private var internalType = TokenType.WORD
    private var internalText = ""
    private var internalStart = 0
    private var internalEnd = 0
    override val type: TokenType
        get() = internalType
    private var position = -1
    override val start: Int
        get() = internalStart
    override val end: Int
        get() = internalEnd
    override val text: String
        get() = internalText
    private var eof = false
    private val buffer = CharBuffer(4) {
        position--
    }

    private fun readChar(): Char {
        val resultChar = if (buffer.isEmpty) {
            try {
                nextChar()
            } catch (e: Tokenizer.EOFException) {
                eof = true
                throw e
            }
        } else {
            buffer.get()
        }
        position++
        return resultChar
    }

    override fun next(): Boolean {
        val r = next2()
        return r
    }

    private fun next2(): Boolean {
        if (eof) {
            return false
        }
        val char = try {
            readChar()
        } catch (e: Tokenizer.EOFException) {
            return false
        }
        if (char == '/' && readLineComment()) {
            return true
        }
        if (char == '/' && readMultilineCommentStart()) {
            return true
        }
        if (char == '*' && readMultilineCommentEnd()) {
            return true
        }
        if (char == '-' && readResult()) {
            return true
        }
        when {
            char.isDigit() -> readDigital(char)
//            char == '/' -> readLineComment()
            char.isSpace() -> readSpace(char)
            char.isLetter() -> readWord(char)
            char == '{' -> {
                internalStart = position
                internalEnd = position
                internalText = "{"
                internalType = TokenType.OPEN_BRACE
            }

            char == '%' -> {
                internalStart = position
                internalEnd = position
                internalText = "%"
                internalType = TokenType.OPERATOR
            }
            char == '_' -> {
                internalStart = position
                internalEnd = position
                internalText = "_"
                internalType = TokenType.UNDERSCORE
            }

            char == '<' -> {
                internalStart = position
                internalEnd = position
                internalText = "<"
                internalType = TokenType.LESS
            }

            char == '/' -> {
                internalStart = position
                internalEnd = position
                internalText = "/"
                internalType = TokenType.DIV
            }

            char == '>' -> {
                internalStart = position
                internalEnd = position
                internalText = ">"
                internalType = TokenType.GREATER
            }

            char == '}' -> {
                internalStart = position
                internalEnd = position
                internalText = "}"
                internalType = TokenType.CLOSE_BRACE
            }

            char == '=' -> {
                internalStart = position
                internalEnd = position
                internalText = "="
                internalType = TokenType.ASSIGN
            }

            char == '@' -> {
                internalStart = position
                internalEnd = position
                internalText = "@"
                internalType = TokenType.AT
            }

            char == ':' -> {
                internalStart = position
                internalEnd = position
                internalText = ":"
                internalType = TokenType.COLON
            }

            char == '(' -> {
                internalStart = position
                internalEnd = position
                internalText = "("
                internalType = TokenType.OPEN_PAREN
            }

            char == ')' -> {
                internalStart = position
                internalEnd = position
                internalText = ")"
                internalType = TokenType.CLOSE_PAREN
            }

            char == ',' -> {
                internalStart = position
                internalEnd = position
                internalText = ","
                internalType = TokenType.COMMA
            }

            char == '.' -> {
                internalStart = position
                internalEnd = position
                internalText = "."
                internalType = TokenType.DOT
            }

            char == ';' -> {
                internalStart = position
                internalEnd = position
                internalText = ";"
                internalType = TokenType.TERMINATOR
            }

            else -> TODO("Unsupported char \"$char\"")
        }
        return true
    }

    private fun readWord(char: Char) {
        val sb = StringBuilder()
        sb.append(char)
        internalStart = position
        while (true) {
            val e = readChar()
            if (e.isDigit() || e.isLetter() || e == '-') {
                sb.append(e)
                continue
            }
            buffer.push(e)
            break
        }
        internalText = sb.toString()
        internalEnd = position
        internalType = TokenType.WORD
    }

    private fun readSpace(char: Char) {
        internalStart = position
        buffer.push(char)
        val sb = StringBuilder()
        while (true) {
            val e = try {
                readChar()
            } catch (e: Tokenizer.EOFException) {
                internalText = sb.toString()
                break
            }
            if (!e.isSpace()) {
                buffer.push(e)
                internalText = sb.toString()
                break
            }
            sb.append(e)
            internalEnd = position
            internalType = TokenType.SPACE
        }
    }

    private fun readMultilineCommentEnd(): Boolean {
        val c = readChar()
        if (c != '/') {
            buffer.push(c)
            return false
        }
        internalStart = position - 1
        internalText = "*/"
        internalEnd = position
        internalType = TokenType.MULTI_LINE_COMMENT_END
        return true
    }

    private fun readMultilineCommentStart(): Boolean {
        val c = readChar()
        if (c != '*') {
            buffer.push(c)
            return false
        }
        internalStart = position - 1
        internalText = "/*"
        internalEnd = position
        internalType = TokenType.MULTI_LINE_COMMENT_START
        return true
    }

    private fun readLineComment(): Boolean {
        val c = readChar()
        if (c != '/') {
            buffer.push(c)
            return false
        }
        internalStart = position - 1
        val sb = StringBuilder("//")
        while (true) {
            val e = readChar()
            if (e == '\n') {
                buffer.push(e)
                break
            }
            sb.append(e)
        }
        internalText = sb.toString()
        internalEnd = position
        internalType = TokenType.LINE_COMMENT
        return true
    }

    private fun readDigital(char: Char): Boolean {
        val sb = StringBuilder()
        sb.append(char)
        internalStart = position - 1
        while (true) {
            val e = readChar()
            if (!e.isDigit()) {
                buffer.push(e)
                break
            }
            sb.append(e)
        }
        internalText = sb.toString()
        internalEnd = position
        internalType = TokenType.DIGITAL
        return true
    }


    private fun readResult(): Boolean {
        val c = readChar()
        if (c != '>') {
            buffer.push(c)
            return false
        }
        internalStart = position - 1
        internalEnd = position
        internalType = TokenType.RESULT
        internalText = "->"
        return true
    }


}

fun Char.isLetter() = this in 'A'..'Z' || this in 'a'..'z'
fun Char.isDigit() = this in '0'..'9'
fun Char.isSpace() = this == ' ' || this == '\n' || this == '\r' || this == '\t'