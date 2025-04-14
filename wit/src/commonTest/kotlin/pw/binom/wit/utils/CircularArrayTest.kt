package pw.binom.wit.utils

import kotlin.test.Test

class CircularArrayTest {
    val arr = IntArray(3)
    var head = 0
    var tail = 0
    var size = 0
    fun push(value: Int) {
        arr[head % arr.size] = value
        head++
        if (size == arr.size) {
            tail++
        }
        size = minOf(size + 1, arr.size)

    }

    fun pop():Int {
        if (head == tail) {
            throw NoSuchElementException()
        }
        head--
        return arr[head % arr.size]
    }

    @Test
    fun test() {

        push(1)
        push(2)
        push(3)
        push(4)
        println(pop())
        println(pop())
        println(pop())
        return
        val e = CircularArray<Int>(3)
        e.push(1)
        e.push(2)
        e.push(3)
        println()
        e.push(4)
        println()
        println(e.pop())
        println(e.pop())
        println(e.pop())
    }
}