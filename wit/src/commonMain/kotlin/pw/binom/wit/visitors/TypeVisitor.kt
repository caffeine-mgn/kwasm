package pw.binom.wit.visitors

import pw.binom.wit.writers.OptionWriter
import pw.binom.wit.writers.TupleWriter

interface TypeVisitor {
    fun u8()
    fun s8()
    fun u16()
    fun s16()
    fun u32()
    fun s32()
    fun u64()
    fun s64()
    fun string()
    fun bool()
    fun char()
    fun id(value: String)
    fun result(): ResultVisitor
    fun list(): ListVisitor
    fun tuple(): TupleWriter
    fun option(): OptionWriter
}