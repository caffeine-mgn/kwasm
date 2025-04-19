package pw.binom.wit.node

import pw.binom.wit.visitors.FuncVisitor
import pw.binom.wit.visitors.WorldElementVisitor
import pw.binom.wit.visitors.WorldVisitor

sealed interface WorldElement {
    companion object {
        fun visitor(
            kind: ReferenceKind,
            annotations: List<AnnotationNode>,
            func: (WorldElement) -> Unit,
        ) = object : WorldElementVisitor {
            override fun id(name: String) {
                func(
                    Reference.InternalInterface(
                        name = name,
                        kind = kind,
                        annotations = annotations,
                    )
                )
            }

            override fun func(name: String): FuncVisitor {
                val node = FuncNode(name = name, args = emptyList(), result = FunctionResult.VoidResult)

                return object : FuncVisitor by node {
                    override fun end() {
                        func(
                            Reference.Func(
                                node = node,
                                kind = kind,
                                annotations = annotations,
                            )
                        )
                    }
                }
            }

            override fun externalInterface(
                packageModule: String,
                packageName: String,
                interfaceName: String,
                version: String?,
            ) {
                func(
                    Reference.ExternalInterface(
                        packageModule = packageModule,
                        packageName = packageName,
                        interfaceName = interfaceName,
                        version = version,
                        kind = kind,
                        annotations = annotations,
                    )
                )
            }
        }
    }

    val annotations: List<AnnotationNode>

    enum class ReferenceKind {
        EXPORT,
        IMPORT,
    }

    sealed interface Reference : WorldElement {
        val kind: ReferenceKind

        override fun accept(visitor: WorldVisitor) {
            super.accept(visitor)
            when (kind) {
                ReferenceKind.EXPORT -> accept(visitor.export())
                ReferenceKind.IMPORT -> accept(visitor.import())
            }
        }

        data class Func(
            val node: FuncNode, override val kind: ReferenceKind,
            override val annotations: List<AnnotationNode>,
        ) : Reference {
            override fun accept(visitor: WorldElementVisitor) {
                node.accept(visitor.func(node.name))
            }
        }

        data class InternalInterface(
            var name: String,
            override val kind: ReferenceKind,
            override val annotations: List<AnnotationNode>,
        ) : Reference {
            override fun accept(visitor: WorldElementVisitor) {
                visitor.id(name)
            }
        }

        data class ExternalInterface(
            var packageModule: String,
            var packageName: String,
            var interfaceName: String,
            var version: String?,
            override val kind: ReferenceKind,
            override val annotations: List<AnnotationNode>,
        ) : Reference {

            val packageNode
                get() = PackageNode(module = packageModule, field = packageName, version = version)

            override fun accept(visitor: WorldElementVisitor) {
                visitor.externalInterface(
                    packageName = packageModule,
                    interfaceName = interfaceName,
                    packageModule = packageModule,
                    version = version,
                )
            }

        }

        fun accept(visitor: WorldElementVisitor)
    }

    data class Include(val worldName: String, override val annotations: List<AnnotationNode>) : WorldElement {
        override fun accept(visitor: WorldVisitor) {
            super.accept(visitor)
            visitor.include(worldName)
        }
    }

    data class ExternalInclude(
        val packageName: String,
        val packageField: String,
        val worldName: String,
        val version: String?,
        override val annotations: List<AnnotationNode>,
    ) : WorldElement {
        override fun accept(visitor: WorldVisitor) {
            super.accept(visitor)
            if (version == null) {
                visitor.include(packageName = packageName, packageField = packageField, interfaceName = worldName)
            } else {
                visitor.include(
                    packageName = packageName,
                    packageField = packageField,
                    interfaceName = worldName,
                    version = version
                )
            }
        }
    }

    fun accept(visitor: WorldVisitor) {
        annotations.forEach {
            it.accept(visitor.annotation())
        }
    }
}