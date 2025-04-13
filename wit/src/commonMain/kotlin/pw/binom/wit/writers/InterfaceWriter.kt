package pw.binom.wit.writers

import pw.binom.wit.visitors.*

class InterfaceWriter(private val sb: TextWriter) : InterfaceVisitor {
    companion object {
        private const val NONE = 0
        private const val NAME = 1
        private const val ELEMENTS = 2
    }

    private var status = NONE
    override fun start(name: String) {
        check(status == NONE)
        status = NAME
        sb.append("interface ").append(name).append(" {").appendLine().levelInc()
    }

    override fun typeAlias(): TypeAliasVisitor {
        check(status == NAME || status == ELEMENTS)
        status = ELEMENTS
        val typeAliasWriter = TypeAliasWriter(sb)
        return object : TypeAliasVisitor by typeAliasWriter {
            override fun end() {
                typeAliasWriter.end()
                sb.append(";").appendLine()
            }
        }
    }

    override fun record(): RecordVisitor {
        check(status == NAME || status == ELEMENTS)
        status = ELEMENTS
        return RecordWriter(sb)
    }

    override fun use(): UseVisitor {
        check(status == NAME || status == ELEMENTS)
        status = ELEMENTS
        val visitor = UseWriter(sb)
        return object : UseVisitor by visitor {
            override fun end() {
                visitor.end()
                sb.append(";").appendLine()
            }
        }
    }

    override fun enum(): EnumVisitor {
        check(status == NAME || status == ELEMENTS)
        status = ELEMENTS
        return EnumWriter(sb)
    }

    override fun resource(): ResourceVisitor {
        check(status == NAME || status == ELEMENTS)
        status = ELEMENTS
        return ResourceWriter(sb)
    }

    override fun variant(): VariantVisitor {
        check(status == NAME || status == ELEMENTS)
        status = ELEMENTS
        return VariantWriter(sb)
    }

    override fun func(name: String): FuncVisitor {
        check(status == NAME || status == ELEMENTS)
        status = ELEMENTS
        sb.append(name).append(": func")
        val visitor = FuncWriter(sb)
        return object : FuncVisitor by visitor {
            override fun end() {
                visitor.end()
                sb.append(";").appendLine()
            }
        }
    }

    override fun end() {
        check(status == NAME || status == ELEMENTS)
        status = NONE
        sb.levelDec().append("}").appendLine()
    }
}