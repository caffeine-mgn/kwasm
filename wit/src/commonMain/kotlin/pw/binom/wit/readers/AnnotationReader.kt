package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.AnnotationVisitor

fun Tokenizer.readUntil(predicate: (Tokenizer) -> Boolean): String {
    val sb = StringBuilder()
    while (true) {
        nextOrEof()
        if (!predicate(this)) {
            break
        }
        sb.append(text)
    }
    return sb.toString()
}

object AnnotationReader {
    fun read(tokenizer: Tokenizer, visitor: AnnotationVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        visitor.start(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.OPEN_PAREN)
        LOOP@ while (true) {
            tokenizer.nextNotSpaceOrEof()
            tokenizer.assertType(TokenType.WORD)
            val name = tokenizer.text
            tokenizer.nextNotSpaceOrEof()
            tokenizer.assertType(TokenType.ASSIGN)
            val sb = StringBuilder()
            tokenizer.nextNotSpaceOrEof()
            check(tokenizer.type != TokenType.COMMA && tokenizer.type != TokenType.CLOSE_PAREN)
            sb.append(tokenizer.text)
            sb.append(tokenizer.readUntil { t ->
                t.type != TokenType.COMMA && t.type != TokenType.CLOSE_PAREN
            })
            when (tokenizer.type) {
                TokenType.COMMA -> {
                    visitor.field(name = name, value = sb.toString())
                    break
                }

                TokenType.CLOSE_PAREN -> {
                    visitor.field(name = name, value = sb.toString())
                    break@LOOP
                }

                else -> TODO()
            }
        }
        visitor.end()
    }
}