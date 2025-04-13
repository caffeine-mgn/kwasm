package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.visitors.PackageVisitor

object PackageReader {
    fun read(tokenizer: BasicTokenizer, visitor: PackageVisitor) {
        visitor.start()
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        visitor.moduleName(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.COLON)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        visitor.fieldName(tokenizer.text)
        tokenizer.nextNotSpaceOrEof()

        if (tokenizer.type == TokenType.TERMINATOR) {
            visitor.end()
            return
        }
        tokenizer.assertType(TokenType.AT)
        val version = StringBuilder()
        do {
            tokenizer.nextNotSpaceOrEof()
            tokenizer.assertType(TokenType.DIGITAL)
            version.append(tokenizer.text)
            tokenizer.nextNotSpaceOrEof()
            if (tokenizer.type == TokenType.TERMINATOR) {
                break
            }
            tokenizer.assertType(TokenType.DOT)
            version.append(".")
        } while (true)
        visitor.version(version.toString())
        visitor.end()
    }
}