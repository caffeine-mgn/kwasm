package pw.binom.wit.readers

import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.visitors.TypeVisitor

object TypeReader {
    fun read(tokenizer: BufferedTokenizer, visitor: TypeVisitor) {
        tokenizer.nextNotSpaceOrEof()
        when (tokenizer.type) {
            TokenType.WORD -> when (tokenizer.text) {
                "u8" -> visitor.u8()
                "s8" -> visitor.s8()
                "u16" -> visitor.u16()
                "s16" -> visitor.s16()
                "u32" -> visitor.u32()
                "s32" -> visitor.s32()
                "u64" -> visitor.u64()
                "s64" -> visitor.s64()
                "bool" -> visitor.bool()
                "char" -> visitor.char()
                "string" -> visitor.string()
                "list" -> ListReader.read(tokenizer, visitor.list())
                "borrow" -> BorrowReader.read(tokenizer, visitor.borrow())
                "result" -> ResultReader.read(tokenizer, visitor.result())
                "tuple" -> TupleReader.read(tokenizer, visitor.tuple())
                "option" -> OptionReader.read(tokenizer, visitor.option())
                else -> visitor.id(tokenizer.text)
            }

            TokenType.UNDERSCORE -> visitor.something()
            else -> TODO()
        }
        visitor.end()
    }
}