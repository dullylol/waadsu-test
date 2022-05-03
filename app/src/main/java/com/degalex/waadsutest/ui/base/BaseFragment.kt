package com.degalex.waadsutest.ui.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

// Базовый класс фрагментов, чтоб избавиться от написания одинакового кода
abstract class BaseFragment : Fragment() {

    // Вызываем collect для Flow сразу в корутине
    protected fun <T> Flow<T>.collectInCoroutine(collector: FlowCollector<T>) {
        lifecycleScope.launch {
            collect(collector)
        }
    }
}