package com.hrudhaykanth116.mafet

import android.app.Activity
import android.graphics.Bitmap
import android.os.Process
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import kotlin.system.exitProcess
import androidx.core.graphics.createBitmap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrashHandler @Inject constructor(
) : Thread.UncaughtExceptionHandler {

    private lateinit var mafetApplication: MafetApplication

    fun init(mafetApplication: MafetApplication){
        this.mafetApplication = mafetApplication
    }

    private val defaultHandler: Thread.UncaughtExceptionHandler? =
        Thread.getDefaultUncaughtExceptionHandler()


    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        Log.e("CrashHandler", "Uncaught exception in ${thread.name}", throwable)

        try {
            mafetApplication.currentActivity?.let {
                val file = File(it.cacheDir, "crash.png")
                it.captureAndSaveScreenshot(file)
            }
        }catch (exception: Exception){
            Log.e(TAG, "uncaughtException: ", exception)
        }

        defaultHandler?.uncaughtException(thread, throwable)

    }

    fun Activity.captureAndSaveScreenshot(file: File): File {
        val view = window.decorView.rootView
        val bitmap = createBitmap(view.width, view.height)
        val canvas = android.graphics.Canvas(bitmap)
        view.draw(canvas)

        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        return file
    }

    companion object{
        private const val TAG = "CrashHandler"
    }

}
