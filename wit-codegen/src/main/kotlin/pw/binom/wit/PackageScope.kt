package pw.binom.wit

interface PackageScope : Scope {
    fun findWorld(name:String)
    fun findInterface(name:String)
}