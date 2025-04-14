package pw.binom.wit.writers

import pw.binom.wit.visitors.AnnotationVisitor
import pw.binom.wit.visitors.FuncVisitor
import pw.binom.wit.visitors.WorldElementVisitor
import pw.binom.wit.visitors.WorldVisitor

class WorldWriter(private val sb: TextWriter) : WorldVisitor {
    override fun start(name: String) {
        sb.append("world ").append(name).append(" {").appendLine().levelInc()
    }

    override fun import(): WorldElementVisitor {
        sb.append("export ")
        return build()
    }

    override fun export(): WorldElementVisitor {
        sb.append("export ")
        return build()
    }

    override fun annotation(): AnnotationVisitor = AnnotationWriter(sb)
    override fun include(worldName: String) {
        sb.append("include ").append(worldName).append(";").appendLine()
    }

    private fun build(): WorldElementVisitor {
        val visitor = WorldElementWriter(sb)
        return object : WorldElementVisitor {
            override fun id(name: String) {
                visitor.id(name)
                sb.append(";").appendLine()
            }

            override fun func(name: String): FuncVisitor {
                val visitor = visitor.func(name)
                return object : FuncVisitor by visitor {
                    override fun end() {
                        visitor.end()
                        sb.append(";").appendLine()
                    }
                }
            }

            override fun externalInterface(
                packageModule: String,
                packageName: String,
                interfaceName: String,
                version: String?,
            ) {
                visitor.externalInterface(packageModule, packageName, interfaceName, version)
            }
        }
    }

    override fun lineComment(text: String) {
        sb.append("//").append(text).appendLine()
    }

    override fun end() {
        sb.levelDec().append("}").appendLine()
    }
}