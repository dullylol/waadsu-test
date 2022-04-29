package com.degalex.waadsutest.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.degalex.waadsutest.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    protected val errorMutableSharedFlow = MutableSharedFlow<Throwable>()
    val errorSharedFlow = errorMutableSharedFlow.asSharedFlow()

    protected val loadingMutableStateFlow = MutableStateFlow(false)
    val loadingStateFlow = loadingMutableStateFlow.asStateFlow()

    override val coroutineContext = viewModelScope.coroutineContext + Dispatchers.IO +
            CoroutineExceptionHandler { _, throwable ->
                errorMutableSharedFlow.tryEmit(throwable)

                if (BuildConfig.DEBUG) {
                    Log.e("COROUTINE_EXCEPTION", throwable.stackTraceToString())
                }
            }
}