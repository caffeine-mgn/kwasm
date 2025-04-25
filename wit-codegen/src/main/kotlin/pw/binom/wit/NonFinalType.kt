package pw.binom.wit

sealed interface NonFinalType {
    data class Primitive(val final: FinalType.Primitive) : NonFinalType {
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(final)
        }

        override fun resolve(): FinalType = final
    }

    data class Alias(val other: NonFinalType) : NonFinalType {
        private var resolved: FinalType? = null
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(resolve())
        }

        override fun resolve(): FinalType {
            if (resolved != null) {
                return resolved!!
            }
            val resolved = other.resolve()
            this.resolved = resolved
            return resolved
        }
    }

    sealed interface Use : NonFinalType {
        val typeName: String
        val interfaceName: String

        data class Internal(
            override val interfaceName: String,
            override val typeName: String,
            val scope: Scope,
        ) : Use {
            private var resolved: FinalType? = null
            override fun accept(visitor: TypeVisitor) {
                visitor.visit(resolve())
            }

            override fun resolve(): FinalType {
                return when (val s = scope) {
                    is InterfaceScope -> scope.getType(
                        pack = s.parent.name,
                        interfaceName = interfaceName,
                        name = typeName
                    ).resolve()

                    is PackageScope -> TODO()
                    is ProjectScope -> TODO()
                    is FinalType.Resource -> TODO()
                }
                TODO()
//                if (resolved != null) {
//                    return resolved!!
//                }
//                val int = scope.pack.interfaces[interfaceName] ?: TODO()
//                val resolved = int.getType(typeName).resolve(int)
//                this.resolved = resolved
//                return resolved
            }

        }

        data class External(
            val packageName: Package,
            override val interfaceName: String,
            override val typeName: String,
            val scope: Scope,
        ) : Use {
            private var resolved: FinalType? = null
            override fun accept(visitor: TypeVisitor) {
                visitor.visit(resolve())
            }

            override fun resolve(): FinalType {
                if (resolved != null) {
                    return resolved!!
                }
                val resolved = scope.getType(
                    pack = packageName,
                    interfaceName = interfaceName,
                    name = typeName,
                ).resolve()
                this.resolved = resolved
                return resolved
            }

        }
    }

    data class ByName(
        val name: String,
        val scope: Scope,
    ) : NonFinalType {
        private var resolved: FinalType? = null
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(resolve())
        }

        override fun resolve(): FinalType {
            if (resolved != null) {
                return resolved!!
            }
            val resolved = scope.getType(name).resolve()
            this.resolved = resolved
            return resolved
        }
    }

    fun accept(visitor: TypeVisitor)
    fun resolve(): FinalType
}