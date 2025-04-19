package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.ResourceVisitor

object ResourceReader {
    fun read(tokenizer: BufferedTokenizer, visitor: ResourceVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        visitor.start(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        when (tokenizer.type) {
            TokenType.TERMINATOR -> {
                // Do nothing
            }

            TokenType.OPEN_BRACE -> {
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

                        TokenType.OPERATOR -> {
                            tokenizer.nextNotSpaceOrEof()
                            readFunc(tokenizer, visitor)
                        }

                        TokenType.LINE_COMMENT -> visitor.lineComment(tokenizer.text.substring(2))
                        TokenType.AT -> AnnotationReader.read(tokenizer, visitor.annotation())

                        else -> TODO()
                    }
                }
            }

            else -> TODO()
        }

        visitor.end()
    }

    private fun readFunc(tokenizer: BufferedTokenizer, visitor: ResourceVisitor) {
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
                FuncReader.read(tokenizer, visitor.funcStatic(name))
            }

            else -> TODO()
        }
    }
}