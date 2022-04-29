package com.degalex.waadsutest.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityHolder @Inject constructor() {

    var activity: AppCompatActivity? = null
        private set

    val fragmentManager by lazy { activity?.supportFragmentManager }

    fun attachActivity(activity: AppCompatActivity) {
        this.activity = activity
    }

    fun detachActivity() {
        activity = null
    }
}