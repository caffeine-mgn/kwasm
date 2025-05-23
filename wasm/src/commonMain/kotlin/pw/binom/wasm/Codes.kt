package pw.binom.wasm

object Codes {
    val codes = mapOf(
        Opcodes.GET_LOCAL to "get_local",
        Opcodes.GET_GLOBAL to "get_global",
        Opcodes.SET_LOCAL to "set_local",
        Opcodes.I32_CONST to "i32.const",
        Opcodes.I32_SUB to "i32.sub",
        Opcodes.I32_LOAD to "i32.load",
        Opcodes.I32_STORE to "i32.store",
        Opcodes.I32_GT_S to "i32.gt_s",
        Opcodes.SELECT to "select",
        Opcodes.I32_AND to "i32.and",
        Opcodes.RETURN to "return",
        Opcodes.END to "end",
        Opcodes.DROP to "drop",
        Opcodes.CALL to "call",
        Opcodes.CALL_REF to "call_ref",
        Opcodes.ELSE to "else",
        Opcodes.IF to "if",
        Opcodes.BR to "br",
        Opcodes.I32_EQZ to "i32.eqz",
        Opcodes.BLOCK to "block",
        Opcodes.REF_NULL to "ref.null",
        Opcodes.TEE_LOCAL to "tee_local",
        Opcodes.THROW to "throw",
        Opcodes.REF_IS_NULL to "ref.is_null",
        Opcodes.UNREACHABLE to "unreachable",
        Opcodes.I64_CONST to "i64.const",
        Opcodes.LOOP to "loop",
        Opcodes.REF_EQ to "ref.eq",
        Opcodes.MEMORY_SIZE to "memory.size",
        Opcodes.MEMORY_GROW to "memory.grow",
        Opcodes.TRY to "try",
        Opcodes.F64_LT to "f64.lt",
        Opcodes.CATCH to "catch",
        Opcodes.BR_TABLE to "br_table",
        Opcodes.F64_CONST to "f64.const",
        Opcodes.F32_SUB to "f32.sub",
        Opcodes.F32_ADD to "f32.add",
        Opcodes.F32_CONST to "f32.const",
        Opcodes.F32_MUL to "f32.mul",
        Opcodes.F32_DIV to "f32.div",
        Opcodes.I32_EQ to "i32.eq",
        Opcodes.I32_LT_S to "i32.lt_s",
        Opcodes.I32_LT_U to "i32.lt_u",
        Opcodes.SET_GLOBAL to "set_global",
        Opcodes.BR_IF to "br_if",
        Opcodes.I32_ADD to "i32.add",
        Opcodes.I32_SUB to "i32.sub",
        Opcodes.I32_GE_S to "i32.ge_s",
        Opcodes.I32_LE_U to "i32.le_u",
        Opcodes.I32_LE_S to "i32.le_s",
        Opcodes.I32_GT_U to "i32.gt_u",
        Opcodes.I64_EQ to "i64.eq",
        Opcodes.I64_NE to "i64.ne",
        Opcodes.I64_SUB to "i64.sub",
        Opcodes.I64_LE_S to "i64.le_s",
        Opcodes.I64_AND to "i64.and",
        Opcodes.I32_REM_S to "i32.rem_s",
        Opcodes.I32_SHR_S to "i32.shr_s",
        Opcodes.I32_MUL to "i32.mul",
        Opcodes.I32_OR to "i32.or",
        Opcodes.I32_XOR to "i32.xor",
        Opcodes.I64_GT_S to "i64.gt_s",
        Opcodes.I64_GE_S to "i64.ge_s",
        Opcodes.I64_LT_S to "i64.lt_s",
        Opcodes.I64_ADD to "i64.add",
        Opcodes.I64_REM_S to "i64.rem_s",
        Opcodes.I32_SHL to "i32.shl",
        Opcodes.I32_SHR_U to "i32.shr_u",
        Opcodes.I64_MUL to "i64.mul",
        Opcodes.I64_SHL to "i64.shl",
        Opcodes.I64_SHR_U to "i64.shr_u",
        Opcodes.F64_SUB to "f64.sub",
        Opcodes.F64_DIV to "f64.div",
        Opcodes.F64_MUL to "f64.mul",
        Opcodes.F64_ADD to "f64.add",
        Opcodes.F64_GE to "f64.ge",
        Opcodes.I32_CLZ to "i32.clz",
        Opcodes.F64_GT to "f64.gt",
        Opcodes.I64_XOR to "i64.xor",
        Opcodes.I64_OR to "i64.xor",
        Opcodes.I64_REM_U to "i64.rem_u",
        Opcodes.F32_GT to "f32.gt",
        Opcodes.F32_LT to "f32.lt",
        Opcodes.F32_TRUNC to "f32.trunc",
        Opcodes.F64_LE to "f64.le",
        Opcodes.I64_EXTEND_S_I32 to "i64.extend_s/i32",
        Opcodes.F32_CONVERT_S_I32 to "f32.convert_s/i32",
        Opcodes.F64_CONVERT_S_I32 to "f64.convert_s/i32",
        Opcodes.I32_DIV_S to "i32.div_s",
        Opcodes.I64_DIV_S to "i64.div_s",
        Opcodes.I64_SHR_S to "i64.shr_s",
        Opcodes.I32_WRAP_I64 to "i32.wrap/i64",
        Opcodes.F32_CONVERT_S_I64 to "f32.convert_s/i64",
        Opcodes.F64_CONVERT_S_I64 to "f64.convert_s/i64",
        Opcodes.I32_TRUNC_S_F32 to "i32.trunc_s/f32",
        Opcodes.F32_LOAD to "f32.load",
        Opcodes.F32_ABS to "f32.abs",
        Opcodes.F32_STORE to "f32.store",
        Opcodes.NOP to "nop",
        Opcodes.I64_EXTEND_U_I32 to "i64.extend_u/i32",
        Opcodes.I32_DIV_U to "i32.div_u",
        Opcodes.I32_REM_U to "i32.rem_u",
        Opcodes.I64_DIV_U to "i64.div_u",
        Opcodes.I64_GE_U to "i64.ge_u",
        Opcodes.I64_LE_U to "i64.le_u",
        Opcodes.I32_GE_U to "i32.ge_u",
        Opcodes.I32_STORE8 to "i32.store8",
        Opcodes.I64_LOAD to "i64.load",
        Opcodes.SELECT_WITH_TYPE to "select",
        Opcodes.CATCH_ALL to "catch_all",
        Opcodes.REF_AS_NON_NULL to "ref.as_non_null",
        Opcodes.I32_STORE16 to "i32.store16",
        Opcodes.RETHROW to "rethrow",
        Opcodes.F32_CONVERT_U_I32 to "f32.convert_u/i32",
        Opcodes.F64_CONVERT_U_I32 to "f64.convert_u/i32",
        Opcodes.F32_CONVERT_U_I64 to "f32.convert_u/i64",
        Opcodes.F64_CONVERT_U_I64 to "f64.convert_u/i64",
        Opcodes.F32_COPYSIGN to "f32.copysign",
        Opcodes.F64_PROMOTE_F32 to "f64.promote/f32",
        Opcodes.F64_TRUNC to "f64.trunc",
        Opcodes.F64_COPYSIGN to "f64.copysign",
        Opcodes.F32_DEMOTE_F64 to "f32.demote/f64",
        Opcodes.F64_EQ to "f64.eq",
        Opcodes.F64_NEG to "f64.neg",
        Opcodes.F32_EQ to "f32.eq",
        Opcodes.I64_CLZ to "i64.clz",
        Opcodes.F64_REINTERPRET_I64 to "f64.reinterpret/i64",
        Opcodes.I32_REINTERPRET_F32 to "i32.reinterpret/f32",
        Opcodes.I64_REINTERPRET_F64 to "i64.reinterpret/f64",
        Opcodes.I32_LOAD16_U to "i32.load16_u",
    )

    val gcCodes = mapOf(
        Opcodes.GC_STRUCT_NEW to "struct.new",
        Opcodes.GC_STRUCT_GET to "struct.get",
        Opcodes.GC_REF_CAST to "ref.cast",
        Opcodes.GC_STRUCT_SET to "struct.set",
        Opcodes.GC_REF_TEST to "ref.test",
        Opcodes.GC_REF_TEST_NULL to "ref.test null",
        Opcodes.GC_ARRAY_NEW_DEFAULT to "array.new_default",
        Opcodes.GC_ARRAY_GET to "array.get",
        Opcodes.GC_ARRAY_SET to "array.set",
        Opcodes.GC_ARRAY_GET_S to "array.get_s",
        Opcodes.GC_ARRAY_LEN to "array.len",
        Opcodes.GC_ARRAY_GET_U to "array.get_u",
        Opcodes.GC_ARRAY_COPY to "array.copy",
        Opcodes.GC_ARRAY_NEW_DATA to "array.new_data",
        Opcodes.GC_ARRAY_NEW_FIXED to "array.new_fixed",
        Opcodes.GC_STRUCT_GET_S to "struct.get_s",
        Opcodes.GC_STRUCT_GET_U to "struct.get_u",
        Opcodes.GC_REF_CAST_NULL to "ref.cast null",
        Opcodes.GC_BR_ON_CAST_FAIL to "br_on_cast_fail",
        Opcodes.GC_EXTERN_CONVERT_ANY to "extern.convert_any",
        Opcodes.GC_ANY_CONVERT_EXTERN to "any.convert_extern",
    )

    val numericCodes = mapOf(
        Opcodes.NUMERIC_I32S_CONVERT_SAT_F32 to "i32.trunc_sat_f32_s",
        Opcodes.NUMERIC_I32U_CONVERT_SAT_F32 to "i32.trunc_sat_f32_u",
        Opcodes.NUMERIC_I32S_CONVERT_SAT_F64 to "i32.trunc_sat_f64_s",
        Opcodes.NUMERIC_I32U_CONVERT_SAT_F64 to "i32.trunc_sat_f64_u",
        Opcodes.NUMERIC_I64S_CONVERT_SAT_F32 to "i64.trunc_sat_f32_s",
        Opcodes.NUMERIC_I64U_CONVERT_SAT_F32 to "i64.trunc_sat_f32_u",
        Opcodes.NUMERIC_I64S_CONVERT_SAT_F64 to "i64.trunc_sat_f64_s",
        Opcodes.NUMERIC_I64U_CONVERT_SAT_F64 to "i64.trunc_sat_f64_u",
    )
}
