package pw.binom.wit.utils

class CircularArray<T : Any>(size: Int) : Circular() {
    private val array = arrayOfNulls<Any>(size)

    override val capacity: Int
        get() = array.size

    fun push(value: T) {
        array[calcPush()] = value
    }


    fun pop(): T = array[calcPop()] as T
}