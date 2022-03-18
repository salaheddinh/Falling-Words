package com.salaheddin.fallingwords.utils

import kotlinx.coroutines.*

class Timer(private val time: Long,private val delayMillis: Long, private val action: (Long) -> Unit) {

    var isPaused = false
    private set

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    private var milliseconds = 0L

    private fun startCoroutineTimer(time: Long, delayMillis: Long, action: () -> Unit) =
        scope.launch(Dispatchers.IO) {
            while (milliseconds < time) {
                if (!isPaused) {
                    milliseconds += delayMillis
                    action()
                    delay(delayMillis)
                }
            }
        }

    private var timer: Job = startCoroutineTimer(time = time, delayMillis = delayMillis) {
        scope.launch(Dispatchers.Main) {
            action(milliseconds)
        }
    }

    fun startTimer() {
        timer.start()
    }

    fun restartTimer() {
        milliseconds = 0
        if (timer.isCompleted || timer.isCancelled) {
            timer = startCoroutineTimer(time = time, delayMillis = delayMillis) {
                scope.launch(Dispatchers.Main) {
                    action(milliseconds)
                }
            }
        }
    }

    fun stop() {
        timer.cancel()
    }

    fun pause() {
        isPaused = true
    }

    fun resume() {
        isPaused = false
    }
}