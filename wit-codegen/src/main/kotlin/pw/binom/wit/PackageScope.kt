package pw.binom.wit

import pw.binom.wit.node.PackageNode

class PackageScope(val pack: PackageNode, val v: WitPackage) : Scope {
    val worlds = v.worlds.asSequence().map {
        it.key to WorldScope(this)
    }.toMap()
    val interfaces = v.interfaces.asSequence().map {
        it.key to InterfaceScope(it.value, this)
    }.toMap()
}