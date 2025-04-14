package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.ListVisitor
import pw.binom.wit.visitors.OptionVisitor

object OptionReader {
    fun read(tokenizer: Tokenizer, visitor: OptionVisitor) {
        visitor.start()
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.LESS)
        TypeReader.read(tokenizer, visitor.type())
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.GREATER)
        visitor.end()
    }
}