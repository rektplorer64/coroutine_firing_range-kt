package testroutine

import heavyops.heavilyAddNumbers
import kotlinx.coroutines.runBlocking

fun blockingRoutineExample() {
    println("Starting blockingRoutineExample")

    println(heavilyAddNumbers())

    for (i in 0..10){
        println(i)
    }
}

fun main() {
    blockingRoutineExample()
}