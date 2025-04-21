package pw.binom.wit

import pw.binom.wit.node.InterfaceNode
import pw.binom.wit.node.PackageNode
import pw.binom.wit.node.WitNode
import pw.binom.wit.node.WorldNode

class WitPackage(
    val packageName: PackageNode,
    val worlds: Map<String, WorldNode>,
    val interfaces: Map<String, InterfaceNode>,
) {
    companion object {
        fun create(nodes: Collection<WitNode>): WitPackage {
            var packageExist: PackageNode? = null
            nodes.forEach {
                if (packageExist == null) {
                    packageExist = it.packageNode
                    return@forEach
                }
                if (it.packageNode != null) {
                    check(it.packageNode == packageExist) { "Package $packageExist does not match ${it.packageNode}" }
                }
            }
            check(packageExist != null) { "Package not defined" }
            val worlds = nodes.asSequence()
                .map {
                    it.items.asSequence().filterIsInstance<WorldNode>()
                }
                .flatten()
                .associateBy { it.name }

            val interfaces = nodes.asSequence()
                .map {
                    it.items.asSequence().filterIsInstance<InterfaceNode>()
                }
                .flatten()
                .associateBy { it.name }

            return WitPackage(
                packageName = packageExist!!,
                worlds = worlds,
                interfaces = interfaces,
            )
        }
    }
}