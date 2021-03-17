package heavyops

fun heavilyAddNumbers(limit: Long = 100000000000): Long {

    var sum = 0L
    for (i in 1 until limit) {
        sum += i
    }

    return sum
}