package pw.binom.wit

object Resolver {
    data class Result(
        val types: Set<Struct>,
        val importFunctions: Map<Pair<Scope, String>, Callable.Function>,
        val exportFunctions: Map<Pair<Scope, String>, Callable.Function>,
    )

    fun resolve(artifacts: ProjectScope.Artifacts): Result {
        val types = HashSet<FinalType>()
        val visitor = VImpl(types = types)
        val importFunctions = HashMap<Pair<Scope, String>, Callable.Function>()
        val exportFunctions = HashMap<Pair<Scope, String>, Callable.Function>()
        artifacts.importInterfaces.forEach {
            val pack = artifacts.scope[it.wit.packageName.toPackage] ?: TODO()
            val int = pack.interfaces[it.element.name] ?: TODO()
            int.functions.forEach { (name, func) ->
                func.accept(scope = int, visitor = visitor)
                importFunctions[int to name] = func
            }
            int.types2.forEach { (_, type) ->
                type.accept(visitor = visitor)
            }
        }
        artifacts.exportInterfaces.forEach {
            val pack = artifacts.scope[it.wit.packageName.toPackage] ?: TODO()
            val int = pack.interfaces[it.element.name] ?: TODO()

            int.functions.forEach { (name, func) ->
                exportFunctions[int to name] = func
                func.accept(scope = int, visitor = visitor)
            }
            int.types2.forEach { (_, type) ->
                type.accept(visitor = visitor)
            }
        }
        return Result(
            types = types.asSequence().filterIsInstance<Struct>().toSet(),
            importFunctions = importFunctions,
            exportFunctions = exportFunctions,
        )
    }

    private class VImpl(
        val types: MutableSet<FinalType>,
    ) : TypeVisitor {

        override fun visit(type: FinalType) {
            if (type in types) {
                return
            }
            types += type
            type.accept(visitor = this)
        }

    }

    private fun typeResolve() {

    }
}