package pw.binom.wit.utils

class Cursor(val capacity: Int) {
    private val counter = CircularCounter(capacity)
    private var cursor = counter.headCursor
    fun push(): Int {
        check(counter.size < capacity || counter.tailCursor + 1 < cursor)
        return counter.push()
    }

    fun back() {
        check(cursor - 1 >= counter.tailCursor)
        cursor--
    }

    fun next(): Int {
        check(cursor != counter.headCursor)
        cursor++
        return cursor - 1
    }

    val current
        get() = cursor % capacity

    val hasNext: Boolean
        get() = cursor != counter.headCursor
}