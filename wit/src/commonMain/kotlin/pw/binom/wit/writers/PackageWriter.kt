package pw.binom.wit.writers

import pw.binom.wit.visitors.PackageVisitor

class PackageWriter(private val sb: TextWriter) : PackageVisitor {
    companion object {
        private const val STATUS_NONE = 0
        private const val STATUS_STARTED = 1
        private const val STATUS_MODULE = 2
        private const val STATUS_FIELD = 3
        private const val STATUS_VERSION = 4
    }

    private var status = STATUS_NONE
    override fun start() {
        require(status == STATUS_NONE)
        status = STATUS_STARTED
        sb.append("package ")
    }

    override fun moduleName(value: String) {
        require(status == STATUS_STARTED)
        status = STATUS_MODULE
        sb.append(value)
    }

    override fun fieldName(value: String) {
        require(status == STATUS_MODULE)
        status = STATUS_FIELD
        sb.append(":").append(value)
    }

    override fun version(value: String) {
        require(status == STATUS_FIELD)
        status = STATUS_VERSION
        sb.append("@").append(value)
    }

    override fun end() {
        require(status == STATUS_FIELD || status == STATUS_VERSION)
        status = STATUS_NONE
        sb.append(";").appendLine()
    }
}