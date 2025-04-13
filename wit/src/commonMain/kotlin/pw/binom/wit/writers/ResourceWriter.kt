package pw.binom.wit.writers

import pw.binom.wit.visitors.ConstructorVisitor
import pw.binom.wit.visitors.FuncVisitor
import pw.binom.wit.visitors.ResourceVisitor

class ResourceWriter(private val sb: TextWriter) : ResourceVisitor {
    override fun start(name: String) {
        sb.append("resource ").append(name).append(" {").appendLine().levelInc()
    }

    override fun constructor(): ConstructorVisitor {
        sb.append("constructor")
        val visitor = ConstructorWriter(sb)
        return object : ConstructorVisitor by visitor {
            override fun end() {
                visitor.end()
                sb.append(";").appendLine()
            }
        }
    }

    override fun func(name: String): FuncVisitor {
        sb.append(name).append(": func")
        val visitor = FuncWriter(sb)
        return object : FuncVisitor by visitor {
            override fun end() {
                visitor.end()
                sb.append(";").appendLine()
            }
        }
    }

    override fun funcStatic(name: String): FuncVisitor {
        sb.append(name).append(": static func")
        val visitor = FuncWriter(sb)
        return object : FuncVisitor by visitor {
            override fun end() {
                visitor.end()
                sb.append(";").appendLine()
            }
        }
    }

    override fun end() {
        sb.levelDec().append("}").appendLine()
    }
}