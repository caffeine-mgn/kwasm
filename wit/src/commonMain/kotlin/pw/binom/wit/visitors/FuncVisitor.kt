package pw.binom.wit.visitors

interface FuncVisitor : BaseVisitor {
    fun start()
    fun arg(name: String): TypeVisitor
    fun result(): TypeVisitor
    fun resultMultiple(): MultipleReturnVisitor
    fun resultVoid()
    fun end()
}