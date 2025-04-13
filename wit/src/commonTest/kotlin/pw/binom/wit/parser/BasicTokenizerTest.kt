package pw.binom.wit.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BasicTokenizerTest {
    class StringTokenizer(val str: String) : BasicTokenizer() {
        var cursor = 0
            private set

        override fun nextChar(): Char {
            if (cursor >= str.length) {
                throw Tokenizer.EOFException()
            }
            return str[cursor++]
        }
    }

    fun BasicTokenizer.nextText(): String {
        assertTrue(next())
        return text
    }

    class T(val t: String, val start: Int, val len: Int, val type: TokenType)

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun parse() {
        val content = """
    //test
    
    interface printer {
        print: func(text: string, arg:u32);
    }
    /* comment */
""".trimIndent()
        printString(content)
        val t = StringTokenizer(content)
        val chars = listOf(
            T("//test", start = 0, len = 6, type = TokenType.LINE_COMMENT),
            T("\n\n", start = 6, len = 2, type = TokenType.SPACE),
            T("interface", start = 8, len = 9, type = TokenType.WORD),
            T(" ", start = 17, len = 1, type = TokenType.SPACE),
            T("printer", start = 18, len = 7, type = TokenType.WORD),
            T(" ", start = 25, len = 1, type = TokenType.SPACE),
            T("{", start = 26, len = 1, type = TokenType.OPEN_BRACE),
            T("\n    ", start = 27, len = 5, type = TokenType.SPACE),
            T("print", start = 32, len = 5, type = TokenType.WORD),
            T(":", start = 37, len = 1, type = TokenType.COLON),
            T(" ", start = 38, len = 1, type = TokenType.SPACE),
            T("func", start = 39, len = 4, type = TokenType.WORD),
            T("(", start = 43, len = 1, type = TokenType.OPEN_PAREN),
            T("text", start = 44, len = 4, type = TokenType.WORD),
            T(":", start = 48, len = 1, type = TokenType.COLON),
            T(" ", start = 49, len = 1, type = TokenType.SPACE),
            T("string", start = 50, len = 6, type = TokenType.WORD),
            T(",", start = 56, len = 1, type = TokenType.COMMA),
            T(" ", start = 57, len = 1, type = TokenType.SPACE),
            T("arg", start = 58, len = 3, type = TokenType.WORD),
            T(":", start = 61, len = 1, type = TokenType.COLON),
            T("u32", start = 62, len = 3, type = TokenType.WORD),
            T(")", start = 65, len = 1, type = TokenType.CLOSE_PAREN),
            T(";", start = 66, len = 1, type = TokenType.TERMINATOR),
            T("\n", start = 67, len = 1, type = TokenType.SPACE),
            T("}", start = 68, len = 1, type = TokenType.CLOSE_BRACE),
            T("\n", start = 69, len = 1, type = TokenType.SPACE),
            T("/*", start = 70, len = 2, type = TokenType.MULTI_LINE_COMMENT_START),
            T(" ", start = 72, len = 1, type = TokenType.SPACE),
            T("comment", start = 73, len = 7, type = TokenType.WORD),
            T(" ", start = 80, len = 1, type = TokenType.SPACE),
            T("*/", start = 81, len = 2, type = TokenType.MULTI_LINE_COMMENT_END),
        )
        chars.forEachIndexed { index, it ->
            assertEquals(it.t, t.nextText(), "Error on lex #$index \"${it.t}\"")
            assertEquals(it.start, t.start, "Error on lex #$index \"${it.t}\"")
            assertEquals(it.len, t.length, "Error on lex #$index \"${it.t}\"")
            assertEquals(it.type, t.type, "Error on lex #$index \"${it.t}\"")
        }
        assertFalse(t.next())
    }
}

fun printString(text: String) {
    val size = 2
    val line = text.mapIndexed { index, it ->
        index.toString().padStart(size, '0')
    }.joinToString(" ")
    println(line)
    val line2 = text.map {
        when (it) {
            '\n' -> "\\n"
            '\t' -> "\\t"
            '\r' -> "\\r"
            else -> it.toString()
        }.padStart(size, ' ')
    }.joinToString(" ")
    println(line2)
}