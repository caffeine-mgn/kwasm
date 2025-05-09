package pw.binom.wasm.node.inst

import pw.binom.wasm.FieldId
import pw.binom.wasm.Opcodes
import pw.binom.wasm.TypeId
import pw.binom.wasm.visitors.ExpressionsVisitor

sealed class StructOp : Inst() {
  abstract val type: TypeId
  abstract val field: FieldId


  data class GC_STRUCT_SET(override val type: TypeId, override val field: FieldId) : StructOp() {
    override fun accept(visitor: ExpressionsVisitor) {
      visitor.structOp(
        gcOpcode = Opcodes.GC_STRUCT_SET,
        type = type,
        field = field,
      )
    }
    override fun toString(): String = "struct.set ${type.value} ${field.id}"
  }

  data class GC_STRUCT_GET(override val type: TypeId, override val field: FieldId) : StructOp() {
    override fun accept(visitor: ExpressionsVisitor) {
      visitor.structOp(
        gcOpcode = Opcodes.GC_STRUCT_GET,
        type = type,
        field = field,
      )
    }

    override fun toString(): String = "struct.get ${type.value} ${field.id}"
  }

  data class GC_STRUCT_GET_S(override val type: TypeId, override val field: FieldId) : StructOp() {
    override fun accept(visitor: ExpressionsVisitor) {
      visitor.structOp(
        gcOpcode = Opcodes.GC_STRUCT_GET_S,
        type = type,
        field = field,
      )
    }
    override fun toString(): String = "struct.get_s ${type.value} ${field.id}"
  }

  data class GC_STRUCT_GET_U(override val type: TypeId, override val field: FieldId) : StructOp() {
    override fun accept(visitor: ExpressionsVisitor) {
      visitor.structOp(
        gcOpcode = Opcodes.GC_STRUCT_GET_U,
        type = type,
        field = field,
      )
    }
    override fun toString(): String = "struct.get_u ${type.value} ${field.id}"
  }
}
