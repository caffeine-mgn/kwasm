package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.ListVisitor
import pw.binom.wit.visitors.ResourceVisitor
import pw.binom.wit.visitors.ResultVisitor

object ResultReader {
    fun read(tokenizer: Tokenizer, visitor: ResultVisitor) {
        visitor.start()
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.LESS)
        TypeReader.read(tokenizer, visitor.first())
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.COMMA)
        TypeReader.read(tokenizer, visitor.second())
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.GREATER)
        visitor.end()
    }
}