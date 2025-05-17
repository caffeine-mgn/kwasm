package pw.binom.wasm.readers

import pw.binom.wasm.WasmInput
import pw.binom.wasm.visitors.WasmVisitor

object WasmReader {

    val MAGIC = ubyteArrayOf(0x00u, 0x61u, 0x73u, 0x6du)

    fun read(input: WasmInput, visitor: WasmVisitor) {
        visitor.start()
        UByteArray(4) { input.i8u() }.contentEquals(WasmModuleReader.MAGIC)
        val version = input.i16s()
        val layout = input.i16s()

        visitor.end()
    }
}