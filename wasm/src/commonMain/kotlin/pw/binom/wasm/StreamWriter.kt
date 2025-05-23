package pw.binom.wasm

import pw.binom.*
import pw.binom.collections.LinkedList
import pw.binom.io.*

val Output.asWasm
    get() = StreamWriter(this)

class StreamWriter(val out: Output) : WasmOutput {
    private var msg: String? = null

    private val buffer = ByteBuffer(16)

    private inline fun <T> recording(msg: String? = null, a: () -> T): T {
            return a()
    }

    override fun write(data: ByteBuffer): DataTransferSize {
        if (data.hasRemaining) {
        }
        val e = out.write(data)
        return e
    }

    override fun flush() = out.flush()

    override fun bytes(data: ByteArray) {
        recording {
            writeByteArray(data, buffer)
        }
    }

    override fun bytes(data: ByteBuffer) {
        recording("bytes:ByteBuffer size=${data.remaining}") {
            write(data)
        }
    }

    override fun bytes(data: Input) {
        recording("bytes:Input") {
            data.copyTo(this)
        }
    }

    fun write(byte: UByte) = recording("ubyte $byte") { write(byte.toByte()) }
    fun write(byte: Byte) {
        recording("byte $byte") {
            buffer.reset(0, 1)
            buffer[0] = byte
            writeFully(buffer)
        }
    }

    override fun i8s(value: Byte) {
        recording("i8s $value") {
            buffer.reset(0, 1)
            buffer[0] = value
            writeFully(buffer)
        }
    }

    override fun i8u(value: UByte) = recording("i8u $value") { i8s(value.toByte()) }

    override fun v64u(value: ULong) {
        recording("v64u $value") {
            buffer.clear()
            Leb.writeUnsignedLeb128(value = value) { byte ->
                buffer.put(byte)
            }
            buffer.flip()
            writeFully(buffer)
        }
    }

    override fun v64s(value: Long) {
        recording("v64s $value") {
            buffer.clear()
            Leb.writeSignedLeb128(value = value) { byte ->
                buffer.put(byte)
            }
            buffer.flip()
            writeFully(buffer)
        }
    }

    override fun i64s(value: Long) {
        recording("i64s $value") {
            writeLong(buffer, value.reverse())
        }
    }

    override fun i32s(value: Int) {
        recording("i32s $value") {
            writeInt(buffer, value.reverse())
        }
    }

    override fun i16s(value: Short) {
        recording("i16s $value") {
            writeShort(buffer, value.reverse())
        }
    }

    override fun v32u(value: UInt) {
        recording("v32u $value") {
            buffer.clear()
            WasmIO.v32u(value = value) { byte -> buffer.put(byte) }
            buffer.flip()
            writeFully(buffer)
        }
    }

    override fun v32s(value: Int) {
        recording("v32s $value") {
            buffer.clear()
            WasmIO.v32s(value = value) { byte -> buffer.put(byte) }
            buffer.flip()
            writeFully(buffer)
        }
    }

    fun v33u(value: ULong) {
        recording("v33u $value") {
            buffer.clear()
            Leb.writeUnsignedLeb128(value = value) { byte ->
                buffer.put(byte)
            }
            buffer.flip()
            writeFully(buffer)
        }
    }

    override fun v33s(value: Long) {
        recording("v33u $value") {
            buffer.clear()
            Leb.writeSignedLeb128(value = value) { byte ->
                buffer.put(byte)
            }
            buffer.flip()
            writeFully(buffer)
        }
    }

    override fun string(value: String) {
        val data = value.encodeToByteArray()
        v32u(data.size.toUInt())
        bytes(data)
    }

    override fun close() {
        try {
            out.close()
        } finally {
            buffer.close()
        }
    }

    override fun v1u(b: Boolean) {
        recording("v1u $b") {
            write(if (b) 1 else 0)
        }
    }


    private class VectorWriterImpl(val main: WasmOutput) : VectorWriter {

        private var vectorCount = 0
        private val vectorData = ByteArrayOutput()
        private val vectorStream = StreamWriter(ByteArrayOutput())

        override fun element(value: (WasmOutput) -> Unit) {
            vectorCount++
            value(vectorStream)
        }

        override fun close() {
            main.v32u(vectorCount.toUInt())
            vectorData.locked {
                main.write(it)
            }
            vectorData.close()
        }
    }

    override fun vec(): VectorWriter = VectorWriterImpl(this)
}
