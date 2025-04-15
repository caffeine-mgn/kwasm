package pw.binom.wit.readers

import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.visitors.BorrowVisitor

object BorrowReader {
    fun read(tokenizer: BufferedTokenizer, visitor: BorrowVisitor) {
        visitor.start()
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.LESS)
        TypeReader.read(tokenizer, visitor.type())
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.GREATER)
        visitor.end()
    }
}