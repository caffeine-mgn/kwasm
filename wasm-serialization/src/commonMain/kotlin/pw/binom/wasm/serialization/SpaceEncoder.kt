package pw.binom.wasm.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule

class SpaceEncoder(
    override val serializersModule: SerializersModule,
    val space: MemorySpace,
) : CompositeEncoder, Encoder {

    override fun encodeBooleanElement(descriptor: SerialDescriptor, index: Int, value: Boolean) {
        encodeBoolean(value)
    }

    override fun encodeByteElement(descriptor: SerialDescriptor, index: Int, value: Byte) {
        encodeByte(value)
    }

    override fun encodeCharElement(descriptor: SerialDescriptor, index: Int, value: Char) {
        encodeChar(value)
    }

    override fun encodeDoubleElement(descriptor: SerialDescriptor, index: Int, value: Double) {
        encodeDouble(value)
    }

    override fun encodeFloatElement(descriptor: SerialDescriptor, index: Int, value: Float) {
        encodeFloat(value)
    }

    override fun encodeInlineElement(descriptor: SerialDescriptor, index: Int): Encoder = this

    override fun encodeIntElement(descriptor: SerialDescriptor, index: Int, value: Int) {
        encodeInt(value)
    }

    override fun encodeLongElement(descriptor: SerialDescriptor, index: Int, value: Long) {
        encodeLong(value)
    }

    @ExperimentalSerializationApi
    override fun <T : Any> encodeNullableSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        serializer: SerializationStrategy<T>,
        value: T?,
    ) {
        if (value == null) {
            encodeBoolean(false)
        } else {
            encodeBoolean(true)
            serializer.serialize(this, value)
        }
    }

    override fun <T> encodeSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        serializer: SerializationStrategy<T>,
        value: T,
    ) {
        serializer.serialize(this, value)
    }

    override fun encodeShortElement(descriptor: SerialDescriptor, index: Int, value: Short) {
        space.encodeShort(value)
    }

    override fun encodeStringElement(descriptor: SerialDescriptor, index: Int, value: String) {
        space.encodeString(value)
    }

    override fun endStructure(descriptor: SerialDescriptor) {
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder = this

    override fun encodeBoolean(value: Boolean) {
        space.encodeBoolean(value)
    }

    override fun encodeByte(value: Byte) {
        space.encodeByte(value)
    }

    override fun encodeChar(value: Char) {
        space.encodeChar(value)
    }

    override fun encodeDouble(value: Double) {
        space.encodeDouble(value)
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        encodeInt(index)
    }

    override fun encodeFloat(value: Float) {
        space.encodeFloat(value)
    }

    override fun encodeInline(descriptor: SerialDescriptor): Encoder = this

    override fun encodeInt(value: Int) {
        space.encodeInt(value)
    }

    override fun encodeLong(value: Long) {
        space.encodeLong(value)
    }

    @ExperimentalSerializationApi
    override fun encodeNull() {
        encodeBoolean(false)
    }

    override fun encodeShort(value: Short) {
        space.encodeShort(value)
    }

    override fun encodeString(value: String) {
        space.encodeString(value)
    }
}