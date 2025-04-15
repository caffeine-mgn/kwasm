package pw.binom.wit.parser

import pw.binom.collections.LinkedList

class BufferedTokenizer(val size: Int, val source: Tokenizer) : Tokenizer {

    private val list = LinkedList<Token>()

    init {
        putToken()
    }

    private var current: LinkedList.Node<Token>? = list.lastNode

    private fun currentToken() = current?.value

    private fun putToken() {
        list.addLast(
            Token(
                type = source.type,
                text = source.text,
                start = source.start,
                end = source.end,
            )
        )
        if (list.size > size) {
            list.removeFirstOrNull()
        }
    }

    private data class Token(
        val type: TokenType,
        val text: String,
        val start: Int,
        val end: Int,
    )

    override val type: TokenType
        get() = currentToken()?.type ?: source.type
    override val text: String
        get() = currentToken()?.text ?: source.text
    override val start: Int
        get() = currentToken()?.start ?: source.start
    override val end: Int
        get() = currentToken()?.end ?: source.end

    private var eof = false

    override fun next(): Boolean {
        if (eof) {
            return false
        }
        val next = current?.next
        if (next != null) {
            current = next
            return true
        }
        if (!source.next()) {
            eof = true
            return false
        }
        putToken()
        current = list.lastNode
        return true
    }

    fun pushBackCurrentToken() {
        val current = current
        check(current != null)
        val prev = current.prev
        check(prev != null)
        this.current = prev
    }
}