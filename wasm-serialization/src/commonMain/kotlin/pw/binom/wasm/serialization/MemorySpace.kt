package pw.binom.wasm.serialization

interface MemorySpace {
    fun encodeBoolean(value: Boolean) {
        encodeByte(if (value) 1 else 0)
    }

    fun encodeByte(value: Byte)
    fun encodeInt(value: Int)
    fun encodeShort(value: Short)
    fun encodeLong(value: Long)
    fun encodeChar(value: Char) {
        encodeInt(value.code)
    }

    fun encodeDouble(value: Double) {
        encodeLong(value.toBits())
    }

    fun encodeString(value: String) {
        value.forEach {
            encodeChar(it)
        }
    }

    fun encodeFloat(value: Float) {
        encodeInt(value.toBits())
    }

    fun decodeShort(): Short
    fun decodeBoolean(): Boolean
    fun decodeInt(): Int
    fun decodeLong(): Long
    fun decodeDouble(): Double
    fun decodeFloat(): Float
    fun decodeString(): String
    fun decodeChar(): Char
    fun decodeByte(): Byte
}