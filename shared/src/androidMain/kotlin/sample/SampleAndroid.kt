package sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

actual class Sample {
    actual fun checkMe() = 44
}

actual object Platform {
    actual val name: String = "Android"

    actual suspend fun awaitResponse(): String {
        delay(1000)
        return "suspend: Kotlin/MP - shared module (SampleAndroid.kt) - awaitResponse(), for $name"
    }

    actual fun flowOfResponse() = flow {
        var counter = 1500
        while (true) {
            emit("FLOW: Kotlin/MP - shared module (SampleAndroid.kt) - flowOfTest(): $counter")
            delay(1000)
            counter++
        }
    }
}
