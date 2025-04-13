package pw.binom.wit.readers

import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.visitors.WorldVisitor

object WorldReader {
    fun read(tokenizer: BasicTokenizer, visitor: WorldVisitor) {
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
                    else -> TODO()
                }

                else -> TODO()
            }
        }
        visitor.end()
    }
}