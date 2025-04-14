package pw.binom.wit.visitors

interface TypeVisitor {
    companion object {
        val EMPTY = object : TypeVisitor {}
    }

    fun u8() {}
    fun s8() {}
    fun u16() {}
    fun s16() {}
    fun u32() {}
    fun s32() {}
    fun u64() {}
    fun s64() {}
    fun f32() {}
    fun f64() {}
    fun string() {}
    fun bool() {}
    fun char() {}
    fun id(value: String) {}
    fun result(): ResultVisitor = ResultVisitor.EMPTY
    fun list(): ListVisitor = ListVisitor.EMPTY
    fun tuple(): TupleVisitor = TupleVisitor.EMPTY
    fun option(): OptionVisitor = OptionVisitor.EMPTY
}