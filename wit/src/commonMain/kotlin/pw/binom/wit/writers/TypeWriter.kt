package pw.binom.wit.writers

import pw.binom.wit.visitors.ListVisitor
import pw.binom.wit.visitors.ResultVisitor
import pw.binom.wit.visitors.TypeVisitor

class TypeWriter(private val sb: TextWriter) : TypeVisitor {
    private val list by lazy { ListWriter(sb) }
    private val result by lazy { ResultWriter(sb) }
    private val tuple by lazy { TupleWriter(sb) }
    private val option by lazy { OptionWriter(sb) }

    override fun u8() {
        sb.append("u8")
    }

    override fun s8() {
        sb.append("s8")
    }

    override fun u16() {
        sb.append("u16")
    }

    override fun s16() {
        sb.append("s16")
    }

    override fun u32() {
        sb.append("u32")
    }

    override fun s32() {
        sb.append("s32")
    }

    override fun u64() {
        sb.append("u64")
    }

    override fun s64() {
        sb.append("s64")
    }

    override fun string() {
        sb.append("string")
    }

    override fun bool() {
        sb.append("bool")
    }

    override fun char() {
        sb.append("char")
    }

    override fun id(value: String) {
        sb.append(value)
    }

    override fun result(): ResultVisitor = result
    override fun list(): ListVisitor = list
    override fun tuple(): TupleWriter = tuple
    override fun option(): OptionWriter = option
}