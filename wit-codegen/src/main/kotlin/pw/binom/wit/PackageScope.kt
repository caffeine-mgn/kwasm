package pw.binom.wit

import pw.binom.wit.node.PackageNode

class PackageScope(val parent: ProjectScope, pack: PackageNode, witPackage: WitPackage) : Scope {
    val name = pack.toPackage
    val worlds = witPackage.worlds.asSequence().map {
        it.key to WorldScope(this)
    }.toMap()
    val interfaces = witPackage.interfaces.asSequence().map {
        it.key to InterfaceScope(it.value, this)
    }.toMap()


    override fun getType(name: String): NonFinalType {
        TODO("Not yet implemented")
    }

    override fun getType(pack: Package, interfaceName: String, name: String): NonFinalType {
        if (pack == this.name) {
            val int = interfaces[interfaceName] ?: TODO("Interface ${this.name}/$interfaceName not found")
            return int.getType(name)
        }
        return parent.getType(pack = pack, interfaceName = interfaceName, name = name)
    }

    fun accept(visitor: StructVisitor) {
        interfaces.values.forEach {
            it.accept(visitor)
        }
    }
}