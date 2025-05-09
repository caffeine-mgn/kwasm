package pw.binom.wasm.writers

import pw.binom.wasm.*
import pw.binom.wasm.visitors.ValueVisitor

class ValueWriter(private val out: WasmOutput) :
    ValueVisitor,
    ValueVisitor.RefVisitor,
    ValueVisitor.HeapVisitor,
    ValueVisitor.VectorVisitor,
    ValueVisitor.NumberVisitor {

    // ValueVisitor

    override fun vecType(): ValueVisitor.VectorVisitor = this
    override fun numType(): ValueVisitor.NumberVisitor = this

    override fun refType(): ValueVisitor.RefVisitor = this

    // ValueVisitor.RefVisitor

    override fun refNull(type: AbsoluteHeapType) {
        type(type)
    }

    override fun refType(type: AbsoluteHeapType) {
        when (type) {
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_FUNC_REF -> out.i8u(Types.TYPE_REF_ABS_HEAP_FUNC_REF)
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_EXTERN -> out.i8u(Types.TYPE_REF_EXTERN_REF)
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_NONE -> out.i8u(Types.TYPE_REF_ABS_HEAP_NONE)
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_ANY -> out.i8u(Types.TYPE_REF_ABS_HEAP_ANY)
            else -> TODO()
        }
    }

    override fun ref(): ValueVisitor.HeapVisitor {
        out.i8u(Types.TYPE_REF)
        return this
    }

    override fun refNull(): ValueVisitor.HeapVisitor {
        out.i8u(Types.TYPE_REF_NULL)
        return this
    }

    // ValueVisitor.HeapVisitor

    override fun type(type: AbsoluteHeapType) {
        val byte = when (type) {
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_NO_FUNC -> Types.TYPE_REF_ABS_HEAP_NO_FUNC
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_NO_EXTERN -> Types.TYPE_REF_ABS_HEAP_NO_EXTERN
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_NONE -> Types.TYPE_REF_ABS_HEAP_NONE
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_FUNC_REF -> Types.TYPE_REF_ABS_HEAP_FUNC_REF
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_EXTERN -> Types.TYPE_REF_ABS_HEAP_EXTERN
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_ANY -> Types.TYPE_REF_ABS_HEAP_ANY
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_EQ -> Types.TYPE_REF_ABS_HEAP_EQ
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_I31 -> Types.TYPE_REF_ABS_HEAP_I31
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_STRUCT -> Types.TYPE_REF_ABS_HEAP_STRUCT
            AbsoluteHeapType.TYPE_REF_ABS_HEAP_ARRAY -> Types.TYPE_REF_ABS_HEAP_ARRAY
        }
        out.i8u(byte)
    }

    override fun type(type: TypeId) {
        out.v32s(type.value.toInt())
    }

    // ValueVisitor.VectorVisitor

    override fun v128() {
        out.i8u(Types.TYPE_VEC_V128)
    }


    // ValueVisitor.NumberVisitor

    override fun f32() {
        out.i8u(Types.TYPE_NUM_F32)
    }

    override fun f64() {
        out.i8u(Types.TYPE_NUM_F64)
    }

    override fun i32() {
        out.i8u(Types.TYPE_NUM_I32)
    }

    override fun i64() {
        out.i8u(Types.TYPE_NUM_I64)
    }
}
