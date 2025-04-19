package pw.binom.wit.node

import pw.binom.wit.visitors.PackageVisitor

data class PackageNode(var module: String, var field: String, var version: String?) : PackageVisitor {
    fun accept(visitor: PackageVisitor) {
        visitor.start()
        visitor.moduleName(module)
        visitor.fieldName(field)
        val version = version
        if (version != null) {
            visitor.version(version)
        }
        visitor.end()
    }

    override fun start() {
        version = null
    }

    override fun moduleName(value: String) {
        module = value
    }

    override fun fieldName(value: String) {
        field = value
    }

    override fun version(value: String) {
        version = value
    }

    override fun end() {
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(module).append(":").append(field)
        if (version != null) {
            sb.append("@").append(version)
        }
        return sb.toString()
    }
}