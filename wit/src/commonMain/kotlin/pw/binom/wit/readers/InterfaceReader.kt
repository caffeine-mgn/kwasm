package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.FuncVisitor
import pw.binom.wit.visitors.InterfaceVisitor
import pw.binom.wit.visitors.TypeAliasVisitor
import pw.binom.wit.visitors.UseVisitor

object InterfaceReader {
    fun read(tokenizer: BufferedTokenizer, visitor: InterfaceVisitor) {
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
                    "type" -> readTypeAlias(tokenizer, visitor.typeAlias())
                    "record" -> RecordVisitor.read(tokenizer, visitor.record())
                    "use" -> readUse(tokenizer, visitor.use())
                    "enum" -> EnumReader.read(tokenizer, visitor.enum())
                    "resource" -> ResourceReader.read(tokenizer, visitor.resource())
                    "variant" -> VariantReader.read(tokenizer, visitor.variant())
                    else -> readFunc(tokenizer, visitor.func(tokenizer.text))
//                    else -> TODO("type=${tokenizer.type} text=${tokenizer.text} start=${tokenizer.start}")
                }

                TokenType.AT -> AnnotationReader.read(tokenizer, visitor.annotation())
                TokenType.LINE_COMMENT -> visitor.lineComment(tokenizer.text.substring(2))

                else -> TODO("type=${tokenizer.type} text=${tokenizer.text} start=${tokenizer.start}")
            }
        }
        visitor.end()
    }

    private fun readFunc(tokenizer: BufferedTokenizer, visitor: FuncVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.COLON)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        check(tokenizer.text == "func")
        FuncReader.read(tokenizer, visitor)
    }

    private fun readUse(tokenizer: Tokenizer, visitor: UseVisitor) {
        UseReader.read(tokenizer, visitor)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.TERMINATOR)
    }

    private fun readTypeAlias(tokenizer: BufferedTokenizer, visitor: TypeAliasVisitor) {
        TypeAliasReader.read(tokenizer, visitor)
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.TERMINATOR)
    }
}