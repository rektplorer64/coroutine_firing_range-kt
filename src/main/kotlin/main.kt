import testroutine.coroutineHelloWorld
import testroutine.coroutineHelloWorldII
import testroutine.coroutineWithResult
import kotlinx.coroutines.*


fun main(args: Array<String>) {
    println("Started Running...")

//    coroutine.blockingRoutineExample()
    coroutineHelloWorld()

    coroutineHelloWorldII()

    runBlocking {
        val result = coroutineWithResult()
        println("Main gets $result")
    }
}
