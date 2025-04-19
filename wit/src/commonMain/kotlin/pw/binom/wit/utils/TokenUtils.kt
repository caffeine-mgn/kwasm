package pw.binom.wit.utils

import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.TokenType
import kotlin.jvm.JvmInline

fun BufferedTokenizer.readVersion(): String {
    nextNotSpaceOrEof()
    val sb = StringBuilder()
    while (true) {
        assertType(TokenType.DIGITAL)
        nextNotSpaceOrEof()
        if (type != TokenType.DOT) {
            pushBackCurrentToken()
            break
        }
        sb.append(".")
        nextNotSpaceOrEof()
    }
    return sb.toString()
}

@JvmInline
value class CheckResult<T>(@PublishedApi internal val obj: Any?) {
    companion object {
        inline fun <T> success(value: T) = CheckResult<T>(value)
        inline fun success() = CheckResult<Unit>(Unit)
        inline fun <T> fail() = CheckResult<T>(FAIL)
    }

    @PublishedApi
    internal object FAIL

    inline val isFail: Boolean
        get() = obj === FAIL

    inline val isSuccess: Boolean
        get() = obj !== FAIL

    inline fun getOrThrow(): T {
        check(!isFail) { "Token result is fail" }
        return obj as T
    }
}

fun interface TokenRule {
    fun check(tokenizer: TokenType, text: String): Boolean
}

@JvmInline
value class TokensRule(val list: List<TokenRule>) {
    companion object {
        fun build(func: TokenRuleContext.() -> Unit): TokensRule {
            val l = ArrayList<TokenRule>()
            TokenRuleContext(l)
            l.trimToSize()
            return TokensRule(l)
        }
    }
}

class TokenRuleContext(private val list: MutableList<TokenRule>) {
    fun add(func: TokenRule) {
        list += func
    }

    fun type(type: TokenType) {
        add { argType, _ ->
            argType == type
        }
    }
}

@JvmInline
value class RulesCheckResult(private val list: List<Any?>?) {
    companion object {
        val FAIL = RulesCheckResult(null)
    }

    val isSuccess: Boolean
        get() = list != null
    val isFail: Boolean
        get() = list == null
}

inline fun BufferedTokenizer.check(rule: TokensRule, func: () -> Unit): Boolean {
    var counter = 0
    rule.list.forEach {
        val checkResult = it.check(tokenizer = type, text = text)
        if (!checkResult) {
            repeat(counter) {
                pushBackCurrentToken()
            }
            return false
        }
        counter++
    }
    func()
    return true
}