package pw.binom.wit.readers

import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.WorldVisitor

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

    private fun parseInclude(tokenizer: Tokenizer, visitor: WorldVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        visitor.include(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.TERMINATOR)
    }
}