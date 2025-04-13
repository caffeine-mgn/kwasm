package pw.binom.wit.parser

interface Tokenizer {

    class EOFException : Exception()

    val type: TokenType
    val text: String
    val start: Int
    val end: Int
    val length: Int
        get() = (end - start) + 1
    fun next(): Boolean

    fun assertType(type: TokenType) {
        if (this.type != type) {
            throw IllegalStateException("Expected type $type but found ${this.type}. Text: $text")
        }
    }

    fun nextOrEof() {
        if (!next()) {
            throw EOFException()
        }
    }

    fun nextNotSpaceOrEof() {
        if (!nextNotSpace()) {
            throw EOFException()
        }
    }

    fun nextNotSpace(): Boolean {
        do {
            if (!next()) {
                return false
            }
            if (type != TokenType.SPACE) {
                return true
            }
        } while (true)
    }
}