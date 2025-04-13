package pw.binom.wit.readers

import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.visitors.WorldElementVisitor
import pw.binom.wit.visitors.WorldVisitor

object WorldElementReader {
    fun read(tokenizer: BasicTokenizer, visitor: WorldElementVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        val name = tokenizer.text
        tokenizer.nextNotSpaceOrEof()
        when (tokenizer.type) {
            TokenType.TERMINATOR -> visitor.id(name)
            TokenType.COLON -> {
                tokenizer.nextNotSpaceOrEof()
                tokenizer.assertType(TokenType.WORD)
                check(tokenizer.text == "func")
                FuncReader.read(tokenizer, visitor.func(name))
            }

            else -> TODO()
        }
    }
}