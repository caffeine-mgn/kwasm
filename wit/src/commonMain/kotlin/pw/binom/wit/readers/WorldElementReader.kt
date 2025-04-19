package pw.binom.wit.readers

import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.WorldElementVisitor
import pw.binom.wit.visitors.WorldVisitor

object WorldElementReader {
    fun read(tokenizer: BufferedTokenizer, visitor: WorldElementVisitor) {
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.WORD)
        val name = tokenizer.text
        tokenizer.nextNotSpaceOrEof()
        when (tokenizer.type) {
            TokenType.TERMINATOR -> visitor.id(name)
            TokenType.COLON -> {
                tokenizer.nextNotSpaceOrEof()
                tokenizer.assertType(TokenType.WORD)
                when (tokenizer.text) {
                    "func" -> FuncReader.read(tokenizer, visitor.func(name))
                    else -> readInterface(tokenizer = tokenizer, moduleName = name, visitor = visitor)
                }
            }

            else -> TODO()
        }
    }

    private fun readInterface(tokenizer: Tokenizer, moduleName: String, visitor: WorldElementVisitor) {
        tokenizer.assertType(TokenType.WORD)
        val fieldName = tokenizer.text
        tokenizer.nextNotSpaceOrEof()
        tokenizer.assertType(TokenType.DIV)
        tokenizer.nextNotSpaceOrEof()
        val interfaceName = tokenizer.text
        tokenizer.nextNotSpaceOrEof()
        when (tokenizer.type) {
            TokenType.AT -> {
                val sb = StringBuilder()
                sb.append(tokenizer.readUntil { it.type != TokenType.TERMINATOR })
                visitor.externalInterface(
                    packageModule = moduleName,
                    packageName = fieldName,
                    interfaceName = interfaceName,
                    version = sb.toString()
                )
            }

            TokenType.TERMINATOR -> {
                visitor.externalInterface(
                    packageModule = moduleName,
                    packageName = fieldName,
                    interfaceName = interfaceName,
                    version = null,
                )
            }

            else -> TODO()
        }
    }
}