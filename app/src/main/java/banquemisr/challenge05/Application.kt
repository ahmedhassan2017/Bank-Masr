package banquemisr.challenge05

import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.File
import android.app.Application as AndroidApplication

class Application: AndroidApplication() {
    companion object {
//        lateinit var appCacheDir: File


    }

    override fun onCreate() {
        super.onCreate()

        /** set sentry error handler for rxjava errors (if not set, it will crash the app) */
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                // Merely log undeliverable exceptions
            } else {
                // Forward all others to current thread's uncaught exception handler
                Thread.currentThread().also { thread ->
                    thread.uncaughtExceptionHandler?.uncaughtException(thread, e)
                }
            }
        }

//        appCacheDir = cacheDir



    }
}