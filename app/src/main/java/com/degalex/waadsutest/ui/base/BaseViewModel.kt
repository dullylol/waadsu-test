package com.degalex.waadsutest.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.degalex.waadsutest.BuildConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext

// Базовый класс для вьюмоделей, чтоб избавиться от дальнейшего написания одинакового кода
abstract class BaseViewModel : ViewModel() {

    // StateFlow для хранения ошибки
    protected val errorMutableStateFlow = MutableStateFlow<Throwable?>(null)
    val errorStateFlow = errorMutableStateFlow.asStateFlow()

    // Обработчик ошибок, выкидуемых в корутинах
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (BuildConfig.DEBUG) {
            Log.e("COROUTINE_EXCEPTION", throwable.stackTraceToString())
        }
        onCoroutineError(throwable)
    }

    // Метод, который вызывается при ошибки в корутине, который можно переопределить
    protected open fun onCoroutineError(throwable: Throwable) {
        errorMutableStateFlow.value = throwable
    }

    // Запуск корутины с обработчиком ошибок
    protected fun launchSafely(
        coroutineContext: CoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ) = viewModelScope.launch(
        context = viewModelScope.coroutineContext + coroutineExceptionHandler + coroutineContext,
        start = start,
        block = block,
    )

    // Запуск корутины с обработчиком ошибок и StateFlow для загрузки.
    // Перед запуском корутины этот StateFlow = true, а в конце её выполения false
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