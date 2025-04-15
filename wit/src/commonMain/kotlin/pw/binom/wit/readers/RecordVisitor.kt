package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.visitors.RecordVisitor
import pw.binom.wit.visitors.TypeVisitor

object RecordVisitor {
    fun read(tokenizer: BufferedTokenizer, visitor: RecordVisitor) {
        tokenizer.nextNotSpaceOrEof()
        visitor.start(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.OPEN_BRACE)
        while (true) {
            tokenizer.nextNotSpaceOrEof()
            when (tokenizer.type) {
                TokenType.CLOSE_BRACE -> break
                TokenType.COMMA -> continue
                TokenType.WORD -> readType(tokenizer, visitor.field(tokenizer.text))
                else -> TODO()
            }
        }
        visitor.end()
    }

    private fun readType(tokenizer: BufferedTokenizer, visitor: TypeVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.COLON)
        TypeReader.read(tokenizer, visitor)
    }
}