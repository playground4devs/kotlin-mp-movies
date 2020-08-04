package sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

actual class Sample {
    actual fun checkMe() = 44
}

actual object Platform {
    actual val name: String = "Android"
}
