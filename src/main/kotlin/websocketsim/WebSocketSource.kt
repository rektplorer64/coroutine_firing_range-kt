package websocketsim

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Instant
import java.util.concurrent.TimeUnit

private const val TOTAL_WEBSOCKET_EVENTS = 20L

private class WebSocketSource {

    private val _socketStream = MutableStateFlow<String?>(null)
    val socketStream = _socketStream.asStateFlow().filterNotNull()


    init {
        Observable
            .intervalRange(0, TOTAL_WEBSOCKET_EVENTS, 530, 1000, TimeUnit.MILLISECONDS)
            .subscribe {
                val incomingMessage = "message_${it}_${Instant.now().toEpochMilli().toString().padStart(15, '0')}"
                println("\n*** Incoming WebSocket Message: \"$incomingMessage\"")
                _socketStream.value = incomingMessage
            }
    }
}

private class DataStorage {

    private val _dataStream = MutableStateFlow<ArrayList<String>?>(null)
    val dataStream = _dataStream.asStateFlow().filterNotNull()

    fun insertOne(string: String) {
        val newInstance = ArrayList(_dataStream.value ?: arrayListOf())
        newInstance.add(string)
        _dataStream.value = newInstance
    }
}

fun main(args: Array<String>): Unit = runBlocking {

    println("Starting...")

    val dataStorage = DataStorage()
    launch(Dispatchers.IO) {
        println("\n\nCollecting data storage stream...")
        var count = 0
        dataStorage.dataStream.collect {
            println(
                "\tInsertion Result ${(++count).toString().padStart(2, '0')}/$TOTAL_WEBSOCKET_EVENTS:\t\"${it.lastOrNull()}\""
            )
        }
    }

    val webSocketSource = WebSocketSource()
    launch(Dispatchers.IO) {
        println("\n\nCollecting socket stream...")
        webSocketSource.socketStream.collect {
            println("\tInserting:\t\t\t\t\"$it\"")
            dataStorage.insertOne(it)
        }
    }

}
