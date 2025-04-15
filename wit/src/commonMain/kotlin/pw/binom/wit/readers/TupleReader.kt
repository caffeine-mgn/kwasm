package pw.binom.wit.readers

import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.visitors.TupleVisitor

object TupleReader {
    fun read(tokenizer: BufferedTokenizer, visitor: TupleVisitor) {
        visitor.start()
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.LESS)
        TypeReader.read(tokenizer, visitor.first())
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.COMMA)
        TypeReader.read(tokenizer, visitor.second())
        tokenizer.nextNotSpaceOrEof()
        when (tokenizer.type) {
            TokenType.COMMA -> {
                TypeReader.read(tokenizer, visitor.third())
                tokenizer.nextNotSpaceOrEof()
                tokenizer.assertType(TokenType.GREATER)
            }

            TokenType.GREATER -> {
                // dp nothing
            }

            else -> TODO()
        }
        visitor.end()
    }
}