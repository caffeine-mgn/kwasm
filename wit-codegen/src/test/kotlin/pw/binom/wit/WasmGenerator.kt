package pw.binom.wit

class WasmGenerator(val sb: Appendable) {
    fun gen(k: Resolver.Result) {
        k.types.forEach { type ->
            when (type) {
                is FinalType.Enum -> {}
                is FinalType.Flags -> {}
                is FinalType.Record -> {}
                is FinalType.Resource -> gen(type)
                is FinalType.Variant -> {}
            }
        }
    }

    fun gen(r: FinalType.Resource) {
        r.functions.forEach { (name, function) ->
            val methodKotlinName = name.replace('-', '_')
            sb.appendLine("@WasmImport(module = \"${r.pack.nameSpace}:${r.pack.packageName}/${r.interfaceName}\", name = \"[method]${r.name}.$methodKotlinName\")")
            sb.append("fun __${r.fullName}_method_${r.kotlinName}_$methodKotlinName(")
            sb.appendLine(")")
        }
        sb.appendLine("@WasmImport(module = \"${r.pack.nameSpace}:${r.pack.packageName}/${r.interfaceName}\", name = \"[resource-drop]${r.name}\")")
        sb.appendLine("external fun ${r.destructorName}(handle: Int)")
    }

    val Struct.kotlinName
        get() = name.replace('-', '_')

    val FinalType.Resource.fullName
        get() = "${pack.nameSpace}_${pack.packageName}_${name}".replace('-', '_')

    val FinalType.Resource.destructorName
        get() = "__${fullName}_drop"
}