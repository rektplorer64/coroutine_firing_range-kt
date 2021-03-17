package testroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun coroutineHelloWorld() {
    GlobalScope.launch {
        delay(1000L)
        println("World!")
    }
    print("Hello! ")
    Thread.sleep(2000L)
}

fun main() {
    runBlocking {
        coroutineHelloWorld()
    }
}