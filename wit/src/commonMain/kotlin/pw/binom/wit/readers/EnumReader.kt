package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.EnumVisitor

object EnumReader {
    fun read(tokenizer: Tokenizer, visitor: EnumVisitor) {
        tokenizer.nextNotSpaceOrEof()
        visitor.start(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.OPEN_BRACE)
        while (true) {
            tokenizer.nextNotSpaceOrEof()
            when (tokenizer.type) {
                TokenType.WORD -> {
                    visitor.element(tokenizer.text)
                    tokenizer.nextNotSpaceOrEof()
                    when (tokenizer.type) {
                        TokenType.COMMA -> continue
                        TokenType.CLOSE_BRACE -> break
                        else -> TODO()
                    }
                }

                TokenType.CLOSE_BRACE -> break
                TokenType.LINE_COMMENT -> visitor.lineComment(tokenizer.text.substring(2))
                else -> TODO()
            }
        }
        visitor.end()
    }
}