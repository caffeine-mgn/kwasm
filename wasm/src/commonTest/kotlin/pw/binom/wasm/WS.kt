package pw.binom.wasm

import pw.binom.wasm.visitors.WasmModuleVisitor

class WS(val out: WasmModuleVisitor) : WasmModuleVisitor by out {
  override fun codeSection() = CS(out.codeSection())
//  override fun codeSection() = CodeSectionWriter(sectionData)
}
