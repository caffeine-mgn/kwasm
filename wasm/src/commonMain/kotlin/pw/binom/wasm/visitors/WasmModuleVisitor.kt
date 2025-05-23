package pw.binom.wasm.visitors

import pw.binom.wasm.FunctionId

interface WasmModuleVisitor {
    fun start() {}
    fun end() {}
    fun version(version: Int) {}
    fun importSection(): ImportSectionVisitor = ImportSectionVisitor.SKIP
    fun functionSection(): FunctionSectionVisitor = FunctionSectionVisitor.SKIP
    fun codeSection(): CodeSectionVisitor = CodeSectionVisitor.SKIP
    fun typeSection(): TypeSectionVisitor = TypeSectionVisitor.SKIP
    fun customSection(): CustomSectionVisitor = CustomSectionVisitor.SKIP
    fun startSection(function: FunctionId) {}
    fun tableSection(): TableSectionVisitor = TableSectionVisitor.SKIP
    fun memorySection(): MemorySectionVisitor = MemorySectionVisitor.SKIP
    fun elementSection(): ElementSectionVisitor = ElementSectionVisitor.SKIP
    fun globalSection(): GlobalSectionVisitor = GlobalSectionVisitor.SKIP
    fun tagSection(): TagSectionVisitor = TagSectionVisitor.SKIP
    fun exportSection(): ExportSectionVisitor = ExportSectionVisitor.SKIP
    fun dataCountSection(): DataCountSectionVisitor = DataCountSectionVisitor.SKIP
    fun dataSection(): DataSectionVisitor = DataSectionVisitor.SKIP
}
