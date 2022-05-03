package com.degalex.waadsutest.ui.navigation

import androidx.appcompat.app.AppCompatActivity

// Класс для хранения активити, который потом инжектиться в роутеры для навигации
class ActivityHolder {

    var activity: AppCompatActivity? = null
        private set

    fun attachActivity(activity: AppCompatActivity) {
        this.activity = activity
    }

    fun detachActivity() {
        activity = null
    }
}