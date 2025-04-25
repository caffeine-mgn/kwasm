package pw.binom.wit

import pw.binom.wit.node.ConstructorNode
import pw.binom.wit.node.FuncNode
import pw.binom.wit.node.FunctionResult
import pw.binom.wit.node.Type

internal fun Type.toFinalType(scope: Scope): NonFinalType = when (this) {
    is Type.Borrow -> FinalType.Borrow(element.toFinalType(scope), scope)
    is Type.Id -> NonFinalType.ByName(name, scope)
    is Type.List -> FinalType.List2(this.element.toFinalType(scope), scope)
    is Type.Option -> FinalType.Option(this.element.toFinalType(scope), scope)
    Type.Primitive.BOOL -> FinalType.Primitive.BOOL
    Type.Primitive.CHAR -> FinalType.Primitive.CHAR
    Type.Primitive.F32 -> FinalType.Primitive.F32
    Type.Primitive.F64 -> FinalType.Primitive.F64
    Type.Primitive.S16 -> FinalType.Primitive.S16
    Type.Primitive.S32 -> FinalType.Primitive.S32
    Type.Primitive.S64 -> FinalType.Primitive.S64
    Type.Primitive.S8 -> FinalType.Primitive.S8
    Type.Primitive.STRING -> FinalType.Primitive.STRING
    Type.Primitive.U16 -> FinalType.Primitive.U16
    Type.Primitive.U32 -> FinalType.Primitive.U32
    Type.Primitive.U64 -> FinalType.Primitive.U64
    Type.Primitive.U8 -> FinalType.Primitive.U8
    is Type.Result -> FinalType.Result(first?.toFinalType(scope), second?.toFinalType(scope), scope)
    Type.Something -> TODO()
    is Type.Tuple -> FinalType.Tuple(elements.map { it.toFinalType(scope) }, scope)
}

internal fun FuncNode.toCallable(scope: Scope) = Callable.Function(
    args = this.args.map { it.first to it.second.toFinalType(scope) },
    result = when (val res = this.result) {
        is FunctionResult.Multiple -> Callable.FunctionResult.Multi(res.results.map {
            it.first to it.second.toFinalType(
                scope
            )
        })

        is FunctionResult.SingleResult -> Callable.FunctionResult.Single(res.type.toFinalType(scope))
        FunctionResult.VoidResult -> Callable.FunctionResult.Void
    },
)

internal fun ConstructorNode.toCallable(scope: Scope) = Callable.Constructor(
    args = this.args.map { it.first to it.second.toFinalType(scope) },
)