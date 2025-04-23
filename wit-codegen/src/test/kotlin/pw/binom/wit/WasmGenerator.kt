package pw.binom.wit

class WasmGenerator(val sb:Appendable) {
    fun generateType(name:String, type:TType){
        println("$name ---> $type")
    }
}