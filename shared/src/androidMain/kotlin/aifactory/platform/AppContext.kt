package aifactory.platform

import android.content.Context

object AppContext {
    lateinit var context: Context
        private set

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
}
