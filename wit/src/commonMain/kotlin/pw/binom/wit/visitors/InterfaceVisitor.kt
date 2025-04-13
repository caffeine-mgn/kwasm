package pw.binom.wit.visitors

interface InterfaceVisitor {
    fun start(name: String)
    fun typeAlias(): TypeAliasVisitor
    fun record(): RecordVisitor
    fun use(): UseVisitor
    fun enum(): EnumVisitor
    fun resource(): ResourceVisitor
    fun variant(): VariantVisitor
    fun func(name: String): FuncVisitor
    fun end()
}