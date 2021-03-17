package testroutine

import kotlinx.coroutines.*

abstract class SomeLifecycle<T> {

    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    suspend fun execute() {

        try {
            onInitialize()

            val result = withContext(coroutineScope.coroutineContext) {
                onCalculate()
            }

            onCompleted(result)
        } catch (e: Exception) {
            if (e is CancellationException) {
                onCancellation(e)
            } else {
                onError(e)
            }
        }

    }

    abstract fun onInitialize();
    abstract suspend fun onCalculate(): T;
    abstract fun onCompleted(result: T);
    abstract fun onError(error: Exception);
    abstract fun onCancellation(error: CancellationException);

    fun cancel() {
        coroutineScope.cancel()
    }
}

fun main() {

    runBlocking {

        val myLifecycle: SomeLifecycle<Long> = object : SomeLifecycle<Long>() {

            override fun onInitialize() {
                println("[myLifecycle] Initializing...")
            }

            override suspend fun onCalculate(): Long {
                return coroutineWithResultWithClass(
                    listOf(
                        AddNumberJob(50000000000, "A"),
                        AddNumberJob(100000000, "B"),
                        AddNumberJob(400000000000, "C"),
                        AddNumberJob(2000000000, "D"),
                        AddNumberJob(30000000000, "E"),
                    )
                )
            }

            override fun onCompleted(result: Long) {
                println("[myLifecycle] Operation completed!")
            }

            override fun onError(error: Exception) {
                println("An error occurs...")
                error.printStackTrace()
                coroutineScope.cancel()
            }

            override fun onCancellation(error: CancellationException) {
                println("The job is cancelled.")
            }
        }

        launch {
            println("Executing the task...")
            myLifecycle.execute()
        }
        println("Starting to wait 5 seconds before a termination...")
        delay(5000)
        myLifecycle.cancel()
        println("Terminated the job!")

        println("Starting another task!")
        delay(50000)
        println("The other task has completed!")

    }
}