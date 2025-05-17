package pw.binom.wasm.wasi.clock

import pw.binom.date.Date
import pw.binom.date.DateTime
import pw.binom.wasm.runner.ExecuteContext
import pw.binom.wasm.runner.ImportResolver
import pw.binom.wasm.runner.RType
import pw.binom.wasm.runner.Value
import kotlin.time.Duration.Companion.milliseconds

object ClockModule : ImportResolver {
    private const val CLOCK_ID_MONOTONIC = 1
    override fun func(module: String, field: String, type: RType.Function): ((ExecuteContext) -> Unit)? =
        when {
            module == "wasi:clocks/monotonic-clock@0.2.5" && field == "now" -> now()
            module == "wasi_snapshot_preview1" && field == "clock_time_get" -> nowOld()
            else -> super.func(module, field, type)
        }

    fun nowOld(): (ExecuteContext) -> Unit = {
        val clockId = it.args[0] as Value.Primitive.I32
        check(clockId.value == CLOCK_ID_MONOTONIC)
        val precision = it.args[1] as Value.Primitive.I64
        val resultPtr = it.args[2] as Value.Primitive.I32
        it.runner.memory[0].pushI64(
            value = DateTime.nowTime.milliseconds.inWholeNanoseconds,
            offset = resultPtr.value.toUInt(),
            align = 1u,
        )
        it.pushResult(Value.Primitive.I32(0))
    }

    fun now(): (ExecuteContext) -> Unit = {
        it.pushResult(Value.Primitive.I64(DateTime.nowTime.milliseconds.inWholeNanoseconds))
    }
}