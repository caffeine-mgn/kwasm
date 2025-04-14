package pw.binom.wit.node

import pw.binom.wit.visitors.InterfaceVisitor

sealed interface InterfaceElement {
    fun accept(visitor: InterfaceVisitor)
}