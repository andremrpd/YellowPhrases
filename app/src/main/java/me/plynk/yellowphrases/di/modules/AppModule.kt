package me.plynk.yellowphrases.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import me.plynk.yellowphrases.App
import me.plynk.yellowphrases.di.ApplicationContext


@Module
class AppModule (val app: App) {

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context = app

    @Provides
    internal fun provideApplication() : Application = app

}