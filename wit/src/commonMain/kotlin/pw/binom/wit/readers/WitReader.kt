package pw.binom.wit.readers

import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.WitVisitor

object WitReader {
    fun parse(tokenizer: BufferedTokenizer, fileRootVisitor: WitVisitor) {
        fileRootVisitor.start()
        while (true) {
            val hasNext = tokenizer.nextNotSpace()
            if (!hasNext) {
                fileRootVisitor.end()
                return
            }
            when (tokenizer.type) {
                TokenType.WORD -> when (tokenizer.text) {
                    "package" -> PackageReader.read(tokenizer, fileRootVisitor.witPackage())
                    "interface" -> InterfaceReader.read(tokenizer, fileRootVisitor.witInterface())
                    "world" -> WorldReader.read(tokenizer, fileRootVisitor.world())
//                    "use" -> UseReader.read(tokenizer, fileRootVisitor.use())
                    else -> TODO("Token ${tokenizer.type}: ${tokenizer.text}")
                }

                TokenType.LINE_COMMENT -> fileRootVisitor.lineComment(tokenizer.text.substring(2))
                TokenType.AT -> AnnotationReader.read(tokenizer, fileRootVisitor.annotation())

                else -> TODO("Unknown type ${tokenizer.type}: ${tokenizer.text}")
            }
        }
    }
}

