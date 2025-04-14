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

    override fun externalInterface(
        packageModule: String,
        packageName: String,
        interfaceName: String,
        version: String?,
    ) {
        sb.append(packageModule).append(":").append(packageName).append("/").append(interfaceName)
        if (version != null) {
            sb.append("@").append(version)
        }
        sb.append(";").appendLine()
    }
}