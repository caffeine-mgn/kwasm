package pw.binom.wit.writers

import pw.binom.wit.visitors.FuncVisitor
import pw.binom.wit.visitors.WorldElementVisitor

class WorldElementWriter(private val sb: TextWriter) : WorldElementVisitor {
    override fun id(name: String) {
        sb.append(name)
    }

    override fun func(name: String): FuncVisitor {
        sb.append(name).append(": func")
        return FuncWriter(sb)
    }
}