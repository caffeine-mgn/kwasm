package pw.binom.wasm.wasi.config

import pw.binom.wasm.runner.Context
import pw.binom.wasm.runner.ExecuteContext
import pw.binom.wasm.runner.ImportResolver
import pw.binom.wasm.runner.RType

class ConfigModule : ImportResolver {

    override fun func(module: String, field: String, type: RType.Function): ((ExecuteContext) -> Unit)? {
        when {
            module == "wasi:config/store@0.2.0-draft" && field == "get" -> get
            module == "wasi:config/store@0.2.0-draft" && field == "get-all" -> getAll
        }
        return super.func(module, field, type)
    }

    private val get: (ExecuteContext) -> Unit = {

    }

    private val getAll: (ExecuteContext) -> Unit = {

    }
}