package pw.binom.wit.visitors

interface BaseVisitor {
    fun lineComment(text: String) {}
    fun multilineComment(text: String) {}
}