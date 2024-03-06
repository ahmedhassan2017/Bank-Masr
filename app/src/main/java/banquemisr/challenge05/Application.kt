package banquemisr.challenge05

import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.File
import android.app.Application as AndroidApplication
@HiltAndroidApp
class Application: AndroidApplication() {


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


    }
}