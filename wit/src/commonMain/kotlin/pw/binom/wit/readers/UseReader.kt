package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.UseVisitor

object UseReader {
    fun read(tokenizer: Tokenizer, visitor: UseVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        val name = tokenizer.text

        tokenizer.nextNotSpaceOrEof()

        when (tokenizer.type) {
            TokenType.DOT -> {
                visitor.start(name)
                tokenizer.nextNotSpaceOrEof()
                tokenizer.assertType(TokenType.OPEN_BRACE)
            }

            TokenType.COLON -> {
                tokenizer.nextNotSpaceOrEof()
                tokenizer.assertType(TokenType.WORD)
                val fieldName = tokenizer.text
                tokenizer.nextNotSpaceOrEof()
                tokenizer.assertType(TokenType.DIV)
                tokenizer.nextNotSpaceOrEof()
                tokenizer.assertType(TokenType.WORD)
                val interfaceName = tokenizer.text
                tokenizer.nextNotSpaceOrEof()
                when (tokenizer.type) {
                    TokenType.AT -> {
                        val version = tokenizer.readUntil { it.type != TokenType.OPEN_BRACE }
                            .trimEnd()
                            .removeSuffix(".")
                        visitor.start(
                            module = name,
                            name = fieldName,
                            interfaceName = interfaceName,
                            version = version,
                        )
                    }

                    TokenType.OPEN_BRACE -> {
                        visitor.start(
                            module = name,
                            name = fieldName,
                            interfaceName = interfaceName,
                            version = null,
                        )
                    }

                    else -> TODO()
                }
            }

            else -> TODO()
        }



        tokenizer.nextNotSpaceOrEof()
        if (tokenizer.type != TokenType.CLOSE_BRACE) {
            while (true) {
                when (tokenizer.type) {
                    TokenType.WORD -> {
                        val name = tokenizer.text
                        tokenizer.nextNotSpaceOrEof()
                        when (tokenizer.type) {
                            TokenType.CLOSE_BRACE -> {
                                visitor.type(name)
                                break
                            }

                            TokenType.COMMA -> {
                                visitor.type(name)
                                tokenizer.nextNotSpaceOrEof()
                                continue
                            }

                            TokenType.WORD -> {
                                check(tokenizer.text == "as")
                                tokenizer.nextNotSpaceOrEof()
                                tokenizer.assertType(TokenType.WORD)
                                visitor.type(name = name, alias = tokenizer.text)
                                tokenizer.nextNotSpaceOrEof()
                            }

                            else -> TODO()
                        }
                    }

                    TokenType.CLOSE_BRACE -> break
                    else -> TODO()
                }
                tokenizer.assertType(TokenType.CLOSE_BRACE)
            }
        }
        visitor.end()
    }
}