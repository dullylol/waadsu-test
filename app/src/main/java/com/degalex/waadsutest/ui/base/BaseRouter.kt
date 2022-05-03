package com.degalex.waadsutest.ui.base

import android.content.Intent
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.degalex.waadsutest.ui.navigation.ActivityHolder

// Базовый класс для роутеров для удобной дальнейшей навигации и избавления повторяющегося кода
abstract class BaseRouter(
    private val activityHolder: ActivityHolder,
) {

    // Запускат новое активити по переданному интенту
    protected fun startActivity(intent: Intent) {
        activityHolder.activity?.startActivity(intent)
    }

    // Открывет новый фрагмент с добавлением транзакции в БэкСтэк
    protected fun openFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
    ) {
        activityHolder.activity?.supportFragmentManager?.commit {
            add(containerViewId, fragment)
            addToBackStack(null)
        }
    }

    // Заменяет текущий фрагмент на новый без добавления в БэкСтэк
    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
    ) {
        activityHolder.activity?.supportFragmentManager?.commit {
            replace(containerViewId, fragment)
        }
    }

    // Навигация назад
    protected fun back() {
        activityHolder.activity?.onBackPressed()
    }
}