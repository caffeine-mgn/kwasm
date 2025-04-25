package pw.binom.wit

import kotlin.jvm.JvmInline

@JvmInline
value class Package private constructor(val raw: String) {
    constructor(namespace: String, packageName: String) : this("$namespace:$packageName") {
        require(namespace.isNotBlank()) { "namespace is empty" }
        require(packageName.isNotBlank()) { "package is empty" }
    }

//    constructor(namespace: String, packageName: String, version: String) : this("$namespace:$packageName@$version") {
//        require(namespace.isNotBlank()) { "namespace is empty" }
//        require(packageName.isNotBlank()) { "package is empty" }
//        require(version.isNotBlank()) { "version is empty" }
//    }

    constructor(namespace: String, packageName: String, version: String?) : this(
        "$namespace:$packageName${version?.let { "@$it" } ?: ""}"
    ) {
        require(namespace.isNotBlank()) { "namespace is empty" }
        require(packageName.isNotBlank()) { "package is empty" }
        require(version == null || version.isNotBlank()) { "version is empty" }
    }

    companion object {
        fun parse(data: String): Package {
            val s1 = data.indexOf('/')
            require(s1 > 0)
            val s2 = data.indexOf('@')
            require(s2 == -1 || s2 > s1)
            return Package(data)
        }
    }

    override fun toString(): String = raw

    val nameSpace
        get() = raw.substringBefore(':')

    val packageName: String
        get() {
            val i1 = raw.indexOf(':')
            val i2 = raw.indexOf('@')
            return if (i2 == -1) {
                raw.substring(i1 + 1)
            } else {
                raw.substring(i1 + 1, i2)
            }
        }
}