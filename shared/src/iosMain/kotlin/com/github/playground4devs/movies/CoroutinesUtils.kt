package com.github.playground4devs.movies

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

sealed class SuspendWrapperParent<T>(private val suspender: suspend () -> T) {

    fun subscribe(
            scope: CoroutineScope,
            onSuccess: (item: T) -> Unit,
            onThrow: (error: Throwable) -> Unit
    ) = scope.launch {
        try {
            onSuccess(suspender())
        } catch (error: Throwable) {
            onThrow(error)
        }
    }

    fun blockingSubscribe(
        onSuccess: (item: T) -> Unit,
        onThrow: (error: Throwable) -> Unit
    ) = runBlocking {
        try {
            onSuccess(suspender())
        } catch (error: Throwable) {
            onThrow(error)
        }
    }
}

class SuspendWrapper<T : Any>(suspender: suspend () -> T) : SuspendWrapperParent<T>(suspender)
class NullableSuspendWrapper<T>(suspender: suspend () -> T) : SuspendWrapperParent<T>(suspender)

sealed class FlowWrapperParent<T>(private val flow: Flow<T>) {

    fun subscribe(
            scope: CoroutineScope,
            onEach: (item: T) -> Unit,
            onComplete: () -> Unit,
            onThrow: (error: Throwable) -> Unit
    ) = flow
            .onEach { onEach(it) }
            .catch { onThrow(it) } // catch{} before onCompletion{} or else completion hits rx first and ends stream
            .onCompletion { onComplete() }
            .launchIn(scope)
}

class FlowWrapper<T : Any>(flow: Flow<T>) : FlowWrapperParent<T>(flow)
class NullableFlowWrapper<T>(flow: Flow<T>) : FlowWrapperParent<T>(flow)