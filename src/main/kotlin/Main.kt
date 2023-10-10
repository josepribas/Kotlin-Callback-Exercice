import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {

    println("COROUTINE TEST")
    println("Start")

    //val data = fetchData()
    //println("Data fetched: $data")

    val fetchDataJob = async {
        val data = fetchData()
        println("Data fetched: $data")
        data
    }

//    val processedData = processData(data)
//    println("Processed data: $processedData")

    // Launch processData coroutine concurrently and wait for fetchDataJob to complete
    val processDataJob = async {
        fetchDataJob.join() // Wait for fetchDataJob to complete
        val data = fetchDataJob.await() // Fetch the result from fetchDataJob
        val processedData = processData(data)
        println("Processed data: $processedData")
        //println("Processed data")
    }
    // Delay to simulate other tasks being executed
    delay(2000)

    println("End")

    println("CALLBACK TEST")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val process = async {
        loadDataFromServer { data ->
            println("Loaded data: $data")
        }
    }

    println("Loading data...")
    Thread.sleep(7000)
}

suspend fun loadDataFromServer(callback: (List<String>) -> Unit) {
    // Simulate loading data from a remote server
    Thread.sleep(5000)

    // Return some dummy data
    val data = listOf("John", "Jane", "Bob", "Alice")

    // Invoke the callback function with the loaded data
    callback(data)
}

suspend fun fetchData(): String {
    delay(1000) // Simulating network request delay
    return "Data fetched"
}
suspend fun processData(data: String): String {
    delay(500) // Simulating processing delay
    return "Processed: $data"
}