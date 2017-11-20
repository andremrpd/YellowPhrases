package me.plynk.yellowphrases.di.modules

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import dagger.Module
import dagger.Provides
import me.plynk.yellowphrases.di.ActivityContext

@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    internal fun provideFragment(): Fragment = fragment

    @Provides
    internal fun provideActivity(): Activity = fragment.activity!!

    @Provides
    @ActivityContext
    internal fun provideContext(): Context = fragment.activity!!

}