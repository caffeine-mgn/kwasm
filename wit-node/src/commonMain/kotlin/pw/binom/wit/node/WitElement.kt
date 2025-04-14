package pw.binom.wit.node

import pw.binom.wit.visitors.WitVisitor

sealed interface WitElement {
    fun accept(visitor: WitVisitor)
}