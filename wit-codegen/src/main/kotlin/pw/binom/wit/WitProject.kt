package pw.binom.wit

import pw.binom.wit.node.InterfaceNode
import pw.binom.wit.node.PackageNode
import pw.binom.wit.node.WorldElement
import pw.binom.wit.node.WorldNode

class WitProject(val collections: Map<PackageNode, WitPackage>) {
    companion object;

    fun get(packageName: PackageNode, worldName: String): WorldSpace {
        val collection = collections[packageName] ?: TODO("Package $packageName not found")
        val world = collection.worlds[worldName] ?: TODO("World $worldName in package $packageName not found")
        return WorldSpace(world = world, c = collection)
    }


    fun collectInterfaces(
        packageName: PackageNode,
        worldName: String,
        worlds: MutableSet<WorldPath>,
        exportInterfaces: MutableSet<InterfacePath>,
        importInterfaces: MutableSet<InterfacePath>,
    ) {
        val collection = collections[packageName] ?: TODO("Package $packageName not found")
        val world = collection.worlds[worldName] ?: TODO("World $worldName in package $packageName not found")
        val worldPath = WorldPath(collection, world)
        check(worldPath !in worlds)
        worlds += worldPath
        world.elements.forEach { element ->
            when (element) {
                is WorldElement.ExternalInclude -> TODO()
                is WorldElement.Include -> {
                    collectInterfaces(
                        packageName = packageName,
                        worldName = element.worldName,
                        worlds = worlds,
                        exportInterfaces = exportInterfaces,
                        importInterfaces = importInterfaces,
                    )
                }

                is WorldElement.Reference.ExternalInterface -> {
                    val world = collections[element.packageNode] ?: TODO()
                    val int = world.interfaces[element.interfaceName] ?: TODO()
                    when (element.kind) {
                        WorldElement.ReferenceKind.EXPORT -> exportInterfaces
                        WorldElement.ReferenceKind.IMPORT -> importInterfaces
                    } += InterfacePath(world, int)
                }

                is WorldElement.Reference.Func -> TODO()
                is WorldElement.Reference.InternalInterface -> {
                    val int = collection.interfaces[element.name] ?: TODO()
                    when (element.kind) {
                        WorldElement.ReferenceKind.EXPORT -> exportInterfaces
                        WorldElement.ReferenceKind.IMPORT -> importInterfaces
                    } += InterfacePath(collection, int)
                }
            }
        }
    }
}

data class InterfacePath(val wit: WitPackage, val element: InterfaceNode)
data class WorldPath(val wit: WitPackage, val element: WorldNode)

class WorldSpace(val world: WorldNode, val c: WitPackage) {

}