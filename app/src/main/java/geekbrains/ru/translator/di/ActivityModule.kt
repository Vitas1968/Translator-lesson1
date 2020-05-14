package geekbrains.ru.translator.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import geekbrains.ru.translator.view.main.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
