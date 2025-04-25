package pw.binom.wit

import pw.binom.wit.node.PackageNode
import pw.binom.wit.node.WorldElement

class ProjectScope(val collections: Map<PackageNode, WitPackage>) : Scope {
    companion object;
    private val packages = collections.entries.associate { (pack, wit) ->
        pack.toPackage to PackageScope(
            parent = this,
            pack = pack,
            witPackage = wit
        )
    }

    operator fun get(pack: Package) = packages[pack]

    fun getArtifacts(
        packageName: PackageNode,
        worldName: String,
    ): Artifacts {
        val exports = HashSet<InterfacePath>()
        val imports = HashSet<InterfacePath>()
        collectInterfaces(
            packageName = packageName,
            worldName = worldName,
            worlds = HashSet(),
            exportInterfaces = exports,
            importInterfaces = imports,
        )
        return Artifacts(
            exportInterfaces = exports,
            importInterfaces = imports,
            scope = this,
        )
    }

    class Artifacts(
        val scope: ProjectScope,
        val exportInterfaces: Set<InterfacePath>,
        val importInterfaces: Set<InterfacePath>,
    )

    private fun collectInterfaces(
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

    override fun getType(name: String): NonFinalType {
        TODO("Not yet implemented")
    }

    override fun getType(pack: Package, interfaceName: String, name: String): NonFinalType {
        val scope = packages[pack] ?: TODO("Package $pack not found")
        return scope.getType(pack = pack, interfaceName = interfaceName, name = name)
    }
}