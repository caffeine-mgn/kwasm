package pw.binom.wit

sealed interface Callable {
    val args: List<Pair<String, NonFinalType>>

    data class Constructor(
        override val args: List<Pair<String, NonFinalType>>,
    ) : Callable {
        override fun accept(scope: Scope, visitor: TypeVisitor) {
            args.forEach { (_, type) ->
                type.accept(visitor)
            }
        }
    }

    data class Function(
        override val args: List<Pair<String, NonFinalType>>,
        val result: FunctionResult,
    ) : Callable {
        override fun accept(scope: Scope, visitor: TypeVisitor) {
            args.forEach { (_, type) ->
                type.accept(visitor)
            }
            result.accept(scope, visitor)
        }
    }

    sealed interface FunctionResult {
        data object Void : FunctionResult {
            override fun accept(scope: Scope, visitor: TypeVisitor) {
            }

        }

        data class Single(val type: NonFinalType) : FunctionResult {
            override fun accept(scope: Scope, visitor: TypeVisitor) {
                type.accept(visitor)
            }
        }

        data class Multi(val types: List<Pair<String, NonFinalType>>) : FunctionResult {
            override fun accept(scope: Scope, visitor: TypeVisitor) {
                types.forEach { (_, type) ->
                    type.accept(visitor)
                }
            }

        }

        fun accept(scope: Scope, visitor: TypeVisitor)
    }

    fun accept(scope: Scope, visitor: TypeVisitor)
}