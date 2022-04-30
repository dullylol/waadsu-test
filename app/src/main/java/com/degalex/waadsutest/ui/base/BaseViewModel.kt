package com.degalex.waadsutest.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.degalex.waadsutest.BuildConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    protected val errorMutableStateFlow = MutableStateFlow<Throwable?>(null)
    val errorStateFlow = errorMutableStateFlow.asStateFlow()

    protected val loadingMutableStateFlow = MutableStateFlow(false)
    val loadingStateFlow = loadingMutableStateFlow.asStateFlow()

    override val coroutineContext = viewModelScope.coroutineContext + Dispatchers.IO +
            CoroutineExceptionHandler { _, throwable ->
                errorMutableStateFlow.value = throwable

                if (BuildConfig.DEBUG) {
                    Log.e("COROUTINE_EXCEPTION", throwable.stackTraceToString())
                }
            }

    protected fun CoroutineScope.launchWithLoading(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ) : Job {
        onLoading(true)

        return launch(context, start) {
            block()
            onLoading(false)
        }
    }

    protected fun onLoading(loading: Boolean) {
        loadingMutableStateFlow.value = loading
    }

    protected fun onError(throwable: Throwable) {
        onLoading(false)
        errorMutableStateFlow.tryEmit(throwable)
    }
}