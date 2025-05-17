package pw.binom.wasm.visitors

interface WasmVisitor {
    fun start()
    fun end()
    fun module(): WasmModuleVisitor
    fun component(): WasmComponentVisitor
}