package pw.binom.wit.writers

import pw.binom.wit.visitors.VariantVisitor

class VariantWriter(private val sb: TextWriter) : VariantVisitor {
}