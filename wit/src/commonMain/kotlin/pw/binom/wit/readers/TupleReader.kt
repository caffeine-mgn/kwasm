package pw.binom.wit.readers

import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.visitors.TupleVisitor

object TupleReader {
    fun read(tokenizer: BufferedTokenizer, visitor: TupleVisitor) {
        visitor.start()
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.LESS)
        while (true) {
            TypeReader.read(tokenizer, visitor.element())
            tokenizer.nextNotSpaceOrEof()
            when (tokenizer.type) {
                TokenType.COMMA -> continue
                TokenType.GREATER -> break
                else -> TODO()
            }
        }
        visitor.end()
    }
}