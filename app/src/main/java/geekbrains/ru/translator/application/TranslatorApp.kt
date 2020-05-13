package geekbrains.ru.translator.application

import android.app.Application
import timber.log.Timber

class TranslatorApp: Application() {
    companion object {
        lateinit var instance: TranslatorApp
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
    }

}