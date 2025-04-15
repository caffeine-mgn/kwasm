package pw.binom.wit.visitors

import kotlin.js.JsName

interface ResourceVisitor:BaseVisitor {
    fun start(name: String)

    @JsName("constructor2")
    fun init(): ConstructorVisitor
    fun annotation(): AnnotationVisitor
    fun func(name: String): FuncVisitor
    fun funcStatic(name: String): FuncVisitor
    fun end()
}