package com.example.lesson_4_safarov

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.lang.AssertionError
import kotlin.coroutines.ContinuationInterceptor

class MainViewModel : ViewModel() {

    companion object {
        private const val EMPTY_STRING = ""
        private fun log(logText: String) {
            Log.i("coroutines test", logText)
        }
    }

    private val _stringValue = MutableLiveData(EMPTY_STRING)
    var stringValue: LiveData<String> = _stringValue

    fun onButtonClicked(stringValue: String) {
        this._stringValue.value = stringValue
    }

    init {
        coroutineExample1()
        //coroutineExample2()
        //coroutineExample3()
        //coroutineExample4()
        //coroutineExample5()
        //coroutineExample6()
        //coroutineExample7()
        //coroutineExample8()
        //coroutineExample9()
        //coroutineExample10()
        //coroutineExample11()
    }

    private fun coroutineExample1() {
        viewModelScope.launch {
            log("init() in")

            val job = Job()
            val coroutine = launch(
                context = job,
                start = CoroutineStart.DEFAULT
            ) {
                alpha1()
            }
            log("Coroutine: $coroutine")
            log("Job: $job")

            log("init() out")
        }
    }

    private suspend fun alpha1() {
        log("alpha() in")
        delay(1000)
        log("alpha() out")
    }

    private fun coroutineExample2() {
        viewModelScope.launch(Dispatchers.Default) {
            log("init() in " + Thread.currentThread().toString())
            val coroutine = launch(
                context = Dispatchers.Main,
                start = CoroutineStart.DEFAULT
            ) {
                alpha2()
            }
            coroutine.join()
            log("init() out " + Thread.currentThread().toString())
        }
    }

    private suspend fun alpha2() {
        log("alpha() in" + Thread.currentThread().toString())
        delay(1000)
        log("alpha() out" + Thread.currentThread().toString())
    }

    private fun coroutineExample3() {
        viewModelScope.launch {
            log("CoroutineName: "
                        + coroutineContext[CoroutineName].toString())
            log("Job: "
                        + coroutineContext[Job].toString())
            log("ContinuationInterceptor: "
                        + coroutineContext[ContinuationInterceptor].toString())
            log("CoroutineExceptionHandler: "
                        + coroutineContext[CoroutineExceptionHandler].toString())
        }
    }

    private fun coroutineExample4() {
        viewModelScope.launch {
            val parentJob = launch {
                val childJobAlpha = launch {
                    log("childJobAlpha: in")
                    repeat(1000) {
                        log("childJobAlpha step: $it")
                        delay(1000L)
                    }
                    log("childJobAlpha: out")
                }
                childJobAlpha.invokeOnCompletion {
                    log("childJobAlpha: ${it?.message}")
                }
                val childJobBravo = launch {
                    log("childJobBravo: in")
                    repeat(1000) {
                        log("childJobBravo step: $it")
                        delay(1000L)
                    }
                    log("childJobBravo: out")
                }
                childJobBravo.invokeOnCompletion {
                    log("childJobBravo: ${it?.message}")
                }
                delay(10000)
            }
            delay(1500)
            log("parentJob isActive: ${parentJob.isActive}")
        }
    }

    private fun coroutineExample5() {
        viewModelScope.launch {
            val handler = CoroutineExceptionHandler { _, throwable ->
                log("Caught ${throwable.message}")
            }
            val parentJob = launch(handler + SupervisorJob()) {
                try {
                    val childJobAlpha = launch(SupervisorJob()) {
                        log("childJobAlpha: in")
                        repeat(1000) {
                            log("childJobAlpha step: $it")
                            delay(1000L)
                        }
                        log("childJobAlpha: out")
                    }
                } catch (t: Throwable) {
                    log("childJobAlpha: ${t.message}")
                }

                try {
                    val childJobBravo = launch {
                        log("childJobBravo: in")
                        delay(1000)
                        throw AssertionError("Forced exception")
                    }
                } catch (t: Throwable) {
                    log("childJobBravo: ${t.message}")
                }
            }
            parentJob.invokeOnCompletion {
                log("childJobBravo: ${it?.message}")
            }
            delay(1500)
            log("parentJob isActive: ${parentJob.isActive}")
        }
    }

    private fun coroutineExample6() {
        viewModelScope.launch {
            val parentJob = launch(SupervisorJob()) {
                val childBravo = async(SupervisorJob()) {
                    log("childJobBravo: in")
                    delay(1000)
                    throw AssertionError("Forced exception")
                }
                try {
                    childBravo.await()
                } catch (t: Throwable) {
                    log("Caught: ${t.message}")
                }
                log("parentJob() out")
            }
            parentJob.invokeOnCompletion {
                log("parentJob: ${it?.message}")
            }

            delay(1500)
            log("parentJob isActive: ${parentJob.isActive}")
        }
    }

    private fun coroutineExample7() {
        val handler = CoroutineExceptionHandler { _, exception ->
            log("Caught: ${exception.message}")
        }
        viewModelScope.launch(handler) {
            try {
                val childAlpha = launch {
                    log("childAlpha: in")
                    repeat(1000) {
                        delay(1000)
                        log("childAlpha step: $it")
                    }
                }
            } catch (t: Throwable) {
                log("Caught: ${t.message}")
            }
        }
        viewModelScope.launch(handler) {
            try {
                val childBravo = launch {
                    log("childBravo: in")
                    delay(1000)
                    throw AssertionError("Forced exception")
                }
            } catch (t: Throwable) {
                log("Caught: ${t.message}")
            }
        }
    }

    private fun coroutineExample8() {
        viewModelScope.launch {
            val veryLongJob = launch {
                try {
                    log("veryLongJob() before delay")
                    delay(Long.MAX_VALUE)
                    log("veryLongJob() after delay")
                } finally {
                    log("veryLongJob() cancelled")
                }
            }
            log("veryLongJob() cancelling")
            yield()
            veryLongJob.cancelAndJoin()

            log("veryLongJob() isActive: ${veryLongJob.isActive}")
            log("parentJob() isActive: $isActive")
        }
    }

    private fun coroutineExample9() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            log("1: " + Thread.currentThread().toString())
            delay(1000)
            log("2: " + Thread.currentThread().toString())

            withContext(Dispatchers.Main) {
                delay(1000L)
                log("3: " + Thread.currentThread().toString())

                withContext(Dispatchers.Unconfined) {
                    log("4: " + Thread.currentThread().toString())
                    delay(1000L)
                    log("5: " + Thread.currentThread().toString())
                }
            }
            log("6: " + Thread.currentThread().toString())
            delay(1000L)
            log("7: " + Thread.currentThread().toString())
        }
    }

    private fun coroutineExample10() {
        viewModelScope.launch(Dispatchers.Default) {
            log("1: " + Thread.currentThread().toString())
            delay(1000L)
            log("2: " + Thread.currentThread().toString())

            withContext(Dispatchers.IO) {
                delay(1000L)
                log("3: " + Thread.currentThread().toString())
            }

            log("4: " + Thread.currentThread().toString())
            delay(1000L)
            log("5: " + Thread.currentThread().toString())
        }
    }

    private fun coroutineExample11() {
        viewModelScope.launch(Dispatchers.Default) {
            launch {
                repeat(10) {
                    delay(1000L)
                    log("1-$it: " + Thread.currentThread().toString())
                }
            }
            launch {
                repeat(10) {
                    delay(1000L)
                    log("2-$it: " + Thread.currentThread().toString())
                }
            }
        }
    }
}