package pw.binom.wit

sealed interface Struct {
    val scope: Scope
    val name: String
    val pack: Package
        get() = when (val scope = scope) {
            is InterfaceScope -> scope.parent.name
            is PackageScope -> scope.name
            is ProjectScope -> TODO()
            is FinalType.Resource -> scope.pack
        }
    val interfaceName: String
        get() = when (val scope = scope) {
            is InterfaceScope -> scope.name
            is PackageScope -> TODO()
            is ProjectScope -> TODO()
            is FinalType.Resource -> scope.interfaceName
        }
}