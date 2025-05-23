package pw.binom.wasm

//sealed interface Types {
//  enum class Numers:Types {
//    I32,
//    I64,
//  }
//  object I32 : Types
//  object I64 : Types
//  class Ref(val type: TypeId) : Types
//}


/**
 * reftype
 *
 * https://www.w3.org/TR/wasm-core-2/#binary-reftype
 * https://webassembly.github.io/gc/core/binary/types.html#reference-types
 */

//fun StreamReader.readAbsHeapType(byte: UByte = readUByte()): UByte {
//  check(Types.isAbsHeapType(byte))
//  return byte
//}

/**
 * https://webassembly.github.io/gc/core/binary/types.html#binary-heaptype
 */
//fun StreamReader.readHeapType(
//  byte: UByte = readUByte(),
//  absHeadType: (UByte) -> Unit,
//  type: (TypeId) -> Unit,
//) {
//  val firstByte = byte
//  if (Types.isAbsHeapType(firstByte)) {
//    absHeadType(readAbsHeapType(firstByte))
//  } else {
//    type(TypeId(v33s(firstByte.toByte()).toUInt()))
//  }
//}

fun isValueType(byte: UByte) =
    when (byte) {
        Types.TYPE_NUM_I32,
        Types.TYPE_NUM_I64,
        Types.TYPE_NUM_F32,
        Types.TYPE_NUM_F64,
        Types.TYPE_VEC_V128,
        Types.TYPE_REF_ABS_HEAP_FUNC_REF,
        Types.TYPE_REF_EXTERN_REF,
        Types.TYPE_REF_NULL,
        Types.TYPE_REF,
        Types.TYPE_REF_ABS_HEAP_NONE,
        Types.TYPE_REF_ABS_HEAP_ANY,
        Types.TYPE_REF_ABS_HEAP_STRUCT,
            -> true

        else -> false
    }
