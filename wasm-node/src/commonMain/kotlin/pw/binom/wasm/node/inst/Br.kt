package pw.binom.wasm.node.inst

import pw.binom.wasm.LabelId
import pw.binom.wasm.Opcodes
import pw.binom.wasm.visitors.ExpressionsVisitor

sealed class Br : Inst() {
  abstract val label: LabelId

  data class BR(override val label: LabelId) : Br() {
    override fun accept(visitor: ExpressionsVisitor) {
      visitor.br(opcode = Opcodes.BR, label = label)
    }
  }

  data class BR_IF(override val label: LabelId) : Br() {
    override fun accept(visitor: ExpressionsVisitor) {
      visitor.br(opcode = Opcodes.BR_IF, label = label)
    }
  }

  data class BR_ON_NULL(override val label: LabelId) : Br() {
    override fun accept(visitor: ExpressionsVisitor) {
      visitor.br(opcode = Opcodes.BR_ON_NULL, label = label)
    }
  }

  data class BR_ON_NON_NULL(override val label: LabelId) : Br() {
    override fun accept(visitor: ExpressionsVisitor) {
      visitor.br(opcode = Opcodes.BR_ON_NON_NULL, label = label)
    }
  }
}
