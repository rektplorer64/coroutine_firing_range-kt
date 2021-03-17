package testroutine

import heavyops.heavilyAddNumbers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class AddNumberJob (
    val limit: Long,
    val name: String
)

suspend fun coroutineWithResultWithClass(jobs: List<AddNumberJob>): Long {
    val x = GlobalScope.async {

        val results = jobs.map {
            async {

                println("${it.name} has started...")
                var result: Long = 0
                val duration = measureTimeMillis {
                    result = heavilyAddNumbers(it.limit)
                }

                println("After $duration ms,\t\t${it.name} has completed!\t=>\tThe result is =\t$result")
                return@async result
            }
        }

        val summation = results.awaitAll().reduce{
            acc, l -> acc + l
        }

        println("The summation of ${jobs.size} jobs is $summation")
        summation
    }
    return x.await()
}

fun main() {
    runBlocking {
        coroutineWithResultWithClass(listOf(
            AddNumberJob(50000000000, "A"),
            AddNumberJob(100000000, "B"),
            AddNumberJob(400000000000, "C"),
            AddNumberJob(2000000000, "D"),
            AddNumberJob(30000000000, "E"),
        ))
    }
}
