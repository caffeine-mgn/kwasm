package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.visitors.ResourceVisitor

object ResourceReader {
    fun read(tokenizer: BasicTokenizer, visitor: ResourceVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        visitor.start(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.OPEN_BRACE)
        while (true) {
            tokenizer.nextNotSpaceOrEof()
            when (tokenizer.type) {
                TokenType.CLOSE_BRACE -> break
                TokenType.WORD -> {
                    when (tokenizer.text) {
                        "constructor" -> ConstructorReader.read(tokenizer, visitor.init())
                        else -> readFunc(tokenizer, visitor)
                    }
                }

                else -> TODO()
            }
        }
        visitor.end()
    }

    private fun readFunc(tokenizer: BasicTokenizer, visitor: ResourceVisitor) {
        val name = tokenizer.text
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.COLON)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        when (tokenizer.text) {
            "func" -> FuncReader.read(tokenizer, visitor.func(name))
            "static" -> {
                tokenizer.nextNotSpaceOrEof()
                tokenizer.assertType(TokenType.WORD)
                check(tokenizer.text == "func")
                FuncReader.read(tokenizer, visitor.func(name))
            }

            else -> TODO()
        }
    }
}