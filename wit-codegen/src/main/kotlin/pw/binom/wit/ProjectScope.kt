package pw.binom.wit

import pw.binom.wit.node.PackageNode

class ProjectScope(val collections: Map<PackageNode, WitPackage>) {
    companion object;
    init {
        collections.forEach { (pack, wit) ->
            PackageScope(pack, wit)
        }
    }
}