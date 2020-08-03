package sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

expect class Sample() {
    fun checkMe(): Int
}

expect object Platform {
    val name: String

    suspend fun awaitResponse(): String

    fun flowOfResponse(): Flow<String>
}

suspend fun awaitTest(): String {
    delay(1000)
    return "suspend: Kotlin/MP - shared module (Sample.kt) - awaitTest()"
}

fun flowOfTest() = flow {
    var counter = 1500
    while (true) {
        emit("FLOW: Kotlin/MP - shared module (Sample.kt) - flowOfTest(): $counter")
        delay(1000)
        counter++
    }
}

fun hello(): String = "Hello from ${Platform.name}"

class Proxy {
    fun proxyHello() = hello()
}

fun main() {
    println(hello())
}