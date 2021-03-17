package testroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun coroutineHelloWorldII() {
    GlobalScope.launch {
        delay(1000L)
        println("World!")
    }
    print("Hello! ")

    runBlocking {
        delay(2000L)
    }
}

fun main() {
    runBlocking {
        coroutineHelloWorldII()
    }
}