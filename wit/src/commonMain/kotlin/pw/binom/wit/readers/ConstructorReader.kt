package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.visitors.ConstructorVisitor

object ConstructorReader {
    fun read(tokenizer: BufferedTokenizer, visitor: ConstructorVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.OPEN_PAREN)
        visitor.start()
        //read args
        while (true) {
            tokenizer.nextNotSpaceOrEof()
            when (tokenizer.type) {
                TokenType.WORD -> {
                    val argName = tokenizer.text
                    tokenizer.nextNotSpaceOrEof()
                    tokenizer.assertType(TokenType.COLON)
                    TypeReader.read(tokenizer, visitor.arg(argName))
                    tokenizer.nextNotSpaceOrEof()
                    when (tokenizer.type) {
                        TokenType.COMMA -> continue
                        TokenType.CLOSE_PAREN -> break
                        else -> TODO()
                    }
                }

                TokenType.CLOSE_PAREN -> break
                else -> TODO()
            }

            TODO()
        }
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.TERMINATOR)
        visitor.end()
    }
}