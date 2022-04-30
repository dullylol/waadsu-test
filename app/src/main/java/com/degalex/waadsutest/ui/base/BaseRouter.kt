package com.degalex.waadsutest.ui.base

import android.content.Intent
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.degalex.waadsutest.ui.navigation.ActivityHolder

abstract class BaseRouter(
    private val activityHolder: ActivityHolder,
) {

    protected fun startActivity(intent: Intent) {
        activityHolder.activity?.startActivity(intent)
    }

    protected fun openFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
    ) {
        activityHolder.activity?.supportFragmentManager?.commit {
            add(containerViewId, fragment)
            addToBackStack(null)
        }
    }

    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
    ) {
        activityHolder.activity?.supportFragmentManager?.commit {
            replace(containerViewId, fragment)
        }
    }

    protected fun back() {
        activityHolder.activity?.onBackPressed()
    }
}