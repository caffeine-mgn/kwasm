package pw.binom.wit.readers

import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.utils.TokensRule
import pw.binom.wit.utils.readVersion
import pw.binom.wit.visitors.WorldVisitor
import kotlin.jvm.JvmInline

object WorldReader {
    fun read(tokenizer: BufferedTokenizer, visitor: WorldVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        visitor.start(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.OPEN_BRACE)
        while (true) {
            tokenizer.nextNotSpaceOrEof()
            when (tokenizer.type) {
                TokenType.CLOSE_BRACE -> break
                TokenType.WORD -> when (tokenizer.text) {
                    "export" -> WorldElementReader.read(tokenizer, visitor.export())
                    "import" -> WorldElementReader.read(tokenizer, visitor.import())
                    "include" -> parseInclude(tokenizer, visitor)
                    else -> TODO()
                }

                TokenType.LINE_COMMENT -> visitor.lineComment(tokenizer.text.substring(2))
                TokenType.AT -> AnnotationReader.read(tokenizer, visitor.annotation())

                else -> TODO()
            }
        }
        visitor.end()
    }

    private val simpleInclude = TokensRule.build {
        type(TokenType.WORD)
        type(TokenType.TERMINATOR)
    }

    private val external = TokensRule.build {
        type(TokenType.WORD)
        type(TokenType.COMMA)
        type(TokenType.WORD)
        type(TokenType.DIV)
        type(TokenType.WORD)
        type(TokenType.TERMINATOR)
    }

    private fun parseInclude(tokenizer: BufferedTokenizer, visitor: WorldVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        val first = tokenizer.text
        tokenizer.nextNotSpaceOrEof()
        when (tokenizer.type) {
            TokenType.TERMINATOR -> {
                visitor.include(first)
                return
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
                    TokenType.TERMINATOR -> {
                        visitor.include(
                            packageName = first,
                            packageField = fieldName,
                            interfaceName = interfaceName
                        )
                    }

                    TokenType.AT -> {
                        val version = tokenizer.readVersion()
                        visitor.include(
                            packageName = first,
                            packageField = fieldName,
                            interfaceName = interfaceName,
                            version = version,
                        )
                        tokenizer.nextNotSpaceOrEof()
                        tokenizer.assertType(TokenType.TERMINATOR)
                    }

                    else -> TODO()
                }
            }

            else -> TODO()
        }
    }
}
