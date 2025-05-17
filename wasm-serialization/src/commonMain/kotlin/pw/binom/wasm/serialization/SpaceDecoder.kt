package pw.binom.wasm.serialization

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.modules.SerializersModule

class SpaceDecoder(
    override val serializersModule: SerializersModule,
    val space: MemorySpace,
) : CompositeDecoder, Decoder {
    override fun decodeBooleanElement(descriptor: SerialDescriptor, index: Int): Boolean =
        decodeBoolean()

    override fun decodeByteElement(descriptor: SerialDescriptor, index: Int): Byte =
        decodeByte()

    override fun decodeCharElement(descriptor: SerialDescriptor, index: Int): Char =
        decodeChar()

    override fun decodeDoubleElement(descriptor: SerialDescriptor, index: Int): Double =
        decodeDouble()

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        TODO("Not yet implemented")
    }

    override fun decodeFloatElement(descriptor: SerialDescriptor, index: Int): Float =
        decodeFloat()

    override fun decodeInlineElement(descriptor: SerialDescriptor, index: Int): Decoder =
        this

    override fun decodeIntElement(descriptor: SerialDescriptor, index: Int): Int =
        decodeInt()

    override fun decodeLongElement(descriptor: SerialDescriptor, index: Int): Long =
        decodeLong()

    @ExperimentalSerializationApi
    override fun <T : Any> decodeNullableSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        deserializer: DeserializationStrategy<T?>,
        previousValue: T?,
    ): T? {
        TODO("Not yet implemented")
    }

    override fun <T> decodeSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        deserializer: DeserializationStrategy<T>,
        previousValue: T?,
    ): T = deserializer.deserialize(this)

    override fun decodeShortElement(descriptor: SerialDescriptor, index: Int): Short =
        decodeShort()

    override fun decodeStringElement(descriptor: SerialDescriptor, index: Int): String =
        decodeString()

    override fun endStructure(descriptor: SerialDescriptor) {
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder = this

    override fun decodeBoolean(): Boolean = space.decodeBoolean()

    override fun decodeByte(): Byte = space.decodeByte()

    override fun decodeChar(): Char = space.decodeChar()

    override fun decodeDouble(): Double = space.decodeDouble()

    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int = decodeInt()

    override fun decodeFloat(): Float = space.decodeFloat()

    override fun decodeInline(descriptor: SerialDescriptor): Decoder = this

    override fun decodeInt(): Int = space.decodeInt()

    override fun decodeLong(): Long = space.decodeLong()

    @ExperimentalSerializationApi
    override fun decodeNotNullMark(): Boolean = space.decodeBoolean()

    @ExperimentalSerializationApi
    override fun decodeNull(): Nothing? {
        space.decodeBoolean()
        return null
    }

    override fun decodeShort(): Short = space.decodeShort()

    override fun decodeString(): String = space.decodeString()


}