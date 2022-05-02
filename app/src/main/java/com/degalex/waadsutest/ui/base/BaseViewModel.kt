package com.degalex.waadsutest.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.degalex.waadsutest.BuildConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    protected val errorMutableStateFlow = MutableStateFlow<Throwable?>(null)
    val errorStateFlow = errorMutableStateFlow.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (BuildConfig.DEBUG) {
            Log.e("COROUTINE_EXCEPTION", throwable.stackTraceToString())
        }
        onCoroutineError(throwable)
    }

    protected open fun onCoroutineError(throwable: Throwable) {
        errorMutableStateFlow.value = throwable
    }

    protected fun launchSafely(
        coroutineContext: CoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ) = viewModelScope.launch(
        context = viewModelScope.coroutineContext + coroutineExceptionHandler + coroutineContext,
        start = start,
        block = block,
    )

    protected fun launchSafelyWithLoading(
        context: CoroutineContext,
        loadingMutableStateFlow: MutableStateFlow<Boolean>,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        loadingMutableStateFlow.value = true

        return launchSafely(context, start) {
            block()
            loadingMutableStateFlow.value = false
        }
    }
}