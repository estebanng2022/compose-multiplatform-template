package aifactory.core

import android.util.Log

object Logx {
    private const val TAG = "AiFactoryApp"

    fun d(tag: String = TAG, msg: String) {
        Log.d(tag, msg)
    }

    fun i(tag: String = TAG, msg: String) {
        Log.i(tag, msg)
    }

    fun w(tag: String = TAG, msg: String) {
        Log.w(tag, msg)
    }

    fun w(msg: String, t: Throwable?) {
        Log.w(TAG, msg, t)
    }

    fun e(tag: String = TAG, msg: String, t: Throwable? = null) {
        Log.e(tag, msg, t)
    }
}
