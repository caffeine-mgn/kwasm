package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.visitors.TypeAliasVisitor

object TypeAliasReader {
    fun read(tokenizer: BufferedTokenizer, visitor: TypeAliasVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        visitor.start(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.ASSIGN)
        TypeReader.read(tokenizer, visitor.type())
        visitor.end()
    }
}