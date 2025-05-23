package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.visitors.FuncVisitor

object FuncReader {
    fun read(tokenizer: BufferedTokenizer, visitor: FuncVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.OPEN_PAREN)
        visitor.start()
        //read args
        while (true) {
            tokenizer.nextNotSpaceOrEof()
            when (tokenizer.type) {
                TokenType.OPERATOR -> continue
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
                TokenType.LINE_COMMENT -> {
                    visitor.lineComment(tokenizer.text.substring(2))
                    continue
                }

                else -> TODO()
            }
            TODO()
        }
        tokenizer.nextNotSpaceOrEof()
        when (tokenizer.type) {
            TokenType.TERMINATOR -> {
                visitor.resultVoid()
                visitor.end()
                return
            }

            TokenType.RESULT -> {
                tokenizer.nextNotSpaceOrEof()
                when (tokenizer.type) {
                    TokenType.OPEN_PAREN -> MultipleReturnReader.read(tokenizer, visitor.resultMultiple())
                    TokenType.WORD -> {
                        tokenizer.pushBackCurrentToken()
                        TypeReader.read(tokenizer = tokenizer, visitor.result())
                    }

                    else -> TODO()
                }
            }

            else -> TODO()
        }
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.TERMINATOR)
        visitor.end()
    }
}