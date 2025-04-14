package pw.binom.wit.node

import pw.binom.wit.visitors.*

class WitNode(var packageNode: PackageNode?, var items: List<WitElement>) : WitVisitor {
    private var argsList: ArrayList<WitElement>? = null
    override fun start() {
        argsList = ArrayList()
    }

    override fun end() {
        items = argsList!!
        argsList = null
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

    private val annotations = ArrayList<AnnotationNode>()

    override fun annotation(): AnnotationVisitor {
        val r = AnnotationNode("", emptyList())
        annotations += r
        return r
    }

    override fun witInterface(): InterfaceVisitor {
        val e = InterfaceNode("", emptyList())
        argsList!! += e
        return e
    }

    override fun world(): WorldVisitor {
        TODO("Not yet implemented")
    }
}