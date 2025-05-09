package pw.binom.wit.readers

import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.ListVisitor

object ListReader {
    fun read(tokenizer: BufferedTokenizer, visitor: ListVisitor) {
        visitor.start()
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.LESS)
        TypeReader.read(tokenizer, visitor.type())
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.GREATER)
        visitor.end()
    }
}