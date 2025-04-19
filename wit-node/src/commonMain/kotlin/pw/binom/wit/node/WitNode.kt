package pw.binom.wit.node

import pw.binom.wit.visitors.*

data class WitNode(var packageNode: PackageNode?, var items: List<WitElement>) : WitVisitor {
    private var tmpElements: ArrayList<WitElement>? = null
    private var tmpAnnotations: ArrayList<AnnotationNode>? = null

    val worlds
        get() = items.asSequence().filterIsInstance<WorldNode>()

    override fun start() {
        tmpElements = ArrayList()
        tmpAnnotations = ArrayList()
    }

    override fun end() {
        items = tmpElements!!
        tmpElements = null
        tmpAnnotations = null
    }

    fun accept(visitor: WitVisitor) {
        packageNode?.accept(visitor.witPackage())
        items.forEach { it.accept(visitor) }
    }

    override fun witPackage(): PackageVisitor {
        val p = PackageNode("", "", null)
        packageNode = p
        return p
    }

    override fun annotation(): AnnotationVisitor {
        val r = AnnotationNode("", emptyList())
        tmpAnnotations!! += r
        return r
    }

    override fun witInterface(): InterfaceVisitor {
        val e = InterfaceNode("", emptyList(), tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        tmpElements!! += e
        return e
    }

    override fun world(): WorldVisitor {
        val e = WorldNode("", emptyList(), tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        tmpElements!! += e
        return e
    }
}