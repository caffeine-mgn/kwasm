package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.visitors.MultipleReturnVisitor

object MultipleReturnReader {
    fun read(tokenizer: BasicTokenizer, visitor: MultipleReturnVisitor) {
        visitor.start()
        while (true) {
            tokenizer.nextNotSpaceOrEof()
            when (tokenizer.type) {
                TokenType.WORD -> {
                    val argName = tokenizer.text
                    tokenizer.nextNotSpaceOrEof()
                    tokenizer.assertType(TokenType.COLON)
                    TypeReader.read(tokenizer, visitor.arg(argName))
                    tokenizer.nextNotSpaceOrEof()
                    when (tokenizer.type) {
                        TokenType.COMMA -> continue
                        TokenType.CLOSE_PAREN -> break
                        else -> TODO()
                    }
                }

                TokenType.CLOSE_PAREN -> break
                else -> TODO()
            }

            TODO()
        }
        visitor.end()
    }
}