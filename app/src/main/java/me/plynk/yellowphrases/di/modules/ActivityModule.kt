package me.plynk.yellowphrases.di.modules

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import me.plynk.yellowphrases.di.ActivityContext


@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    internal fun provideActivity(): Activity = activity

    @Provides
    @ActivityContext
    internal fun provideContext(): Context = activity
}