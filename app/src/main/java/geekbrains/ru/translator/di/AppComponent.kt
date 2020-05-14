package geekbrains.ru.translator.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import geekbrains.ru.translator.application.TranslatorApp
import geekbrains.ru.translator.di.modules.ActivityModule
import geekbrains.ru.translator.di.modules.InteractorModule
import geekbrains.ru.translator.di.modules.RepositoryModule
import geekbrains.ru.translator.di.modules.ViewModelModule
import javax.inject.Singleton

@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(englishVocabularyApp: TranslatorApp)
}
