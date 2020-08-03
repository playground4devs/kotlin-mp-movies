package sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

actual class Sample {
    actual fun checkMe() = 7
}

actual object Platform {
    actual val name: String = "iOS"

    actual suspend fun awaitResponse(): String {
        delay(1000)
        return "suspend: Kotlin/MP - shared module (SampleIos.kt) - awaitResponse(), for $name"
    }

    actual fun flowOfResponse() = flow {
        var counter = 1000
        while (true) {
            emit("FLOW: Kotlin/MP - shared module (SampleIos.kt) - flowOfTest(): $counter")
            delay(1000)
            counter--
        }
    }
}