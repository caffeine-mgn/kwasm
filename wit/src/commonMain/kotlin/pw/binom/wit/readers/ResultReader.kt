package pw.binom.wit.readers

import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.ListVisitor
import pw.binom.wit.visitors.ResourceVisitor
import pw.binom.wit.visitors.ResultVisitor

object ResultReader {
    fun read(tokenizer: BufferedTokenizer, visitor: ResultVisitor) {
        visitor.start()
        tokenizer.nextNotSpaceOrEof()
        when (tokenizer.type) {
            TokenType.LESS -> {
                TypeReader.read(tokenizer, visitor.first())
                tokenizer.nextNotSpaceOrEof()
                when (tokenizer.type) {
                    TokenType.COMMA -> {
                        TypeReader.read(tokenizer, visitor.second())
                        tokenizer.nextNotSpaceOrEof()
                        tokenizer.assertType(TokenType.GREATER)
                    }

                    TokenType.GREATER -> {
                        // Do nothing
                    }

                    else -> TODO()
                }
            }

            else -> tokenizer.pushBackCurrentToken()
        }

        visitor.end()
    }
}