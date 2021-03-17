package testroutine

import heavyops.heavilyAddNumbers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

suspend fun coroutineWithResult(): Long {
    val x = GlobalScope.async {
        val x = async {
            println("x has started...")
            val result = heavilyAddNumbers(500000000)
            println("x has completed!")
            return@async result
        }
        val y = async {
            println("y has started...")
            val result = heavilyAddNumbers(20000000)
            println("y has completed!")
            return@async result
        }
        val result = x.await() + y.await()

        println("x + y = $result")
        result
    }
    return x.await()
}

fun main() {
    runBlocking {
        coroutineWithResult()
    }
}