package pw.binom.wit.parser

class CharBuffer(limit: Int, private val onPush: () -> Unit = {}) {
    class CharBufferFullException : IllegalStateException() {
        override val message: String
            get() = "CharBuffer is full"
    }

    class CharBufferEmptyException : IllegalStateException() {
        override val message: String
            get() = "CharBuffer is empty"
    }

    private val buffer = CharArray(limit)
    private var position = 0
    val isEmpty: Boolean
        get() = position == 0
    val isNotEmpty: Boolean
        get() = !isEmpty
    val isFull
        get() = position == buffer.size

    fun push(char: Char) {
        if (isFull) {
            throw CharBufferFullException()
        }
        buffer[position++] = char
        onPush()
    }

    fun get(): Char {
        if (isEmpty) {
            throw CharBufferEmptyException()
        }
        return buffer[--position]
    }
}