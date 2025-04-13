package pw.binom.wit.visitors

interface FuncVisitor {
    fun start()
    fun arg(name: String): TypeVisitor
    fun result(): TypeVisitor
    fun resultMultiple(): MultipleReturnVisitor
    fun resultVoid()
    fun end()
}