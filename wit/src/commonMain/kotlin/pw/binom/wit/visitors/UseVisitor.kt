package pw.binom.wit.visitors

interface UseVisitor {
    fun start(name: String)
    fun start(module: String, name: String, interfaceName: String, version: String?)
    fun type(name: String)
    fun type(name: String, alias:String)
    fun end()
}