package pw.binom.wit.writers

import pw.binom.wit.visitors.UseVisitor

class UseWriter(val sb: TextWriter) : UseVisitor {
    companion object {
        private const val NONE = 0
        private const val STARTED = 1
        private const val TYPES = 2
    }

    private var status = 0
    override fun start(name: String) {
        check(status == NONE)
        status = STARTED
        sb.append("use ").append(name).append(".{")
    }

    override fun type(name: String) {
        if (status == STARTED) {
            sb.append(name)
            status = TYPES
            return
        }
        check(status == TYPES)
        sb.append(", ").append(name)
    }

    override fun end() {
        check(status == STARTED || status == TYPES)
        status = NONE
        sb.append("}")
    }
}