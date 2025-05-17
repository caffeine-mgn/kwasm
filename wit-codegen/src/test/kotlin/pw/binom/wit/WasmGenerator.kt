package pw.binom.wit

import pw.binom.wit.writers.PrettyTextWriter

val FinalType.Record.kotlinName
    get() = kotlinClassName(name).replace('-', '_')

val FinalType.Resource.kotlinName
    get() = kotlinClassName(name).replace('-', '_')

fun kotlinClassName(wasiName: String): String {
    val sb = StringBuilder()
    var upper = true
    wasiName.forEach { char ->
        if (char == '-') {
            upper = true
            return@forEach
        }
        if (upper) {
            upper = false
            sb.append(char.uppercaseChar())
        } else {
            sb.append(char)
        }
    }
    return sb.toString()
}

class WasmGenerator(val sb: Appendable, val wordSize: WordSize) {
    enum class WordSize(val pointerSize: Int) {
        X32(4),
        X64(8),
    }

    val e = PrettyTextWriter(sb)

    val FinalType.sizeInBytes: Int
        get() = when (this) {
            is FinalType.Borrow -> TODO()
            is FinalType.Enum -> TODO()
            is FinalType.Flags -> TODO()
            is FinalType.List2 -> Int.SIZE_BYTES + wordSize.pointerSize
            is FinalType.Option -> 1 + type.resolve().sizeInBytes
            FinalType.Primitive.BOOL -> Byte.SIZE_BYTES
            FinalType.Primitive.CHAR -> Int.SIZE_BYTES
            FinalType.Primitive.U8,
            FinalType.Primitive.S8,
                -> Byte.SIZE_BYTES

            FinalType.Primitive.U16,
            FinalType.Primitive.S16,
                -> Short.SIZE_BYTES

            FinalType.Primitive.U32,
            FinalType.Primitive.S32,
                -> Int.SIZE_BYTES

            FinalType.Primitive.U64,
            FinalType.Primitive.S64,
                -> Long.SIZE_BYTES

            FinalType.Primitive.F32 -> Int.SIZE_BYTES
            FinalType.Primitive.F64 -> Long.SIZE_BYTES

            FinalType.Primitive.STRING -> Int.SIZE_BYTES + wordSize.pointerSize
            is FinalType.Record -> fields.sumOf { it.type.resolve().sizeInBytes }
            is FinalType.Resource -> wordSize.pointerSize
            is FinalType.Result -> TODO()
            is FinalType.Tuple -> types.sumOf { it.resolve().sizeInBytes }
            is FinalType.Variant -> TODO()
        }

    fun put(ptrName: String, type: FinalType, variableName: String): String {
        when (type) {
            is FinalType.Borrow -> TODO()
            is FinalType.Enum -> TODO()
            is FinalType.Flags -> TODO()
            is FinalType.List2 -> TODO()
            is FinalType.Option -> TODO()
            FinalType.Primitive.BOOL -> TODO()
            FinalType.Primitive.CHAR -> TODO()
            FinalType.Primitive.F32 -> TODO()
            FinalType.Primitive.F64 -> TODO()
            FinalType.Primitive.S16 -> TODO()
            FinalType.Primitive.S32 -> TODO()
            FinalType.Primitive.S64 -> TODO()
            FinalType.Primitive.S8 -> TODO()
            FinalType.Primitive.STRING -> TODO()
            FinalType.Primitive.U16 -> TODO()
            FinalType.Primitive.U32 -> TODO()
            FinalType.Primitive.U64 -> TODO()
            FinalType.Primitive.U8 -> TODO()
            is FinalType.Record -> TODO()
            is FinalType.Resource -> TODO()
            is FinalType.Result -> TODO()
            is FinalType.Tuple -> TODO()
            is FinalType.Variant -> TODO()
        }
    }

    fun load(ptrName: String, t: FinalType) {
        val jj = when (t) {
            is FinalType.Borrow -> TODO()
            is FinalType.Enum -> TODO()
            is FinalType.Flags -> TODO()
            is FinalType.List2 -> TODO()
            is FinalType.Option -> {
                e.append("if ($ptrName.loadByte() > 0) ")
                load(ptrName, t.type.resolve())
                e.append(" else null")
            }

            FinalType.Primitive.BOOL -> e.append("$ptrName.loadByte() > 0")
            FinalType.Primitive.CHAR -> e.append("$ptrName.loadInt().toChar()")
            FinalType.Primitive.F32 -> TODO()
            FinalType.Primitive.F64 -> TODO()
            FinalType.Primitive.S16 -> e.append("$ptrName.loadInt().toShort()")
            FinalType.Primitive.S32 -> TODO()
            FinalType.Primitive.S64 -> TODO()
            FinalType.Primitive.S8 -> TODO()
            FinalType.Primitive.STRING -> e.append("$ptrName.loadWitString()")
            FinalType.Primitive.U16 -> e.append("$ptrName.loadInt().toUShort()")
            FinalType.Primitive.U32 -> e.append("$ptrName.loadInt().toUInt()")
            FinalType.Primitive.U64 -> e.append("$ptrName.loadLong().toULong()")
            FinalType.Primitive.U8 -> e.append("$ptrName.loadByte().toUByte()")
            is FinalType.Record -> e.append("${t.kotlinName}.read($ptrName)")
            is FinalType.Resource -> TODO()
            is FinalType.Result -> TODO()
            is FinalType.Tuple -> TODO()
            is FinalType.Variant -> TODO()
        }
    }

    fun gen(t: FinalType) {
        val ee = when (t) {
            is FinalType.Borrow -> TODO()
            is FinalType.Enum -> TODO()
            is FinalType.Flags -> TODO()
            is FinalType.List2 -> TODO()
            is FinalType.Option -> {
                gen(t.type.resolve())
                e.append("?")
            }

            FinalType.Primitive.BOOL -> e.append("Boolean")
            FinalType.Primitive.CHAR -> e.append("Char")
            FinalType.Primitive.F32 -> e.append("Float")
            FinalType.Primitive.F64 -> e.append("Double")
            FinalType.Primitive.S16 -> e.append("Short")
            FinalType.Primitive.S32 -> e.append("Int")
            FinalType.Primitive.S64 -> e.append("Long")
            FinalType.Primitive.S8 -> e.append("Byte")
            FinalType.Primitive.STRING -> e.append("String")
            FinalType.Primitive.U16 -> e.append("UShort")
            FinalType.Primitive.U32 -> e.append("UInt")
            FinalType.Primitive.U64 -> e.append("ULong")
            FinalType.Primitive.U8 -> e.append("UByte")
            is FinalType.Record -> e.append(t.kotlinName)
            is FinalType.Resource -> TODO()
            is FinalType.Result -> TODO()
            is FinalType.Tuple -> TODO()
            is FinalType.Variant -> TODO()
        }
    }

    val FinalType.Record.funcReadName
        get() = "read${kotlinName}"

    val FinalType.Record.funcWriteName
        get() = "write${kotlinName}"

    fun generateRead(k: FinalType.Record) {
        val ptrName = "pointer"
        e.append("fun ${k.funcReadName}($ptrName: Pointer): ${k.kotlinName} =").levelInc().appendLine()
        var offset = 0
        e.append(k.kotlinName).append("(").appendLine().levelInc()
        k.fields.forEach { (name, type) ->
            e.append("$name = ")
            load("($ptrName + $offset)", type.resolve())
            e.append(",").appendLine()
            offset += type.resolve().sizeInBytes
        }
        e.levelDec().append(")").appendLine()
        e.levelDec()
    }

    val String.ptrVariable: String
        get() = "${this}_ptr"

    val String.bytesVariable: String
        get() = "${this}_bytes"

    fun genWrite(k: FinalType.Record) {
        e.append("internal fun ${k.funcWriteName}(value:${k.kotlinName}, allocator:Allocator): Pointer {").appendLine()
            .levelInc()
        e.append("var cursor = pointer").appendLine()

        val stringFields = k.eachFields().filter { it.second is FinalType.Primitive.STRING }.map { it.first }.toList()
        val optionStringFields = k.eachFields().filter { it.second is FinalType.Option }.map { it.first }.toList()

        stringFields.forEach { name ->
            val varName = name.replace('.', '_')
            e.append("val ${varName.bytesVariable} = value.$name.encodeToByteArray()").appendLine()
        }
        val strings = stringFields.joinToString(" + ") { name ->
            val varName = name.replace('.', '_')
            "${varName.bytesVariable}.size"
        }.takeIf { it.isNotEmpty() } ?: "0"
        e.append("val fullSize = $strings + ${k.sizeInBytes}").appendLine()

        stringFields.forEach { name ->
            val varName = name.replace('.', '_')
            e.append("val ${varName.ptrVariable} = cursor").appendLine()
            e.append("cursor.storeBytes(${varName.bytesVariable})").appendLine()
            e.append("cursor += ${varName.bytesVariable}.size").appendLine()
        }
        e.levelDec().append("}").appendLine()
    }

    fun gen(k: FinalType.Record) {
        e.append("data class ${k.kotlinName} (").appendLine()
            .levelInc()
        k.fields.forEach { (name, type) ->
            e.append("$name: ")
            gen(type.resolve())
            e.append(",")
            e.appendLine()
        }
        e.levelDec().append(") {").levelInc().appendLine()
        e.append("companion object {").appendLine().levelInc()
        e.append("val STATIC_SIZE_BYTES = ${k.sizeInBytes}").appendLine()

        e.levelDec()
        e.levelDec().append("}").appendLine()
        e.append("write(pointer: Pointer) {").levelInc().appendLine()

        e.levelDec().append("}").appendLine()

        e.append("}").appendLine()
        generateRead(k)
        genWrite(k)
    }

    fun gen(k: Resolver.Result) {
        val records = k.types.filterIsInstance<FinalType.Record>()
        records.forEach {
            gen(it)
            println("->$it")
        }
//        k.types.forEach { type ->
//            when (type) {
//                is FinalType.Enum -> {}
//                is FinalType.Flags -> {}
//                is FinalType.Record -> {}
//                is FinalType.Resource -> gen(type)
//                is FinalType.Variant -> {}
//            }
//        }
    }

    fun gen(r: FinalType.Resource) {
        r.functions.forEach { (name, function) ->
            val methodKotlinName = name.replace('-', '_')
            sb.appendLine("@WasmImport(module = \"${r.pack.nameSpace}:${r.pack.packageName}/${r.interfaceName}\", name = \"[method]${r.name}.$methodKotlinName\")")
            sb.append("fun __${r.fullName}_method_${r.kotlinName}_$methodKotlinName(")
            sb.appendLine(")")
        }
        sb.appendLine("@WasmImport(module = \"${r.pack.nameSpace}:${r.pack.packageName}/${r.interfaceName}\", name = \"[resource-drop]${r.name}\")")
        sb.appendLine("external fun ${r.destructorName}(handle: Int)")
    }

//    val Struct.kotlinName
//        get() = name.replace('-', '_')

    val FinalType.Resource.fullName
        get() = "${pack.nameSpace}_${pack.packageName}_${name}".replace('-', '_')

    val FinalType.Resource.destructorName
        get() = "__${fullName}_drop"
}