package pw.binom.wasm.runner

import pw.binom.wasm.FunctionId
import pw.binom.wasm.node.Export
import pw.binom.wasm.node.ValueType

interface ImportResolver {
    fun init(context: Context) {}
    fun global(module: String, field: String, type: ValueType, mutable: Boolean): GlobalVar? = null
    fun memory(module: String, field: String, inital: UInt, max: UInt?): MemorySpace? = null
    fun func(module: String, field: String, type: RType.Function): ((ExecuteContext) -> Unit)? = null
}

interface Context {
    fun findFunction(name: String): Export.Function?
    fun runFunction(id: FunctionId, args: List<Value>): List<Value>
}

interface ExecuteContext {
    val runner: Runner
    val args: List<Value>
    fun pushResult(value: Value)
    fun stop()
}
