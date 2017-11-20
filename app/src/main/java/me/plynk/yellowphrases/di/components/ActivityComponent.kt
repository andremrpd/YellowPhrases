package me.plynk.yellowphrases.di.components

import dagger.Subcomponent
import me.plynk.yellowphrases.di.PerActivity
import me.plynk.yellowphrases.di.modules.ActivityModule
import me.plynk.yellowphrases.features.base.BaseActivity
import me.plynk.yellowphrases.features.main.MainActivity

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: BaseActivity)
    fun inject(activity: MainActivity)

}