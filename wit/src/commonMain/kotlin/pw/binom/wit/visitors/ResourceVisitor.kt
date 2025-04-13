package pw.binom.wit.visitors

import kotlin.js.JsName

interface ResourceVisitor {
    fun start(name: String)

    @JsName("constructor2")
    fun constructor(): ConstructorVisitor
    fun func(name: String): FuncVisitor
    fun funcStatic(name: String): FuncVisitor
    fun end()
}