package pw.binom.wit.readers

import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.visitors.VariantVisitor

object VariantReader {
    fun read(tokenizer: BufferedTokenizer, visitor: VariantVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        visitor.start(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.OPEN_BRACE)
        tokenizer.nextNotSpaceOrEof()
        while (true) {
            when (tokenizer.type) {
                TokenType.WORD -> {
                    val name = tokenizer.text
                    tokenizer.nextNotSpaceOrEof()
                    when (tokenizer.type) {
                        TokenType.CLOSE_BRACE -> {
                            visitor.element(name)
                            break
                        }

                        TokenType.COMMA,
                            -> {
                            visitor.element(name)
                            tokenizer.nextNotSpaceOrEof()
                        }

                        TokenType.OPEN_PAREN -> {
                            TypeReader.read(tokenizer, visitor.elementWithType(name))
                            tokenizer.nextNotSpaceOrEof()
                            tokenizer.assertType(TokenType.CLOSE_PAREN)
                            tokenizer.nextNotSpaceOrEof()
                            when (tokenizer.type) {
                                TokenType.CLOSE_BRACE -> break
                                TokenType.COMMA -> tokenizer.nextNotSpaceOrEof()
                                else -> TODO()
                            }
                        }

                        else -> TODO()
                    }
                }

                TokenType.LINE_COMMENT -> {
                    visitor.lineComment(tokenizer.text.substring(2))
                    tokenizer.nextNotSpaceOrEof()
                }

                TokenType.CLOSE_BRACE -> break
                else -> TODO()
            }
        }
        visitor.end()
    }
}