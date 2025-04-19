package pw.binom.wit.node

import pw.binom.wit.visitors.AnnotationVisitor
import pw.binom.wit.visitors.WitVisitor
import pw.binom.wit.visitors.WorldElementVisitor
import pw.binom.wit.visitors.WorldVisitor

class WorldNode(
    var name: String,
    var elements: List<WorldElement>,
    var annotations: List<AnnotationNode>,
) : WitElement, WorldVisitor {

    private var tmpAnnotations: ArrayList<AnnotationNode>? = null
    private var tmpElements: ArrayList<WorldElement>? = null

    fun accept(visitor: WorldVisitor) {
        elements.forEach {
            it.accept(visitor)
        }
    }

    override fun accept(visitor: WitVisitor) {
        annotations.forEach {
            it.accept(visitor.annotation())
        }
        accept(visitor.world())
    }

    override fun start(name: String) {
        this.name = name
        tmpAnnotations = ArrayList()
        tmpElements = ArrayList()
    }

    override fun import(): WorldElementVisitor =
        WorldElement.visitor(
            kind = WorldElement.ReferenceKind.IMPORT,
            annotations = tmpAnnotations!!
        ) {
            tmpElements!! += it
            tmpAnnotations = ArrayList()
        }

    override fun export(): WorldElementVisitor =
        WorldElement.visitor(
            kind = WorldElement.ReferenceKind.EXPORT,
            annotations = tmpAnnotations!!
        ) {
            tmpElements!! += it
            tmpAnnotations = ArrayList()
        }

    override fun annotation(): AnnotationVisitor {
        val n = AnnotationNode("", emptyList())
        tmpAnnotations!! += n

        return n
    }

    override fun include(worldName: String) {
        tmpElements!! += WorldElement.Include(worldName, annotations = tmpAnnotations!!)
        tmpAnnotations = ArrayList()
    }

    override fun include(packageName: String, packageField: String, interfaceName: String) {
        tmpElements!! += WorldElement.ExternalInclude(
            packageName = packageName,
            packageField = packageField,
            worldName = interfaceName,
            version = null,
            annotations = tmpAnnotations!!
        )
        tmpAnnotations = ArrayList()
    }

    override fun include(packageName: String, packageField: String, interfaceName: String, version: String) {
        tmpElements!! += WorldElement.ExternalInclude(
            packageName = packageName,
            packageField = packageField,
            worldName = interfaceName,
            version = version,
            annotations = tmpAnnotations!!
        )
        tmpAnnotations = ArrayList()
    }

    override fun end() {
        tmpAnnotations = null
        elements = tmpElements!!
        tmpElements = null
    }
}