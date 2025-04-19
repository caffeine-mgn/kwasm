package pw.binom.wasm.wasi

import kotlin.wasm.unsafe.Pointer
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi


@OptIn(UnsafeWasmMemoryApi::class)
value class WList(val ptr: Pointer) {
    var size: Int
        get() = (ptr + 4).loadInt()
        set(value) {
            (ptr + 4).storeInt(value)
        }

    operator fun get(index: Int) = ptr.loadInt() + (index * 4)
}