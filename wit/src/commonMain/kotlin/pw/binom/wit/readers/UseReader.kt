package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.visitors.UseVisitor

object UseReader {
    fun read(tokenizer: BasicTokenizer, visitor: UseVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        visitor.start(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.DOT)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.OPEN_BRACE)
        tokenizer.nextNotSpaceOrEof()
        if (tokenizer.type != TokenType.CLOSE_BRACE) {
            while (true) {
                when (tokenizer.type) {
                    TokenType.WORD -> {
                        visitor.type(tokenizer.text)
                        tokenizer.nextNotSpaceOrEof()
                        when (tokenizer.type) {
                            TokenType.CLOSE_BRACE -> break
                            TokenType.COMMA -> {
                                tokenizer.nextNotSpaceOrEof()
                                continue
                            }
                            else -> TODO()
                        }
                    }
                    else -> TODO()
                }
                tokenizer.assertType(TokenType.CLOSE_BRACE)
            }
        }
        visitor.end()
    }
}