package pw.binom.wit

class WasmGenerator(val sb:Appendable) {
    fun generateType(name:String, type:TypeRef){
        println("$name ---> $type")
    }
}