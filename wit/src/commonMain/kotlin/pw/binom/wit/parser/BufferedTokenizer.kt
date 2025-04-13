package pw.binom.wit.parser

class BufferedTokenizer(val source: Tokenizer) : Tokenizer {

    private data class Token(
        val type: TokenType,
        val text: String,
        val start: Int,
        val end: Int,
    )

    private var backToken: Token? = null
    private var nextToken: Token? = null

    override val type: TokenType
        get() = nextToken?.type ?: source.type
    override val text: String
        get() = nextToken?.text ?: source.text
    override val start: Int
        get() = nextToken?.start ?: source.start
    override val end: Int
        get() = nextToken?.end ?: source.end

    override fun next(): Boolean {
        if (backToken != null) {
            nextToken = backToken
            backToken = null
            return true
        }
        if (nextToken != null) {
            nextToken = null
        }
        return source.next()
    }

    fun pushBackCurrentToken() {
        check(backToken == null) { "Can't push back current token" }
        backToken = Token(
            type = source.type,
            text = source.text,
            start = source.start,
            end = source.end,
        )
    }
}